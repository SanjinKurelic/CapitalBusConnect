package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;

import java.time.Duration;
import java.time.LocalDate;

public interface TripPricesDao {

    default TripPrices getTripPrice(Duration tripDuration) {
        return getTripPrice(tripDuration, LocalDate.now());
    }

    TripPrices getTripPrice(Duration tripDuration, LocalDate date);

}
