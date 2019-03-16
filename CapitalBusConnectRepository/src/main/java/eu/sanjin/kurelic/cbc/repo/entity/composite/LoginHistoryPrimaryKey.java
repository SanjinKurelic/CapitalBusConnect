package eu.sanjin.kurelic.cbc.repo.entity.composite;

import eu.sanjin.kurelic.cbc.repo.entity.User;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class LoginHistoryPrimaryKey implements Serializable {

    @ManyToOne
    private User username;
    @Column(name = "date_time")
    private LocalDate dateTime;

    public LoginHistoryPrimaryKey() {
    }

    public LoginHistoryPrimaryKey(User username, LocalDate dateTime) {
        this.username = username;
        this.dateTime = dateTime;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginHistoryPrimaryKey that = (LoginHistoryPrimaryKey) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, dateTime);
    }
}
