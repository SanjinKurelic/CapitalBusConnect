package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;

import java.time.LocalDate;
import java.util.List;

public interface UserLoginHistoryDao {

  List<UserLoginHistory> getUserLoginHistory(String username, int offset, int limit);

  List<UserLoginHistory> getUserLoginHistory(String username, LocalDate date, int offset, int limit);

  List<UserLoginHistory> getAllLoginHistory(int offset, int limit);

  List<UserLoginHistory> getAllLoginHistory(LocalDate date, int offset, int limit);

  Long getUserLoginHistoryCount(String username);

  Long getAllLoginHistoryCount();

  void addUserLoginHistory(UserLoginHistory userLoginHistory);

}
