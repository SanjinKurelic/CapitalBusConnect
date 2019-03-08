package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;

import java.time.LocalDate;
import java.util.List;

public interface UserLoginInfoDao {

    List<UserLoginHistory> getUserLoginHistory(String username);

    default List<UserLoginHistory> getUserLoginHistory(User user){
        return getUserLoginHistory(user.getUsername());
    }

    boolean addUserLoginHistory(UserLoginHistory userLoginHistory);

    default List<UserLoginHistory> getAllUserLoginHistory() {
        return getAllUserLoginHistory(LocalDate.MIN);
    }

    List<UserLoginHistory> getAllUserLoginHistory(LocalDate fromDate);

}
