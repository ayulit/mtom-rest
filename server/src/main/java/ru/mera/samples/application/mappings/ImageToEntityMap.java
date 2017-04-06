package ru.mera.samples.application.mappings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.entities.ImageEntity;


public class ImageToEntityMap extends PropertyMap<ImageDTO, ImageEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ImageToEntityMap.class);

    Converter<String, String> converter = new AbstractConverter<String, String>() {

        @Override
        protected String convert(String source) {
            return source == null ? null : source.toUpperCase();
        }
    };
    
    protected void configure() {
    
/*    byte[] imageInByte = null;  
      
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
      ImageIO.write(source.getImage(), getImageFormat(source.getName()), bos);
      imageInByte = bos.toByteArray();
    } catch( Exception e ) {
      //logger.info("Exception occured while transforming image to bytes");
      //System.out.println("Exception occured while transforming image to bytes");
        

        
    }*/

        using(converter).map(source.getName(), destination.getName());

    }

    private String getImageFormat(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }
}

