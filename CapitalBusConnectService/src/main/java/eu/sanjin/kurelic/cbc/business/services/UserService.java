package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserFormItemException;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm;
import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public interface UserService {

    SettingsUserForm getUserInformation(String username);

    User getUser(String username);

    void addUser(UserForm user) throws InvalidUserException, InvalidUserFormItemException;

    void updateUser(UserForm user) throws InvalidUserException, InvalidUserFormItemException;

    //void removeUser(String username | UserForm user) throws Exception;

    @Transactional
    default boolean hasUser(UserForm user) {
        if (Objects.isNull(user)) {
            return false;
        }
        return hasUser(user.getEmail());
    }

    boolean hasUser(String username);

    String[] searchUserByName(String partialName, int numberOfSearchResults);

}
