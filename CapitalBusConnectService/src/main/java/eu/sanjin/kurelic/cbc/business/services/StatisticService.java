package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;

import java.util.Locale;

public interface StatisticService {

  InfoItems getTopUsersByTravels(int limit);

  InfoItems getTopBusLinesByTravelling(Locale language, int limit);

  InfoItems getOverbookedBusLines(Locale language, int limit);

  Long getTotalNumberOfPassengers();

  Long getTotalNumberOfTrips();

}
