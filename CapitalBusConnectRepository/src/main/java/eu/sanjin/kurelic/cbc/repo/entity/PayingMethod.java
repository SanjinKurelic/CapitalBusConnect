package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValue;

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
  private PayingMethodValue payingMethodValue;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PayingMethodValue getPayingMethodValue() {
    return payingMethodValue;
  }

  public void setPayingMethodValue(PayingMethodValue payingMethodValue) {
    this.payingMethodValue = payingMethodValue;
  }
}
