package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "authority"})})
public class Authorities implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "username")
  private User username;
  @Column
  private String authority;

  public User getUsername() {
    return username;
  }

  public void setUsername(User username) {
    this.username = username;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (Objects.isNull(o) || getClass() != o.getClass()) return false;
    Authorities that = (Authorities) o;
    return Objects.equals(username, that.username) && Objects.equals(authority, that.authority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, authority);
  }
}
