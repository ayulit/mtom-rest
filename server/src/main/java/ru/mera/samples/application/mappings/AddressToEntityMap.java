package ru.mera.samples.application.mappings;


import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.domain.dao.UserRepository;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddressToEntityMap extends PropertyMap<AddressDTO, AddressEntity> {

  public static final String LITTERAL = ", ";

  @Autowired
  private UserRepository userRepository;

  protected void configure() {
    List<UserEntity> residents       = new ArrayList<>();
    StringBuilder    fullNameBuilder = new StringBuilder();
    source.getResidents().forEach((id, fullName) -> {
      UserEntity userEntity = userRepository.findById(id);
      residents.add(userEntity);
    });
    map().setResidents(residents);
  }
}

