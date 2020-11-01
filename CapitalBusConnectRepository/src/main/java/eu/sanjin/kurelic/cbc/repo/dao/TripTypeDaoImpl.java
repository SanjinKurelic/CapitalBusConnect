package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.entity.TripType_;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class TripTypeDaoImpl implements TripTypeDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public TripType getTripType(TripTypeValue value) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<TripType> criteria = builder.createQuery(TripType.class);
    Root<TripType> root = criteria.from(TripType.class);
    // HQL = FROM TripType WHERE name = :name
    criteria.where(builder.equal(root.get(TripType_.name), value.name()));

    return entityManager.createQuery(criteria).getSingleResult();
  }

}
