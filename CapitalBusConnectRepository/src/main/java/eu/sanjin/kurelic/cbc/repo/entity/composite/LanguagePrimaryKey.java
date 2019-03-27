package eu.sanjin.kurelic.cbc.repo.entity.composite;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LanguagePrimaryKey implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String language;

    public LanguagePrimaryKey() {
    }

    public LanguagePrimaryKey(int id, String language) {
        this.id = id;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        if (o == null || getClass() != o.getClass()) return false;
        LanguagePrimaryKey that = (LanguagePrimaryKey) o;
        return id == that.id && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language);
    }
}