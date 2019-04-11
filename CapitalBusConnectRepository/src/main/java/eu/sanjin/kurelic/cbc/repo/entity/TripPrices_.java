package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(TripPrices.class)
public class TripPrices_ {

    public static volatile SingularAttribute<TripPrices, Integer> id;
    public static volatile SingularAttribute<TripPrices, Integer> tripDuration;
    public static volatile SingularAttribute<TripPrices, LocalDate> fromDate;
    public static volatile SingularAttribute<TripPrices, Double> price;

}
