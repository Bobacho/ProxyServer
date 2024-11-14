package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.example.data.Address;
import org.example.data.HttpRequest;
import org.example.data.Request;

public class ProxyServer {

  private List<Thread> instances;
  private ServerSocket serverSocket;

  public ProxyServer() throws IOException {
    instances = new ArrayList<>();
    serverSocket = new ServerSocket(8080);
    for (int i = 0; i < 200; i++) {
      instances.add(initInstance(i + 1));
    }
  }

  public void start() {
    for (Thread instance : instances) {
      instance.start();
    }
  }

  public Thread initInstance(int index) throws IOException {
    return Thread.ofPlatform().unstarted(() -> {
      while (true) {
        System.out.println("Thread: Number " + index);
        try (Socket clientSocket = serverSocket.accept()) {

          try (InputStream inputStream = clientSocket.getInputStream();
              OutputStream outputStream = clientSocket.getOutputStream();
              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
              requestBuilder.append(line).append("\r\n");
              if (line.isEmpty()) {
                break;
              }
            }

            String requestString = requestBuilder.toString();
            System.out.println("Original Input:\n" + requestString);
            Request requestModel = Request.builder().address(Address.builder()
                .ipAddress(clientSocket.getInetAddress().getHostAddress()).build())
                .time(LocalDateTime.now())
                .build();
            HttpAdapter httpAdapter = new HttpAdapter();
            HttpRequest request = httpAdapter.adaptRequest(requestString);

            String targetHost = request.getHeaders().get("Host").trim();
            if (targetHost == null || targetHost.isEmpty()) {
              outputStream.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes());
              return;
            }

            try (Socket targetSocket = new Socket(targetHost, 443)) {
              OutputStream targetOutputStream = targetSocket.getOutputStream();
              targetOutputStream.write(requestString.getBytes("UTF-8"));
              targetOutputStream.flush();

              InputStream targetInputStream = targetSocket.getInputStream();
              byte[] buffer = new byte[2048];
              targetSocket.setSoTimeout(1000);
              int bytesRead;
              while (targetInputStream.available() >= 0) {
                bytesRead = targetInputStream.read(buffer);
                if (bytesRead != 1) {
                  outputStream.write(buffer, 0, bytesRead);
                }
              }
            } catch (IOException e) {
              outputStream.write("HTTP/1.1 502 Bad Gateway\r\n\r\n".getBytes());
            }
          }
        } catch (IOException exception) {
          exception.printStackTrace();
        }
      }
    });
  }
}
