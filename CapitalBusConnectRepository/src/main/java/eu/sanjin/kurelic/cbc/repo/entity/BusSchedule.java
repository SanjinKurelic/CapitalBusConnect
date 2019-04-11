package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "bus_schedule")
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "from_time")
    private LocalTime fromTime;
    @Column(name = "to_time")
    private LocalTime toTime;
    @OneToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private BusLine busLine;
    @OneToOne
    @JoinColumn(name = "trip_type_id", referencedColumnName = "id")
    private TripType tripType;
    @Column
    private Boolean workweek;
    @Column
    private Boolean weekend;
    @OneToOne
    @JoinColumn(name = "bus_type_id", referencedColumnName = "id")
    private BusType busType;
    @Column
    private Boolean operates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Boolean isWorkweek() {
        return workweek;
    }

    public void setWorkweek(Boolean workweek) {
        this.workweek = workweek;
    }

    public Boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(Boolean weekend) {
        this.weekend = weekend;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public Boolean isOperates() {
        return operates;
    }

    public void setOperates(Boolean operates) {
        this.operates = operates;
    }
}
