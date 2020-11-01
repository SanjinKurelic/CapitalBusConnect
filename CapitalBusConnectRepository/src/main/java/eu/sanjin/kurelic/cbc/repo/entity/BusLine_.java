package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusLine.class)
public class BusLine_ {

  public static volatile SingularAttribute<BusLine, Integer> id;
  public static volatile SingularAttribute<BusLine, City> city1;
  public static volatile SingularAttribute<BusLine, City> city2;
  public static volatile SingularAttribute<BusLine, Boolean> operates;

}
