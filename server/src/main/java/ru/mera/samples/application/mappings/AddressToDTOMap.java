package ru.mera.samples.application.mappings;


import org.modelmapper.PropertyMap;
import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.domain.entities.AddressEntity;

import java.util.HashMap;
import java.util.Map;

public class AddressToDTOMap extends PropertyMap<AddressEntity, AddressDTO> {

  public static final String LITTERAL = ", ";

  protected void configure() {
    Map<Long, String> residents       = new HashMap<>();
    StringBuilder     fullNameBuilder = new StringBuilder();
    source.getResidents().forEach(userEntity -> {
      fullNameBuilder.append(userEntity.getLastName()).append(",").append(userEntity.getFirstName());
      residents.put(userEntity.getId(), fullNameBuilder.toString());
    });
    map().setResidents(residents);
  }
}

