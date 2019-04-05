package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDao {

    BusSchedule getSchedule(Integer id);

    List<BusSchedule> getSchedules(Integer ...ids);

    List<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId);

    TripPrices getTripPrice(Integer tripDuration);

    TripPrices getTripPrice(Integer tripDuration, LocalDate date);

    TripType getTripType(TripTypeValues value);

}
