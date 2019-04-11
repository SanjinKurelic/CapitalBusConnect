package eu.sanjin.kurelic.cbc.repo.entity.composite;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LanguagePrimaryKey.class)
public class LanguagePrimaryKey_ {

    public static volatile SingularAttribute<LanguagePrimaryKey, Integer> id;
    public static volatile SingularAttribute<LanguagePrimaryKey, String> language;

}
