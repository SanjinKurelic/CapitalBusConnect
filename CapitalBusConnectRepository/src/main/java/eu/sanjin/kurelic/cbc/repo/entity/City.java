package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;

@Entity
public class City {

    @Id
    private int id;
    @Column(name = "image_name")
    private String imageName;

    /*@JoinColumns({
            @JoinColumn(name = "language", referencedColumnName = "language", insertable = false, updatable = false),
            @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    })*/
    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
