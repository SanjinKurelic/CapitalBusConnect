package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Duration;
import java.time.LocalDate;

public interface TripPricesRepository extends JpaRepository<TripPrices, Integer> {

  TripPrices findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(Duration tripDuration, LocalDate date);

  default TripPrices findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(Duration tripDuration) {
    return findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(tripDuration, LocalDate.now());
  }

}
