package ru.mera.samples.application.mappings;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.domain.dao.UserRepository;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;


public class AddressToEntityMap extends PropertyMap<AddressDTO, AddressEntity> {
    
    private static final Logger logger = LoggerFactory.getLogger(AddressToEntityMap.class);

    public static final String LITTERAL = ", ";

    @Autowired
    private UserRepository userRepository;

    Converter<AddressDTO, List<UserEntity>> converter = new AbstractConverter<AddressDTO, List<UserEntity>>() {

        @Override
        protected List<UserEntity> convert(AddressDTO source) {

            
            List<UserEntity> residents       = new ArrayList<>();

            if (source == null) {
                return null;
            } else if (source.getResidents() == null) {
                return null;                    
            } else {

                source.getResidents().forEach((id, fullName) -> {
                    UserEntity userEntity = userRepository.findById(1L);
                    residents.add(userEntity);
                });

                return residents;
            }
        }
    };

  protected void configure() {
      
      using(converter).map(source, destination.getResidents());
      
  }
}

