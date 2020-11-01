package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CityDescription.class)
public class CityDescription_ {

  public static volatile SingularAttribute<CityDescription, LanguagePrimaryKey> id;
  public static volatile SingularAttribute<CityDescription, String> title;
  public static volatile SingularAttribute<CityDescription, String> description;
  public static volatile SingularAttribute<CityDescription, City> city;
  public static volatile SingularAttribute<CityDescription, CountryDescription> country;

}
