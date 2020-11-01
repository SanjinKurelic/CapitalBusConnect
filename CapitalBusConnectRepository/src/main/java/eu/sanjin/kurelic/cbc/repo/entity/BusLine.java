package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "bus_line", uniqueConstraints = {@UniqueConstraint(columnNames = {"city1_id", "city2_id"})})
public class BusLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne
  @JoinColumn(name = "city1_id", referencedColumnName = "id")
  private City city1;
  @OneToOne
  @JoinColumn(name = "city2_id", referencedColumnName = "id")
  private City city2;
  @Column
  private Boolean operates;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public City getCity1() {
    return city1;
  }

  public void setCity1(City city1) {
    this.city1 = city1;
  }

  public City getCity2() {
    return city2;
  }

  public void setCity2(City city2) {
    this.city2 = city2;
  }

  public Boolean isOperates() {
    return operates;
  }

  public void setOperates(Boolean operates) {
    this.operates = operates;
  }
}
