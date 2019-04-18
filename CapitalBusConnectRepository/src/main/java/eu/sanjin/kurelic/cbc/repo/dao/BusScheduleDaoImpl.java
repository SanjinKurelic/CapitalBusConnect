package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.*;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BusScheduleDaoImpl implements BusScheduleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BusSchedule getSchedule(int id) {
        return entityManager.unwrap(Session.class).get(BusSchedule.class, id);
    }

    @Override
    public List<BusSchedule> getSchedules(Integer... ids) {
        // If there is no ID-s supplied
        if (Objects.isNull(ids) || ids.length < 1) {
            return new ArrayList<>();
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<BusSchedule> criteria = builder.createQuery(BusSchedule.class);
        Root<BusSchedule> root = criteria.from(BusSchedule.class);
        criteria.where(root.get(BusSchedule_.id).in((Object[]) ids));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId) {
        // If there are no cities supplied
        if (Objects.isNull(fromCityId) || Objects.isNull(toCityId)) {
            return new ArrayList<>();
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<BusSchedule> criteria = builder.createQuery(BusSchedule.class);
        Root<BusSchedule> root = criteria.from(BusSchedule.class);

        Predicate abTrip = builder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValues.A_TO_B.name());
        Predicate baTrip = builder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValues.B_TO_A.name());
        Predicate roundTrip = builder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValues.ROUND_TRIP.name());
        Predicate operates = builder.equal(root.get(BusSchedule_.operates), Boolean.TRUE);

        Path<Integer> city1 = root.get(BusSchedule_.busLine).get(BusLine_.city1).get(City_.id);
        Path<Integer> city2 = root.get(BusSchedule_.busLine).get(BusLine_.city2).get(City_.id);

        // HQL => busLine.city1.id = :city1 AND busLine.city2.id = :city2
        Predicate orderedCheck = builder.and(builder.equal(city1, fromCityId), builder.equal(city2, toCityId));
        // HQL => busLine.city1.id = :city2 AND busLine.city2.id = :city1
        Predicate oppositeCheck = builder.and(builder.equal(city1, toCityId), builder.equal(city2, fromCityId));

        // HQL => tripType.name = 'TripTypeValues.A_TO_B' AND <ordered check>
        Predicate searchAbTrip = builder.and(abTrip, orderedCheck);
        // HQL => tripType.name = 'TripTypeValues.B_TO_A' AND <opposite check>
        Predicate searchBaTrip = builder.and(baTrip, oppositeCheck);
        // HQL => tripType.name = 'TripTypeValues.B_TO_A' AND <ordered check> OR <opposite check>
        Predicate searchRoundTrip = builder.and(roundTrip, builder.or(orderedCheck, oppositeCheck));

        // HQL => <search AB trip> OR <search BA trip> OR <search ROUND trip>
        Predicate combinedSearch = builder.or(searchAbTrip, searchBaTrip, searchRoundTrip);

        // HQL => <combined search> AND operates
        criteria.where(builder.and(combinedSearch, operates));
        criteria.orderBy(builder.asc(root.get(BusSchedule_.fromTime)));

        return entityManager.createQuery(criteria).getResultList();
    }

}
