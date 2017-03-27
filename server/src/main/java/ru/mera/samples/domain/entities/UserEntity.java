package ru.mera.samples.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "USERS" )
@Inheritance( strategy = InheritanceType.JOINED )
public class UserEntity extends AbstractNamedEntity {

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  @ManyToOne
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public AddressEntity getAddress() {
    return address;
  }

  public void setAddress(AddressEntity address) {
    this.address = address;
  }
}
