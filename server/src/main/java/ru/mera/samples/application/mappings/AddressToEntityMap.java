package ru.mera.samples.application.mappings;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.dao.UserRepository;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;


public class AddressToEntityMap extends PropertyMap<AddressDTO, AddressEntity> {
    
    private static final Logger logger = LoggerFactory.getLogger(AddressToEntityMap.class);

    public static final String LITTERAL = ", ";

    @Autowired
    private UserRepository userRepository;

    Converter<AddressDTO, List<UserEntity>> converter = new AbstractConverter<AddressDTO, List<UserEntity>>() {

        @Override
        protected List<UserEntity> convert(AddressDTO source) {

            // at this step 'USER' table must exist and be filled!

            logger.info("alpha");
            
            List<UserEntity> residents       = new ArrayList<>();
            StringBuilder    fullNameBuilder = new StringBuilder();
            UserEntity userEntity = null;

            if (source == null) {
                return null;
            } else if (source.getResidents() == null) {
                return null;                    
            } else {
                
//                @Autowired
//                UserRepository userRepository;
                
                logger.info("bravo");
                logger.info("userEntity=" + userEntity);
                logger.info("userRepository=" + userRepository);
                // Lambda here
                //              source.getResidents().forEach((id, fullName) -> {
                userEntity = userRepository.findById(1L);
                
                logger.info("login" + userEntity.getName());
                
                residents.add(userEntity);
                //              });

                return residents;

            }
        }
    };

  protected void configure() {
      
/*      // at this step 'USER' table must exist and be filled!
      
      List<UserEntity> residents       = new ArrayList<>();
      StringBuilder    fullNameBuilder = new StringBuilder();
      
      // Lambda here
      source.getResidents().forEach((id, fullName) -> {
          UserEntity userEntity = userRepository.findById(id);
          residents.add(userEntity);
      });
      
      map().setResidents(residents);*/
      
      using(converter).map(source, destination.getResidents());
      
  }
}

