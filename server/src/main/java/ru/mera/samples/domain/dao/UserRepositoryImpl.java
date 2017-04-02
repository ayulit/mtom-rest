package ru.mera.samples.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mera.samples.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractNamedRepository<UserEntity> implements UserRepository {

  private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);


}
