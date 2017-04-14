package ru.mera.samples.application.dto;

import java.awt.image.BufferedImage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ImageDTO extends AbstractDTO {

  private String name;

  @JsonSerialize(using=ImageSerializer.class)
  @JsonDeserialize(using = ImageDeserializer.class)
  private BufferedImage image;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
