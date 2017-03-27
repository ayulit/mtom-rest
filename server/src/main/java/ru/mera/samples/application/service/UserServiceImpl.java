/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.mera.samples.application.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mera.samples.application.dto.UserDTO;
import ru.mera.samples.domain.dao.UserRepository;
import ru.mera.samples.domain.entities.UserEntity;


public class UserServiceImpl extends AbstractServiceImpl<UserDTO, UserEntity> implements UserService {

  private static final Log logger = LogFactory.getLog(UserServiceImpl.class);


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public UserDTO load(String name) {
    logger.info("Loading user " + name);
    UserEntity userEntity    = userRepository.findByName(name);
    UserDTO   userDTO = modelMapper.map(userEntity, UserDTO.class);
    return userDTO;
  }

  @Override
  public UserRepository getRepository() {
    return userRepository;
  }
}
