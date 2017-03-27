package ru.mera.samples.domain.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.AbstractEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Repository
@Transactional( readOnly = false )
public abstract class AbstractRepository<T extends AbstractEntity> implements EntityRepository<T> {

  private static final Log logger = LogFactory.getLog(AbstractRepository.class);

  protected Class<T> persistentClass;

  @PersistenceContext
  protected EntityManager entityManager;

  @SuppressWarnings( "unchecked" )
  public AbstractRepository() {
    this.persistentClass = (Class<T>) ( (ParameterizedType) getClass().getGenericSuperclass() )
        .getActualTypeArguments()[0];
  }

  @Override
  public T findById(Long id) {
    return entityManager.find(persistentClass, id);
  }

  @Override
  public List<T> findAll() {
    CriteriaQuery<T> criteria = getCriteriaQuery();
    TypedQuery<T>    allQuery = entityManager.createQuery(criteria);
    return allQuery.getResultList();
  }

  @Override
  public void save(T entity) {
    entityManager.persist(entity);
  }

  @Override
  public void update(T entity) {
    entityManager.merge(entity);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  @Override
  public Long count() {
    CriteriaQuery<Long> countCriteriaQuery = getCountCriteriaQuery();
    TypedQuery<Long>    query              = entityManager.createQuery(countCriteriaQuery);
    return query.getSingleResult();
  }

  @Override
  public boolean exists(Long id) {
    T entity = entityManager.find(persistentClass, id);
    return entity != null;
  }

  protected CriteriaQuery<T> getCriteriaQuery() {
    CriteriaBuilder  cb        = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq        = cb.createQuery(persistentClass);
    Root<T>          rootEntry = cq.from(persistentClass);
    return cq.select(rootEntry);
  }

  protected CriteriaQuery<Long> getCountCriteriaQuery() {
    CriteriaBuilder     cb        = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq        = cb.createQuery(Long.class);
    Root<T>             rootEntry = cq.from(persistentClass);
    return cq.select(cb.count(rootEntry));
  }

}
