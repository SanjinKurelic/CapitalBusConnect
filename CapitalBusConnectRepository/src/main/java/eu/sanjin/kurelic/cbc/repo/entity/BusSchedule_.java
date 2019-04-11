package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(BusSchedule.class)
public class BusSchedule_ {

    public static volatile SingularAttribute<BusSchedule, Integer> id;
    public static volatile SingularAttribute<BusSchedule, LocalDate> fromTime;
    public static volatile SingularAttribute<BusSchedule, LocalDate> toTime;
    public static volatile SingularAttribute<BusSchedule, BusLine> busLine;
    public static volatile SingularAttribute<BusSchedule, TripType> tripType;
    public static volatile SingularAttribute<BusSchedule, Boolean> workweek;
    public static volatile SingularAttribute<BusSchedule, Boolean> weekend;
    public static volatile SingularAttribute<BusSchedule, BusType> busType;
    public static volatile SingularAttribute<BusSchedule, Boolean> operates;

}
