package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;

import java.time.LocalDate;

public interface TripPricesDao {

    TripPrices getTripPrice(Integer tripDuration);

    TripPrices getTripPrice(Integer tripDuration, LocalDate date);

}
