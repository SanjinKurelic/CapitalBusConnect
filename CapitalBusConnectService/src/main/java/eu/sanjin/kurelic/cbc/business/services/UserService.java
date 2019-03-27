package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

    // Login history
    boolean addUserLoginHistory(UserLoginHistory userLoginHistory);

    InfoItems getUserLoginHistory(String username, LocalDate date, int pageNumber, int limit);

    InfoItems getAllLoginHistory(LocalDate date, int pageNumber, int limit);

    int getAllLoginHistoryCount();

    int getUserLoginHistoryCount(String username);

    // Utility
    User convertUserFormToUser(UserForm userForm);

}
