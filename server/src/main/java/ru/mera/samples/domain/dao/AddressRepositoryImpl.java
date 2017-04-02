package ru.mera.samples.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mera.samples.domain.entities.AddressEntity;
import org.springframework.stereotype.Repository;


@Repository
public class AddressRepositoryImpl extends AbstractRepository<AddressEntity> implements AddressRepository {

  private static final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);


}
