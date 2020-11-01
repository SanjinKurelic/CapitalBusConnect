package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CountryDescription.class)
public class CountryDescription_ {

  public static volatile SingularAttribute<CountryDescription, LanguagePrimaryKey> id;
  public static volatile SingularAttribute<CountryDescription, String> title;
  public static volatile SingularAttribute<CountryDescription, Country> country;

}
