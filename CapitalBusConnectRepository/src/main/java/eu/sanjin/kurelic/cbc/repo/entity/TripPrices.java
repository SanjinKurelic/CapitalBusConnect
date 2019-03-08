package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trip_prices", uniqueConstraints = {@UniqueConstraint(columnNames = {"trip_duration", "from_date"})})
public class TripPrices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "trip_duration")
    private int tripDuration;
    @Column(name = "from_date")
    private LocalDate fromDate;
    @Column
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
