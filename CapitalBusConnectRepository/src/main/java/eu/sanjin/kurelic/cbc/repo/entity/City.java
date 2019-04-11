package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;

@Entity
public class City {

    @Id
    private Integer id;
    @Column(name = "image_name")
    private String imageName;
    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
