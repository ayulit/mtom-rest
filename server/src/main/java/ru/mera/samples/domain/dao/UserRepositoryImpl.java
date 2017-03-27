package ru.mera.samples.domain.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractNamedRepository<UserEntity> implements UserRepository {

  private static final Log logger = LogFactory.getLog(UserRepositoryImpl.class);


}
