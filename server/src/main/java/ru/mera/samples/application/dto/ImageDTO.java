package ru.mera.samples.application.dto;

import java.awt.image.BufferedImage;

public class ImageDTO extends AbstractDTO {

  private String name;

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
