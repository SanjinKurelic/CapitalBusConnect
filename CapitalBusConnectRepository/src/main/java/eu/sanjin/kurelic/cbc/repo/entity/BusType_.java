package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusType.class)
public class BusType_ {

    public static volatile SingularAttribute<BusType, Integer> id;
    public static volatile SingularAttribute<BusType, String> name;
    public static volatile SingularAttribute<BusType, Integer> numberOfSeats;
    public static volatile SingularAttribute<BusType, Integer> childrenFriendly;
    public static volatile SingularAttribute<BusType, Boolean> petFriendly;
    public static volatile SingularAttribute<BusType, Boolean> operates;

}
