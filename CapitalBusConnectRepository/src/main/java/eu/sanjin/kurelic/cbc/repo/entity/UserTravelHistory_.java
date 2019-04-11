package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserTravelHistory.class)
public class UserTravelHistory_ {

    public static volatile SingularAttribute<UserTravelHistory, Integer> id;
    public static volatile SingularAttribute<UserTravelHistory, String> username;
    public static volatile SingularAttribute<UserTravelHistory, Double> price;
    public static volatile SingularAttribute<UserTravelHistory, TripHistory> tripHistory;
    public static volatile SingularAttribute<UserTravelHistory, Integer> numberOfAdults;
    public static volatile SingularAttribute<UserTravelHistory, Integer> numberOfChildren;
    public static volatile SingularAttribute<UserTravelHistory, PayingMethod> payingMethod;

}
