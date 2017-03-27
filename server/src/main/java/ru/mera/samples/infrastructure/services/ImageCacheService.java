package ru.mera.samples.infrastructure.services;


import ru.mera.samples.domain.entities.ImageEntity;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface ImageCacheService {

  void addImage(Long id, String name, ImageEntity image);

  Optional<ImageEntity> getImage(String name);

  Optional<ImageEntity> getImage(Long id);
}
