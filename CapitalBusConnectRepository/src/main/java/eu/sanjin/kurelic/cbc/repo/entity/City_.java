package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(City.class)
public class City_ {

    public static volatile SingularAttribute<City, Integer> id;
    public static volatile SingularAttribute<City, String> imageName;
    public static volatile SingularAttribute<City, Country> country;

}
