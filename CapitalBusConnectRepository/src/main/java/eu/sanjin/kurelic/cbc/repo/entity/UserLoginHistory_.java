package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LoginHistoryPrimaryKey;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserLoginHistory.class)
public class UserLoginHistory_ {

  public static volatile SingularAttribute<UserLoginHistory, LoginHistoryPrimaryKey> id;
  public static volatile SingularAttribute<UserLoginHistory, String> ipAddress;

}
