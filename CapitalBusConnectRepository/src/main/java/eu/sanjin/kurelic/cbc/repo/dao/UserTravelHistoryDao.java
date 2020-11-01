package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface UserTravelHistoryDao {

  int TUPLE_USERNAME = 0;
  int TUPLE_NUMBER_OF_TRAVELS = 1;

  UserTravelHistory getUserTravelHistoryById(int id);

  List<UserTravelHistory> getUserTravelHistoryByIds(Integer... ids);

  List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit);

  List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit);

  List<Tuple> getTopUsersByTravels(int limit);

  Long getUserTravelHistoryCount(String username);

  Long getAllUserTravelHistoryCount();

  void addUserTravelHistory(UserTravelHistory userTravelHistory);

}
