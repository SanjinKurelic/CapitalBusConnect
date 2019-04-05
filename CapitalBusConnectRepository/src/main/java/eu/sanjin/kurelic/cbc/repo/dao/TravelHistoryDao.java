package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.*;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;

import java.time.LocalDate;
import java.util.List;

public interface TravelHistoryDao {

    UserTravelHistory getUserTravelHistoryById(int id);

    List<UserTravelHistory> getUserTravelHistoryById(Integer ...ids);

    List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit);

    List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit);

    int getUserTravelHistoryCount(String username);

    boolean addUserTravelHistory(UserTravelHistory userTravelHistory);

    TripHistory getTripHistory(int id);

    void addOrUpdateTripHistory(TripHistory tripHistory);

    default int hasTripHistory(BusSchedule busSchedule, LocalDate date, TripType tripType) {
        return hasTripHistory(busSchedule.getId(), date, tripType.getId());
    }

    int hasTripHistory(int busScheduleId, LocalDate date, int tripTypeId);

    PayingMethod getPayingMethodByName(PayingMethodValues value);

}
