package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule_;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory_;
import eu.sanjin.kurelic.cbc.repo.entity.TripType_;
import eu.sanjin.kurelic.cbc.repo.utility.RowCounter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TripHistoryDaoImpl implements TripHistoryDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public TripHistory getTripHistory(int id) {
    return entityManager.unwrap(Session.class).get(TripHistory.class, id);
  }

  @Override
  public void addOrUpdateTripHistory(TripHistory tripHistory) {
    entityManager.unwrap(Session.class).saveOrUpdate(tripHistory);
  }

  @Override
  public Integer getTripHistoryIdOrNull(Integer busScheduleId, LocalDate date, Integer tripTypeId) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<TripHistory> criteria = builder.createQuery(TripHistory.class);
    Root<TripHistory> root = criteria.from(TripHistory.class);

    // HQL = FROM TripHistory WHERE busSchedule.id = :busScheduleId AND date = :date AND tripType.id = :tripTypeId
    Predicate busSchedulePredicate = builder.equal(root.get(TripHistory_.busSchedule).get(BusSchedule_.id), busScheduleId);
    Predicate datePredicate = builder.equal(root.get(TripHistory_.date), date);
    Predicate tripTypePredicate = builder.equal(root.get(TripHistory_.tripType).get(TripType_.id), tripTypeId);
    criteria.where(builder.and(busSchedulePredicate, datePredicate, tripTypePredicate));

    var list = entityManager.createQuery(criteria).getResultList();
    if (list.size() != 1) {
      return null;
    }
    return list.get(0).getId();
  }

  /**
   * Return list of most traveled schedules (grouped by bus line and operating time)
   *
   * @param limit - limit the results
   * @return Tuple - first parameter is TripHistory and second parameter is counter
   */
  @Override
  public List<Tuple> getMostTraveledSchedules(int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
    Root<TripHistory> root = criteria.from(TripHistory.class);
    Expression<Long> counter = builder.count(root);

    criteria.multiselect(root, counter);
    criteria.groupBy(root.get(TripHistory_.busSchedule), root.get(TripHistory_.tripType));
    criteria.orderBy(builder.desc(counter));

    return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
  }

  /**
   * Return list of trips that operated with full bus capacity
   *
   * @param limit - limit the results
   * @return Tuple - first parameters is TripHistory and second parameter is counter
   */
  @Override
  public List<Tuple> getLastFilledTripHistory(int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
    Root<TripHistory> root = criteria.from(TripHistory.class);

    criteria.multiselect(root, builder.count(root)); // TripHistory => counter
    criteria.where(builder.equal(root.get(TripHistory_.numberOfSeats), 0)); // where number of left seats = 0
    criteria.groupBy(root.get(TripHistory_.busSchedule), root.get(TripHistory_.tripType)); // group by schedule & direction
    criteria.orderBy(builder.desc(root.get(TripHistory_.date))); // get last results by ordering date

    return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
  }

  @Override
  public Long getTripHistoryCount() {
    return RowCounter.countNumberOfRows(entityManager.unwrap(Session.class), TripHistory.class);
  }

}
