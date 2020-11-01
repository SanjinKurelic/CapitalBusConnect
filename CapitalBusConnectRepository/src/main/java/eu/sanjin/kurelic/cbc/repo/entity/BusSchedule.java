package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Table(name = "bus_schedule")
public class BusSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "from_time")
  private LocalTime fromTime;
  @Column
  private Duration duration;
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

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
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
