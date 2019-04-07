package eu.sanjin.kurelic.cbc.repo.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@Entity
@Table(name = "user_travel_history")
public class UserTravelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private Double price;
    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "trip_history_id", referencedColumnName = "id")
    private TripHistory tripHistory;
    @Column(name = "number_of_adults")
    private Integer numberOfAdults;
    @Column(name = "number_of_children")
    private Integer numberOfChildren;
    @OneToOne
    @JoinColumn(name = "paying_method_id", referencedColumnName = "id")
    private PayingMethod payingMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TripHistory getTripHistory() {
        return tripHistory;
    }

    public void setTripHistory(TripHistory tripHistory) {
        this.tripHistory = tripHistory;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public PayingMethod getPayingMethod() {
        return payingMethod;
    }

    public void setPayingMethod(PayingMethod payingMethod) {
        this.payingMethod = payingMethod;
    }
}
