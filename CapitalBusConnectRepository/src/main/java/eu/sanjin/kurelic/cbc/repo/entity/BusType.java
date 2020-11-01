package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus_type")
public class BusType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String name;
  @Column(name = "number_of_seats")
  private Integer numberOfSeats;
  @Column(name = "children_friendly")
  private Boolean childrenFriendly;
  @Column(name = "pet_friendly")
  private Boolean petFriendly;
  @Column
  private Boolean operates;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Boolean isChildrenFriendly() {
    return childrenFriendly;
  }

  public void setChildrenFriendly(Boolean childrenFriendly) {
    this.childrenFriendly = childrenFriendly;
  }

  public Boolean isPetFriendly() {
    return petFriendly;
  }

  public void setPetFriendly(Boolean petFriendly) {
    this.petFriendly = petFriendly;
  }

  public Boolean isOperates() {
    return operates;
  }

  public void setOperates(Boolean operates) {
    this.operates = operates;
  }
}
