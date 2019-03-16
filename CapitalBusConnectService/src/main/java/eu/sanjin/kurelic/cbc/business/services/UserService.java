package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public interface UserService {

    SettingsUserForm getUserInformation(String username);

    User getUser(String username);

    boolean addUser(UserForm user);

    boolean updateUser(UserForm user);

    @Transactional
    default boolean removeUser(UserForm user){
        return removeUser(user.getEmail());
    }

    boolean removeUser(String username);

    @Transactional
    default boolean hasUser(UserForm user) {
        return hasUser(user.getEmail());
    }

    boolean hasUser(String username);

    List<UserLoginHistory> getUserLoginHistory(String username);

    boolean addUserLoginHistory(UserLoginHistory userLoginHistory);

    @Transactional
    default List<UserLoginHistory> getAllUserLoginHistory() {
        return getAllUserLoginHistory(LocalDate.MIN);
    }

    List<UserLoginHistory> getAllUserLoginHistory(LocalDate fromDate);

    // Utility
    User convertUserFormToUser(UserForm userForm);

}
