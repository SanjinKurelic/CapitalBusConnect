package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Authorities.class)
public class Authorities_ {

    public static volatile SingularAttribute<BusLine, User> username;
    public static volatile SingularAttribute<BusLine, String> authority;

}
