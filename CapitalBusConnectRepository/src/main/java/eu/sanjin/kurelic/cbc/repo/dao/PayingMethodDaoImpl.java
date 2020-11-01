package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod;
import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod_;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class PayingMethodDaoImpl implements PayingMethodDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public PayingMethod getPayingMethodByName(PayingMethodValues value) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<PayingMethod> criteria = builder.createQuery(PayingMethod.class);
    Root<PayingMethod> root = criteria.from(PayingMethod.class);

    // HQL = FROM PayingMethod WHERE name = :name
    criteria.where(builder.equal(root.get(PayingMethod_.name), value.name()));

    return entityManager.createQuery(criteria).getSingleResult();
  }

}
