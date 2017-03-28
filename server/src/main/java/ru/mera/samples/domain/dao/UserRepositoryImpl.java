package ru.mera.samples.domain.dao;

import org.apache.log4j.Logger;

import ru.mera.samples.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractNamedRepository<UserEntity> implements UserRepository {

  private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);


}
