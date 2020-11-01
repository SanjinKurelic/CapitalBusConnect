package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TrafficDescription.class)
public class TrafficDescription_ {

  public static volatile SingularAttribute<TrafficDescription, LanguagePrimaryKey> id;
  public static volatile SingularAttribute<TrafficDescription, String> description;
  public static volatile SingularAttribute<TrafficDescription, Traffic> traffic;

}
