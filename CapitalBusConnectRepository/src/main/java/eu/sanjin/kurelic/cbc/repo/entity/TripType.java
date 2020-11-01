package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trip_type")
public class TripType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  @Column(unique = true, name = "name")
  private TripTypeValue tripTypeValue;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TripTypeValue getTripTypeValue() {
    return tripTypeValue;
  }

  public void setTripTypeValue(TripTypeValue tripTypeValue) {
    this.tripTypeValue = tripTypeValue;
  }
}
