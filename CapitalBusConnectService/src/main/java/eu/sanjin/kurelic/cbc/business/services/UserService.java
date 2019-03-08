package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;

import java.time.LocalDate;

public interface UserService {

    SettingsUserForm getUserInformation(String username);

    boolean addUser(UserForm user);

    boolean updateUser(UserForm user);

    default boolean removeUser(UserForm user){
        return removeUser(user.getEmail());
    }

    boolean removeUser(String username);

    default boolean hasUser(UserForm user) {
        return hasUser(user.getEmail());
    }

    boolean hasUser(String username);

    //TODO
    void getUserLoginHistory(String username);

    void addUserLoginHistory();

    default void getAllUserLoginHistory() {
        getAllUserLoginHistory(LocalDate.MIN);
    }

    void getAllUserLoginHistory(LocalDate fromDate);

}
