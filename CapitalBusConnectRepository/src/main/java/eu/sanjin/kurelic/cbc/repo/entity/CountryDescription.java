package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "country_description")
public class CountryDescription {

  @EmbeddedId
  private LanguagePrimaryKey id;
  @Column
  private String title;
  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
  private Country country;

  public LanguagePrimaryKey getId() {
    return id;
  }

  public void setId(LanguagePrimaryKey id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }
}
