package ru.mera.samples.application.mappings;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.PropertyMap;
import ru.mera.samples.application.dto.ImageDTO;
import ru.mera.samples.domain.entities.ImageEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageToDTOMap extends PropertyMap<ImageEntity, ImageDTO> {

  private static final Log logger = LogFactory.getLog(ImageToDTOMap.class);

  protected void configure() {
    BufferedImage resultImage = null;
    try( ByteArrayInputStream byteArr = new ByteArrayInputStream(source.getImage()) ) {
      resultImage = ImageIO.read(byteArr);
    } catch( IOException e ) {
      logger.error("Exception occured while transforming image from bytes");
    }
    map().setImage(resultImage);
  }
}

