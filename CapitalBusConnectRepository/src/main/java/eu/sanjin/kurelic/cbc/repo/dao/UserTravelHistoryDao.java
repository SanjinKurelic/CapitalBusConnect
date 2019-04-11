package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface UserTravelHistoryDao {

    UserTravelHistory getUserTravelHistoryById(int id);

    List<UserTravelHistory> getUserTravelHistoryByIds(Integer... ids);

    List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit);

    List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit);

    List<Tuple> getTopUsersByTravels(int limit);

    Long getUserTravelHistoryCount(String username);

    Long getAllUserTravelHistoryCount();

    void addUserTravelHistory(UserTravelHistory userTravelHistory);

}
