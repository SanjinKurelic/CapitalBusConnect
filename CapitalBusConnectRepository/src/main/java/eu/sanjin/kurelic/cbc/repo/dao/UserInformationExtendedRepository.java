package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;

public interface UserInformationExtendedRepository {

  void updateUserInformationWithoutPassword(User user);

}
