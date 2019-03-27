package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;

import java.time.LocalDate;
import java.util.List;

public interface TravelHistoryDao {

    List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit);

    List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit);

    int getUserTravelHistoryCount(String username);

}
