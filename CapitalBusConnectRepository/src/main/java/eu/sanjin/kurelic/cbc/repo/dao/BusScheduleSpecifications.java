package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine_;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule_;
import eu.sanjin.kurelic.cbc.repo.entity.City_;
import eu.sanjin.kurelic.cbc.repo.entity.TripType_;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class BusScheduleSpecifications {

  public static Specification<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId) {
    return (root, criteriaQuery, criteriaBuilder) -> {
      Predicate abTrip = criteriaBuilder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValue.A_TO_B.name());
      Predicate baTrip = criteriaBuilder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValue.B_TO_A.name());
      Predicate roundTrip = criteriaBuilder.equal(root.get(BusSchedule_.tripType).get(TripType_.name), TripTypeValue.ROUND_TRIP.name());
      Predicate operates = criteriaBuilder.equal(root.get(BusSchedule_.operates), Boolean.TRUE);

      Path<Integer> city1 = root.get(BusSchedule_.busLine).get(BusLine_.city1).get(City_.id);
      Path<Integer> city2 = root.get(BusSchedule_.busLine).get(BusLine_.city2).get(City_.id);

      // HQL => busLine.city1.id = :city1 AND busLine.city2.id = :city2
      Predicate orderedCheck = criteriaBuilder.and(criteriaBuilder.equal(city1, fromCityId), criteriaBuilder.equal(city2, toCityId));
      // HQL => busLine.city1.id = :city2 AND busLine.city2.id = :city1
      Predicate oppositeCheck = criteriaBuilder.and(criteriaBuilder.equal(city1, toCityId), criteriaBuilder.equal(city2, fromCityId));

      // HQL => tripType.name = 'TripTypeValue.A_TO_B' AND <ordered check>
      Predicate searchAbTrip = criteriaBuilder.and(abTrip, orderedCheck);
      // HQL => tripType.name = 'TripTypeValue.B_TO_A' AND <opposite check>
      Predicate searchBaTrip = criteriaBuilder.and(baTrip, oppositeCheck);
      // HQL => tripType.name = 'TripTypeValue.ROUND_TRIP' AND <ordered check> OR <opposite check>
      Predicate searchRoundTrip = criteriaBuilder.and(roundTrip, criteriaBuilder.or(orderedCheck, oppositeCheck));

      // HQL => <search AB trip> OR <search BA trip> OR <search ROUND trip>
      Predicate combinedSearch = criteriaBuilder.or(searchAbTrip, searchBaTrip, searchRoundTrip);

      // HQL => <combined search> AND operates
      return criteriaBuilder.and(combinedSearch, operates);
    };
  }
}
