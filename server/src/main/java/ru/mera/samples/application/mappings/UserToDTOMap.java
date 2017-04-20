package ru.mera.samples.application.mappings;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;

public class UserToDTOMap extends PropertyMap<UserEntity, UserDTO> {

  public static final String LITTERAL = ", ";

  Converter<UserEntity, String> converter = new AbstractConverter<UserEntity, String>() {

      @Override
      protected String convert(UserEntity source) {

          if (source == null) {
              return null;
          } else if (source.getAddress() == null) {
              return null;                    
          } else {
              
              StringBuilder builder = new StringBuilder();
              AddressEntity address = source.getAddress();
              
              builder.append(address.getCountry()).append(LITTERAL)
                     .append(address.getRegion()).append(LITTERAL)
                     .append(address.getTown()).append(LITTERAL)
                     .append(address.getStreet()).append(LITTERAL)
                     .append(address.getHouse());
              
              return builder.toString();
          }
      }
  };  
  
  protected void configure() {
      map(source.getName(), destination.getLogin());
      using(converter).map(source, destination.getAddress());    
  }
}

