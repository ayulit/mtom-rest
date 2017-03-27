package ru.mera.samples.domain.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.AddressEntity;
import org.springframework.stereotype.Repository;


@Repository
public class AddressRepositoryImpl extends AbstractRepository<AddressEntity> implements AddressRepository {

  private static final Log logger = LogFactory.getLog(AddressRepositoryImpl.class);


}
