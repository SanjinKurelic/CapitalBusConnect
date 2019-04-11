package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TrafficType.class)
public class TrafficType_ {

    public static volatile SingularAttribute<TrafficType, Integer> id;
    public static volatile SingularAttribute<TrafficType, String> name;

}
