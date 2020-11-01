package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(TripHistory.class)
public class TripHistory_ {

  public static volatile SingularAttribute<TripHistory, Integer> id;
  public static volatile SingularAttribute<TripHistory, BusSchedule> busSchedule;
  public static volatile SingularAttribute<TripHistory, LocalDate> date;
  public static volatile SingularAttribute<TripHistory, Integer> numberOfSeats;
  public static volatile SingularAttribute<TripHistory, TripType> tripType;

}
