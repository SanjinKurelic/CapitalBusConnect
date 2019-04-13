package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "trip_prices", uniqueConstraints = {@UniqueConstraint(columnNames = {"trip_duration", "from_date"})})
public class TripPrices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "trip_duration")
    private Duration tripDuration;
    @Column(name = "from_date")
    private LocalDate fromDate;
    @Column
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Duration getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(Duration tripDuration) {
        this.tripDuration = tripDuration;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
