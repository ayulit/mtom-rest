package ru.mera.samples.domain.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.mera.samples.domain.entities.AbstractNamedEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;


@Repository
@Transactional( readOnly = false )
public abstract class AbstractNamedRepository<T extends AbstractNamedEntity> extends AbstractRepository<T> implements NamedEntityRepository<T> {

  private static final Log logger = LogFactory.getLog(AbstractNamedRepository.class);

  @PersistenceContext
  protected EntityManager entityManager;


  @Override
  public T findByName(String name) {
    CriteriaBuilder  criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = getCriteriaQuery();
    ParameterExpression<String> pname = criteriaBuilder.parameter(String.class, name);
    criteriaQuery.where(criteriaBuilder.equal(pname, name));
    return entityManager.createQuery(criteriaQuery).getSingleResult();
  }

  @Override
  public boolean existsByName(String name) {
    T entity = findByName(name);
    return entity != null;
  }
}
