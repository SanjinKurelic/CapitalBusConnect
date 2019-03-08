package eu.sanjin.kurelic.cbc.repo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {

    @Id
    private String username;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "ip_address")
    private String ipAddress;

    public UserLoginHistory() {
    }

    public UserLoginHistory(String username, LocalDateTime dateTime, String ipAddress) {
        this.username = username;
        this.dateTime = dateTime;
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
