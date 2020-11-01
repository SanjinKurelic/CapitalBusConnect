package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Traffic.class)
public class Traffic_ {

  public static volatile SingularAttribute<Traffic, Integer> id;
  public static volatile SingularAttribute<Traffic, TrafficType> trafficType;
  public static volatile SingularAttribute<Traffic, LocalDate> date;

}
