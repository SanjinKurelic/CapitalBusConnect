package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;

@Entity
@Table(name = "bus_type")
public class BusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "children_friendly")
    private boolean childrenFriendly;
    @Column(name = "pet_friendly")
    private boolean petFriendly;
    @Column
    private boolean operates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isChildrenFriendly() {
        return childrenFriendly;
    }

    public void setChildrenFriendly(boolean childrenFriendly) {
        this.childrenFriendly = childrenFriendly;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public boolean isOperates() {
        return operates;
    }

    public void setOperates(boolean operates) {
        this.operates = operates;
    }
}
