package ru.mera.samples.domain.dao;

import ru.mera.samples.domain.entities.AbstractEntity;

import java.util.List;

public interface EntityRepository<T extends AbstractEntity> {

  T findById(Long id);

  List<T> findAll();

  void save(T entity);

  void update(T entity);

  void delete(T entity);

  Long count();

  boolean exists(Long id);
}
