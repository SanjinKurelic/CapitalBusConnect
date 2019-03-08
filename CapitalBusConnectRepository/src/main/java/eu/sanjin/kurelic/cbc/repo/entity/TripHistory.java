package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trip_history")
public class TripHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "bus_schedule_id", referencedColumnName = "id")
    private BusSchedule busSchedule;
    @Column
    private LocalDate date;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @OneToOne
    @JoinColumn(name = "trip_type_id", referencedColumnName = "id")
    private TripType tripType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusSchedule getBusSchedule() {
        return busSchedule;
    }

    public void setBusSchedule(BusSchedule busSchedule) {
        this.busSchedule = busSchedule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }
}
