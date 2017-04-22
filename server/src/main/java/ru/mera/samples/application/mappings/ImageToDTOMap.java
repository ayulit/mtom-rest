package ru.mera.samples.application.mappings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.entities.ImageEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageToDTOMap extends PropertyMap<ImageEntity, ImageDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ImageToDTOMap.class);

    Converter<byte[], BufferedImage> converter = new AbstractConverter<byte[], BufferedImage>() {

        @Override
        protected BufferedImage convert(byte[] source) {
            
            if (source == null) {
                return null;
            } else {
                
                InputStream in = new ByteArrayInputStream(source);
                BufferedImage resultImage = null;
                
                try {
                    resultImage = ImageIO.read(in);
                } catch (IOException e) {
                    logger.error("Exception occured while transforming image from bytes");
                }
                
                return resultImage;
                
            }
        }
    };    
    
    protected void configure() {
        using(converter).map(source.getImage(), destination.getImage());
    }
}

