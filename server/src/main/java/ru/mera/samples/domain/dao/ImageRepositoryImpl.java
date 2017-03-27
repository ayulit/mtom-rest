package ru.mera.samples.domain.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.ImageEntity;
import org.springframework.stereotype.Repository;


@Repository
public class ImageRepositoryImpl extends AbstractNamedRepository<ImageEntity> implements ImageRepository {

  private static final Log logger = LogFactory.getLog(ImageRepositoryImpl.class);


}
