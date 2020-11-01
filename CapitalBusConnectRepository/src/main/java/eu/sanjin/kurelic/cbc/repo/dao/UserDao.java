package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;

import java.util.List;

public interface UserDao {

  User getUserInformation(String username);

  default boolean hasUserInformation(String username) {
    return hasUserInformation(getUserInformation(username));
  }

  boolean hasUserInformation(User user);

  void addUserInformation(User user);

  void updateUserInformation(User user);

  void updateUserInformationWithoutPassword(User user);

  void removeUserInformation(User user);

  default void removeUserInformation(String username) {
    removeUserInformation(getUserInformation(username));
  }

  List<User> searchUserInformation(String partialUsername, int limit);

}
