package org.example;

import java.sql.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.example.server.ProxyServer;
import org.hibernate.cfg.Configuration;

public class App {
  public static void main(String[] args) throws SQLException, IOException {
    String url = "jdbc:mysql://localhost:3306/proxy_db?user=root&password=bobilarius19";
    Configuration configuration = new Configuration();
    configuration.addURL(new URL(url));
    ProxyServer server = new ProxyServer();
    server.start();
  }
}
