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
import ru.mera.samples.application.dto.AbstractDTO;
import ru.mera.samples.domain.dao.EntityRepository;
import ru.mera.samples.domain.entities.AbstractEntity;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractServiceImpl<T extends AbstractDTO, E extends AbstractEntity>
    implements AbstractService<T>
{

  private static final Log logger = LogFactory.getLog(AbstractServiceImpl.class);

  @Autowired
  protected ModelMapper modelMapper;

  protected Class<T> dtoClass;

  protected Class<E> entityClass;

  @SuppressWarnings( "unchecked" )
  public AbstractServiceImpl() {
    this.dtoClass = (Class<T>) ( (ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
    this.entityClass = (Class<E>) ( (ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[1];
  }

  @Override
  public void create(T address) {
    E abstractEntity = modelMapper.map(address, entityClass);
    getRepository().save(abstractEntity);
  }

  @Override
  public T read(Long id) {
    AbstractEntity abstractEntity = getRepository().findById(id);
    T              addressDTO     = modelMapper.map(abstractEntity, dtoClass);
    return addressDTO;
  }

  @Override
  public List<T> readAll() {
    List<E> entityList = getRepository().findAll();
    List<T> dtoList    = new ArrayList<>();
    entityList.forEach(e -> dtoList.add(modelMapper.map(e, dtoClass)));
    return dtoList;
  }

  @Override
  public void update(T address) {
    E abstractEntity = modelMapper.map(address, entityClass);
    getRepository().update(abstractEntity);
  }

  @Override
  public void delete(Long id) {
    E abstractEntity = getRepository().findById(id);
    getRepository().save(abstractEntity);
  }

  protected abstract EntityRepository<E> getRepository();
}
