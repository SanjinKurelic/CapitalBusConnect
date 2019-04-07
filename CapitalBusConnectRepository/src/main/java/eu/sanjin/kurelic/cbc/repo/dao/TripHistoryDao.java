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

    default int hasTripHistory(BusSchedule busSchedule, LocalDate date, TripType tripType) {
        return hasTripHistory(busSchedule.getId(), date, tripType.getId());
    }

    int hasTripHistory(int busScheduleId, LocalDate date, int tripTypeId);

    List<Tuple> getMostTraveledSchedules(int limit);

    List<Tuple> getLastFilledTripHistory(int limit);

    Long getTripHistoryCount();

}
