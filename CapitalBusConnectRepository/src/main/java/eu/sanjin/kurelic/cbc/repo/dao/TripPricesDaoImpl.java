package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import eu.sanjin.kurelic.cbc.repo.entity.TripPrices_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Duration;
import java.time.LocalDate;

@Repository
public class TripPricesDaoImpl implements TripPricesDao {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Get trip price based on trip duration and date from which price is valid
   *
   * @param tripDuration - duration of a trip
   * @param date         - optional parameter, if we want to know price of journey in past
   * @return - price or throw exception (every trip duration should have price)
   */
  @Override
  public TripPrices getTripPrice(Duration tripDuration, LocalDate date) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<TripPrices> criteria = builder.createQuery(TripPrices.class);
    Root<TripPrices> root = criteria.from(TripPrices.class);

    // HQL = FROM TripPrices WHERE tripDuration <= :duration AND fromDate <= :date ORDER BY tripDuration DESC, fromDate DESC
    Predicate tripDurationPredicate = builder.lessThanOrEqualTo(root.get(TripPrices_.tripDuration), tripDuration);
    Predicate datePredicate = builder.lessThanOrEqualTo(root.get(TripPrices_.fromDate), date);
    criteria.where(builder.and(tripDurationPredicate, datePredicate));
    criteria.orderBy(builder.desc(root.get(TripPrices_.tripDuration)), builder.desc(root.get(TripPrices_.fromDate)));

    return entityManager.createQuery(criteria).setMaxResults(1).getSingleResult();
  }

}
