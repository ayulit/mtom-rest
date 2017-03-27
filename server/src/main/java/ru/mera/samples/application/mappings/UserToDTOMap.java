package ru.mera.samples.application.mappings;


import org.modelmapper.PropertyMap;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;

public class UserToDTOMap extends PropertyMap<UserEntity, UserDTO> {

  public static final String LITTERAL = ", ";

  protected void configure() {
    StringBuilder builder = new StringBuilder();
    AddressEntity address = source.getAddress();
    builder.append(address.getCountry()).append(LITTERAL).append(address.getRegion()).append(LITTERAL).append(
        address.getTown()).append(LITTERAL).append(address.getStreet()).append(LITTERAL).append(address.getHouse());
    map().setAddressId(address.getId());
    map().setAddress(builder.toString());
  }
}

