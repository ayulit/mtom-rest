package ru.mera.samples.domain.dao;

import org.apache.log4j.Logger;

import ru.mera.samples.domain.entities.ImageEntity;
import org.springframework.stereotype.Repository;


@Repository
public class ImageRepositoryImpl extends AbstractNamedRepository<ImageEntity> implements ImageRepository {

  private static final Logger logger = Logger.getLogger(ImageRepositoryImpl.class);


}
