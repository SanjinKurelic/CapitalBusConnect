package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription_;
import eu.sanjin.kurelic.cbc.repo.entity.Traffic_;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TrafficDescriptionDaoImpl implements TrafficDescriptionDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<TrafficDescription> getTrafficDescriptions(LocalDate date, String language, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<TrafficDescription> criteria = builder.createQuery(TrafficDescription.class);
    Root<TrafficDescription> root = criteria.from(TrafficDescription.class);

    // HQL = FROM TrafficDescription WHERE traffic.date > :date AND id.language = :language ORDER BY traffic.date ASC
    Predicate trafficDate = builder.greaterThanOrEqualTo(root.get(TrafficDescription_.traffic).get(Traffic_.date), date);
    Predicate equalLanguage = builder.equal(root.get(TrafficDescription_.id).get(LanguagePrimaryKey_.language), language);

    criteria.where(builder.and(trafficDate, equalLanguage));
    criteria.orderBy(builder.asc(root.get(TrafficDescription_.traffic).get(Traffic_.date)));

    return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
  }

}
