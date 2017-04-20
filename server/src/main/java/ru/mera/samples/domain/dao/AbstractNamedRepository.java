package ru.mera.samples.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.samples.domain.entities.AbstractNamedEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
@Transactional( readOnly = false )
public abstract class AbstractNamedRepository<T extends AbstractNamedEntity> extends AbstractRepository<T> implements NamedEntityRepository<T> {

  private static final Logger logger = LoggerFactory.getLogger(AbstractNamedRepository.class);

  @PersistenceContext
  protected EntityManager entityManager;

  @Override
  public T findByName(String name) {
      CriteriaBuilder             cb              = entityManager.getCriteriaBuilder();
      CriteriaQuery<T>            cq              = cb.createQuery(persistentClass);
      Root<T>                     rootEntry       = cq.from(persistentClass);
      // "name" - column, name - variable
      cq.select(rootEntry).where(cb.equal(rootEntry.get("name"), name));
      return entityManager.createQuery(cq).getSingleResult();
  }

  @Override
  public boolean existsByName(String name) {
    T entity = findByName(name);
    return entity != null;
  }
}
