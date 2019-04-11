package eu.sanjin.kurelic.cbc.repo.entity.composite;

import eu.sanjin.kurelic.cbc.repo.entity.User;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(LoginHistoryPrimaryKey.class)
public class LoginHistoryPrimaryKey_ {

    public static volatile SingularAttribute<LoginHistoryPrimaryKey, User> username;
    public static volatile SingularAttribute<LoginHistoryPrimaryKey, LocalDateTime> dateTime;

}
