package org.example.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {
  @Id
  private Long idAddress;

  private String ipAddress;
  private String label;

  @Override
  public String toString() {
    return String.format("Address(%s,%s,%s)", idAddress, ipAddress, label);
  }

  public Long getIdAddress() {
    return idAddress;
  }

  public void setIdAddress(Long idAddress) {
    this.idAddress = idAddress;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Address() {
  }

  public static AddressBuilder builder() {
    return new AddressBuilder();
  }

  public static class AddressBuilder {
    private Address result;

    public AddressBuilder() {
      result = new Address();
    }

    public AddressBuilder idAddress(Long idAddress) {
      result.setIdAddress(idAddress);
      return this;
    }

    public AddressBuilder ipAddress(String ipAddress) {
      result.setIpAddress(ipAddress);
      return this;
    }

    public AddressBuilder label(String label) {
      result.setLabel(label);
      return this;
    }

    public Address build() {
      return result;
    }
  }

}
