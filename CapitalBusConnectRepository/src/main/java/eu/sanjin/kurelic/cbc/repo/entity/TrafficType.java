package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;

@Entity
@Table(name = "traffic_info_type")
public class TrafficType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
