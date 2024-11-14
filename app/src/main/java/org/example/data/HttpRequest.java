package org.example.data;

import java.util.Map;

public class HttpRequest extends Request {
  private String method;
  private String host;
  private String type;
  private Map<String, String> headers;
  private Map<String, Object> body;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  @Override
  public String toString() {
    return String.format("HttpRequest(%s,%s,%s,%s)", method, host, type, headers, body);
  }

  public static class HttpRequestBuilder extends RequestBuilder {
    private HttpRequest result;

    public HttpRequestBuilder() {
      result = new HttpRequest();
    }

    public HttpRequestBuilder method(String method) {
      result.setMethod(method);
      return this;
    }

    public HttpRequestBuilder host(String host) {
      result.setHost(host);
      return this;
    }

    public HttpRequestBuilder type(String type) {
      result.setType(type);
      return this;
    }

    public HttpRequestBuilder headers(Map<String, String> headers) {
      result.setHeaders(headers);
      return this;
    }

    public HttpRequestBuilder body(Map<String, Object> body) {
      result.setBody(body);
      return this;
    }

    public HttpRequest build() {
      return result;
    }
  }

  public static HttpRequestBuilder builder() {
    return new HttpRequestBuilder();
  }

  public HttpRequest() {
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, Object> getBody() {
    return body;
  }

  public void setBody(Map<String, Object> body) {
    this.body = body;
  }
}
