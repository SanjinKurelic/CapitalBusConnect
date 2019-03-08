package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;

public interface UserInfoDao {

    User getUserInformation(String username);

    default boolean hasUserInformation(String username){
        return hasUserInformation(getUserInformation(username));
    }

    boolean hasUserInformation(User user);

    boolean addUserInformation(User user);

    boolean updateUserInformation(User user);

    boolean removeUserInformation(User user);

    default boolean removeUserInformation(String username) {
        return removeUserInformation(getUserInformation(username));
    }

}
