package ru.mera.samples.infrastructure.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.ImageEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ImageCacheServiceImpl implements ImageCacheService {

  private static final Log logger = LogFactory.getLog(ImageCacheServiceImpl.class);

  private Map<String, SoftReference<ImageEntity>> images;
  private Map<Long, SoftReference<ImageEntity>> imagesIds;

  @PostConstruct
  public void init() {
    images = new HashMap<>();
    logger.info("ImageCacheService is starting...OK");
  }

  @PreDestroy
  public void destroy() {
    images.clear();
    images = null;
    imagesIds.clear();
    imagesIds = null;
    logger.info("ImageCacheService is stopping...OK");
  }

  public void addImage(Long id, String name, ImageEntity image) {
    SoftReference<ImageEntity> defaultImageRef = new SoftReference<>(image);
    images.put(name, defaultImageRef);
    imagesIds.put(id, defaultImageRef);
  }

  public Optional<ImageEntity> getImage(String name) {
    SoftReference<ImageEntity> imageSoftReference = images.get(name);
    return Optional.of(imageSoftReference.get());
  }

  public Optional<ImageEntity> getImage(Long id) {
    SoftReference<ImageEntity> imageSoftReference = imagesIds.get(id);
    return Optional.of(imageSoftReference.get());
  }
}
