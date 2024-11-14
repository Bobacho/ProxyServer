package org.example.data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Request {
  @Override
  public String toString() {
    return String.format("Request(%s,%s,%s)", idRequest, time.toString(), address.toString());
  }

  public static class RequestBuilder {
    private Request request;

    public RequestBuilder() {
      request = new Request();
    }

    public RequestBuilder address(Address address) {
      request.setAddress(address);
      return this;
    }

    public RequestBuilder content(String content) {
      request.setContent(content);
      return this;
    }

    public RequestBuilder time(LocalDateTime time) {
      request.setTime(time);
      return this;
    }

    public Request build() {
      return request;
    }

    public RequestBuilder idRequest(Long idRequest) {
      request.setIdRequest(idRequest);
      return this;
    }
  }

  public static RequestBuilder builder() {
    return new RequestBuilder();
  }

  @Id
  private Long idRequest;

  public Long getIdRequest() {
    return idRequest;
  }

  public void setIdRequest(Long idRequest) {
    this.idRequest = idRequest;
  }

  @OneToOne
  private Address address;
  private LocalDateTime time;

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  private String content;

  public Request() {

  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
