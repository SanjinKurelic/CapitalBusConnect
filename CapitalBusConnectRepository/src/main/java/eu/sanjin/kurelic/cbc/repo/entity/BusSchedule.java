package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "bus_schedule")
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    private boolean workweek;
    @Column
    private boolean weekend;
    @OneToOne
    @JoinColumn(name = "bus_type_id", referencedColumnName = "id")
    private BusType busType;
    @Column
    private boolean operates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isWorkweek() {
        return workweek;
    }

    public void setWorkweek(boolean workweek) {
        this.workweek = workweek;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public boolean isOperates() {
        return operates;
    }

    public void setOperates(boolean operates) {
        this.operates = operates;
    }
}
