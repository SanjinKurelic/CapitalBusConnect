package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paying_method")
public class PayingMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  @Column(unique = true, name = "name")
  private PayingMethodValues payingMethodValues;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PayingMethodValues getPayingMethodValues() {
    return payingMethodValues;
  }

  public void setPayingMethodValues(PayingMethodValues payingMethodValues) {
    this.payingMethodValues = payingMethodValues;
  }
}
