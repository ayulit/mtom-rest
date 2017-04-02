package ru.mera.samples.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mera.samples.domain.entities.ImageEntity;
import org.springframework.stereotype.Repository;


@Repository
public class ImageRepositoryImpl extends AbstractNamedRepository<ImageEntity> implements ImageRepository {

  private static final Logger logger = LoggerFactory.getLogger(ImageRepositoryImpl.class);


}
