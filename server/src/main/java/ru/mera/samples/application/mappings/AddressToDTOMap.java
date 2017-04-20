package ru.mera.samples.application.mappings;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mera.samples.application.dto.AddressDTO;
import ru.mera.samples.domain.entities.AddressEntity;
import ru.mera.samples.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressToDTOMap extends PropertyMap<AddressEntity, AddressDTO> {

    private static final Logger logger = LoggerFactory.getLogger(AddressToDTOMap.class);

    public static final String LITTERAL = ", ";

    Converter<AddressEntity, Map<Long, String>> converter = new AbstractConverter<AddressEntity, Map<Long, String>>() {

        @Override
        protected Map<Long, String> convert(AddressEntity source) {

            Map<Long, String> residents       = new HashMap<>();
            

            if (source == null) {
                return null;
            } else if (source.getResidents() == null) {
                return null;                    
            } else {
                
                // Lambda here
                source.getResidents().forEach(userEntity -> {
                    StringBuilder     fullNameBuilder = new StringBuilder();
                    fullNameBuilder.append(userEntity.getLastName()).append(LITTERAL).append(userEntity.getFirstName());
                    residents.put(userEntity.getId(), fullNameBuilder.toString());
                });
                
                return residents;
            }
        }
    };

    protected void configure() {
        using(converter).map(source, destination.getResidents());
    }
}

