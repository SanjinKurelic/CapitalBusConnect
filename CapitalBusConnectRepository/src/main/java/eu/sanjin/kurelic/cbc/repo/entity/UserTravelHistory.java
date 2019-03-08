package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.UserPrimaryKey;

import javax.persistence.*;

@Entity
@Table(name = "user_travel_history")
public class UserTravelHistory {

    @EmbeddedId
    private UserPrimaryKey id;
    @Column
    private double price;
    @OneToOne
    @JoinColumn(name = "trip_history_id", referencedColumnName = "id")
    private TripHistory tripHistory;
    @Column(name = "number_of_adults")
    private int numberOfAdults;
    @Column(name = "number_of_children")
    private int numberOfChildren;
    @OneToOne
    @JoinColumn(name = "paying_method_id", referencedColumnName = "id")
    private PayingMethod payingMethod;

    public UserPrimaryKey getId() {
        return id;
    }

    public void setId(UserPrimaryKey id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TripHistory getTripHistory() {
        return tripHistory;
    }

    public void setTripHistory(TripHistory tripHistory) {
        this.tripHistory = tripHistory;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public PayingMethod getPayingMethod() {
        return payingMethod;
    }

    public void setPayingMethod(PayingMethod payingMethod) {
        this.payingMethod = payingMethod;
    }
}
