package eu.sanjin.kurelic.cbc.repo.entity.composite;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPrimaryKey implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;

    public UserPrimaryKey(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserPrimaryKey() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrimaryKey that = (UserPrimaryKey) o;
        return id == that.id && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
