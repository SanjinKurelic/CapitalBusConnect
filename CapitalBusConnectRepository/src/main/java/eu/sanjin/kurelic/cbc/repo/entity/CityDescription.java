package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "city_description")
public class CityDescription {

  @EmbeddedId
  private LanguagePrimaryKey id;
  @Column
  private String title;
  @Column
  private String description;
  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
  private City city;
  @OneToOne
  @JoinColumns({
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false),
    @JoinColumn(name = "language", referencedColumnName = "language", insertable = false, updatable = false)
  })
  private CountryDescription country;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public CountryDescription getCountry() {
    return country;
  }

  public void setCountry(CountryDescription country) {
    this.country = country;
  }
}
