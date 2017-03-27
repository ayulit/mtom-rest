package ru.mera.samples.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table( name = "ADDRESSES" )
@Inheritance( strategy = InheritanceType.JOINED )
public class AddressEntity extends AbstractEntity {

  @Column
  @OneToMany
  List<UserEntity> residents;

  @Column
  private String country;

  @Column
  private String region;

  @Column
  private String town;

  @Column
  private String street;

  @Column
  private String house;

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

  public List<UserEntity> getResidents() {
    return residents;
  }

  public void setResidents(List<UserEntity> residents) {
    this.residents = residents;
  }
}
