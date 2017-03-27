package ru.mera.samples.domain.dao;

import ru.mera.samples.domain.entities.AbstractNamedEntity;

public interface NamedEntityRepository<T extends AbstractNamedEntity> extends EntityRepository<T> {

  T findByName(String name);

  boolean existsByName(String name);
}
