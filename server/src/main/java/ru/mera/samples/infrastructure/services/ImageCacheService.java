package ru.mera.samples.infrastructure.services;


import java.util.Optional;
import ru.mera.samples.domain.entities.ImageEntity;

public interface ImageCacheService {

  void addImage(Long id, String name, ImageEntity image);

  Optional<ImageEntity> getImage(String name);

  Optional<ImageEntity> getImage(Long id);
}
