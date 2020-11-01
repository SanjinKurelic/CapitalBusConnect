package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PayingMethod.class)
public class PayingMethod_ {

  public static volatile SingularAttribute<PayingMethod, Integer> id;
  public static volatile SingularAttribute<PayingMethod, String> name;

}
