package ru.mera.samples.application.mappings;


import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.domain.dao.AddressRepository;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;


public class UserToEntityMap extends PropertyMap<UserDTO, UserEntity> {

  public static final String LITTERAL = ", ";

  @Autowired
  private AddressRepository addressRepository;

  protected void configure() {
    Long          addressId = source.getAddressId();
    AddressEntity address   = null;
    if( addressId != null && addressId >= 0 ) {
      address = addressRepository.findById(addressId);
    }
    map().setAddress(address);
  }
}

