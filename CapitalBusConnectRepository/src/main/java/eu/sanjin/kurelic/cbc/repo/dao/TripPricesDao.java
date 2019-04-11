package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;

import java.time.LocalDate;

public interface TripPricesDao {

    default TripPrices getTripPrice(Integer tripDuration) {
        return getTripPrice(tripDuration, LocalDate.now());
    }

    TripPrices getTripPrice(Integer tripDuration, LocalDate date);

}
