package ru.mera.samples.application.mappings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.modelmapper.PropertyMap;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.entities.ImageEntity;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageToEntityMap extends PropertyMap<ImageDTO, ImageEntity> {

  private static final Logger logger = LoggerFactory.getLogger(ImageToEntityMap.class);

  protected void configure() {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(source.getImage(), getImageFormat(source.getName()), bos);
    } catch( IOException e ) {
      logger.error("Exception occured while transforming image to bytes");
    }
    map().setImage(bos.toByteArray());
  }

  private String getImageFormat(String name) {
    return name.substring(name.lastIndexOf(".") + 1);
  }
}

