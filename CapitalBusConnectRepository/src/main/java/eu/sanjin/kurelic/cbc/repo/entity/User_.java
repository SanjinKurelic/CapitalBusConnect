package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(User.class)
public class User_ {

  public static volatile SingularAttribute<User, String> username;
  public static volatile SingularAttribute<User, String> password;
  public static volatile SingularAttribute<User, Boolean> enabled;
  public static volatile SingularAttribute<User, String> name;
  public static volatile SingularAttribute<User, String> surname;
  public static volatile SingularAttribute<User, LocalDate> dateOfBirth;
  public static volatile SingularAttribute<User, Boolean> receiveNewsletter;

}
