package ru.mera.samples.application.mappings;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.domain.dao.AddressRepository;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;


public class UserToEntityMap extends PropertyMap<UserDTO, UserEntity> {

  public static final String LITTERAL = ", ";

  @Autowired
  private AddressRepository addressRepository;

  Converter<UserDTO, AddressEntity> converter = new AbstractConverter<UserDTO, AddressEntity>() {

      @Override
      protected AddressEntity convert(UserDTO source) {

          if (source == null) {
              return null;
          } else if (source.getAddressId() == null || source.getAddressId() == 0) {
              return null;                    
          } else {

              Long addressId = source.getAddressId();
              AddressEntity address = addressRepository.findById(addressId);

              return address;
          }
      }
  };
  
  protected void configure() {

      String login = source.getLogin();      
      map().setName(login);

      // FIXME possibly we need to do all in one mapping
      using(converter).map(source, destination.getAddress());
      
  }
}

