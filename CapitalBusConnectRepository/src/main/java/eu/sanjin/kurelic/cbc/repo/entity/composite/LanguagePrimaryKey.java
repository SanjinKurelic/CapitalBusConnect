package eu.sanjin.kurelic.cbc.repo.entity.composite;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LanguagePrimaryKey implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String language;

  public LanguagePrimaryKey() {
  }

  public LanguagePrimaryKey(Integer id, String language) {
    this.id = id;
    this.language = language;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (Objects.isNull(o) || getClass() != o.getClass()) return false;
    LanguagePrimaryKey that = (LanguagePrimaryKey) o;
    return Objects.equals(id, that.id) && Objects.equals(language, that.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, language);
  }
}
