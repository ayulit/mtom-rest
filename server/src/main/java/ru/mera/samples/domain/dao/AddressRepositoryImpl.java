package ru.mera.samples.domain.dao;

import org.apache.log4j.Logger;

import ru.mera.samples.domain.entities.AddressEntity;
import org.springframework.stereotype.Repository;


@Repository
public class AddressRepositoryImpl extends AbstractRepository<AddressEntity> implements AddressRepository {

  private static final Logger logger = Logger.getLogger(AddressRepositoryImpl.class);


}
