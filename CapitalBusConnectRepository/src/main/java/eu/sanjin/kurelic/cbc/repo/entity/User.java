package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@SecondaryTable(name = "user_info")
public class User {

    @Id
    private String username;
    @Column
    private String password;
    @Column
    private Boolean enabled;
    @Column(table = "user_info")
    private String name;
    @Column(table = "user_info")
    private String surname;
    @Column(table = "user_info", name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(table = "user_info", name = "receive_newsletter")
    private Boolean receiveNewsletter;

    public User() {
    }

    public User(String username, String password, Boolean enabled, String name, String surname, LocalDate dateOfBirth, Boolean receiveNewsletter) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.receiveNewsletter = receiveNewsletter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isReceiveNewsletter() {
        return receiveNewsletter;
    }

    public void setReceiveNewsletter(Boolean receiveNewsletter) {
        this.receiveNewsletter = receiveNewsletter;
    }
}
