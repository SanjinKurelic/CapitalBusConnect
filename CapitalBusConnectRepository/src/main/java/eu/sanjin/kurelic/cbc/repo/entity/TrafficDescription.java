package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_info_description")
public class TrafficDescription {

  @EmbeddedId
  private LanguagePrimaryKey id;
  @Column
  private String description;
  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
  private Traffic traffic;

  public LanguagePrimaryKey getId() {
    return id;
  }

  public void setId(LanguagePrimaryKey id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Traffic getTraffic() {
    return traffic;
  }

  public void setTraffic(Traffic traffic) {
    this.traffic = traffic;
  }
}
