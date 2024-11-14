package org.example.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.example.data.HttpRequest;

public class HttpAdapter {

  public HttpAdapter() {

  }

  public HttpRequest adaptRequest(String content) {
    String[] contentArray = Arrays.stream(content.split("\n")).filter((c) -> !c.isBlank()).toArray(String[]::new);
    String firstLine = contentArray[0];
    String[] connectionArray = firstLine.split(" ");
    String method = connectionArray[0].trim();
    String type = connectionArray[2].trim();
    String host = connectionArray[1].trim();
    Map<String, String> headers = getHeaders(Arrays.copyOfRange(contentArray, 1, contentArray.length));

    return HttpRequest.builder().method(method).type(type).host(host).headers(headers).build();
  }

  private Map<String, String> getHeaders(String[] content) {
    Map<String, String> headers = new HashMap<>();
    for (String header : content) {
      String[] contentArray = header.split(": ");
      headers.put(contentArray[0].trim(), contentArray[1].trim());
    }
    return headers;
  }

  public String requestToString(HttpRequest request) {
    String result = request.getMethod() + " " + request.getHost() + " " + request.getType();
    for (Entry<String, String> header : request.getHeaders().entrySet()) {
      result += "\n" + header.getKey() + ": " + header.getValue();
    }
    return result + "\n";
  }
}
