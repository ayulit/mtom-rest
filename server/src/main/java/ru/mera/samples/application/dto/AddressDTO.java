package ru.mera.samples.application.dto;

import java.util.Map;

public class AddressDTO extends AbstractDTO {

  Map<Long, String> residents;

  private String country;

  private String region;

  private String town;

  private String street;

  private String house;

  public Map<Long, String> getResidents() {
    return residents;
  }

  public void setResidents(Map<Long, String> residents) {
    this.residents = residents;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }
}
