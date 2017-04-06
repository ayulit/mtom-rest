package ru.mera.samples.application.mappings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.entities.ImageEntity;


public class ImageToEntityMap extends PropertyMap<ImageDTO, ImageEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ImageToEntityMap.class);

    Converter<ImageDTO, byte[]> converter = new AbstractConverter<ImageDTO, byte[]>() {

        @Override
        protected byte[] convert(ImageDTO source) {
            
            if (source == null) {
                return null;
            } else if (source.getImage() == null) {
                return null;                    
            } else {
                
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                
                try {
                    ImageIO.write(source.getImage(), 
                                  StringUtils.getFilenameExtension(source.getName()), bos);
                } catch (IOException e) {
                    // FIXME perhaps will not be working here
                    logger.error("Exception occured while transforming image to bytes");
                }
                
                return bos.toByteArray();
                
            }
        }
    };
    
    protected void configure() {
        using(converter).map(source, destination.getImage());
    }

}

