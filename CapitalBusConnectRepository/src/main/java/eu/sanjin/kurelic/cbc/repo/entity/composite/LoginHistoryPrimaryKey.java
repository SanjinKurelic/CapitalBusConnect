package eu.sanjin.kurelic.cbc.repo.entity.composite;

import eu.sanjin.kurelic.cbc.repo.entity.User;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class LoginHistoryPrimaryKey implements Serializable {

  @ManyToOne
  @JoinColumn(name = "username")
  private User username;
  @Column(name = "date_time")
  private LocalDateTime dateTime;

  public LoginHistoryPrimaryKey() {
  }

  public LoginHistoryPrimaryKey(User username, LocalDateTime dateTime) {
    this.username = username;
    this.dateTime = dateTime;
  }

  public User getUsername() {
    return username;
  }

  public void setUsername(User username) {
    this.username = username;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (Objects.isNull(o) || getClass() != o.getClass()) return false;
    LoginHistoryPrimaryKey that = (LoginHistoryPrimaryKey) o;
    return Objects.equals(username, that.username) &&
      Objects.equals(dateTime, that.dateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, dateTime);
  }
}
