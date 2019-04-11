package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TripType.class)
public class TripType_ {

    public static volatile SingularAttribute<TripType, Integer> id;
    public static volatile SingularAttribute<TripType, String> name;

}
