package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.TripType;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface TripHistoryDao {

    TripHistory getTripHistory(int id);

    void addOrUpdateTripHistory(TripHistory tripHistory);

    default Integer getTripHistoryIdOrNull(BusSchedule busSchedule, LocalDate date, TripType tripType) {
        return getTripHistoryIdOrNull(busSchedule.getId(), date, tripType.getId());
    }

    Integer getTripHistoryIdOrNull(Integer busScheduleId, LocalDate date, Integer tripTypeId);

    List<Tuple> getMostTraveledSchedules(int limit);

    List<Tuple> getLastFilledTripHistory(int limit);

    Long getTripHistoryCount();

}
