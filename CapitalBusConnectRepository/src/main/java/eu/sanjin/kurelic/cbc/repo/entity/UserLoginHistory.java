package eu.sanjin.kurelic.cbc.repo.entity;

import eu.sanjin.kurelic.cbc.repo.entity.composite.LoginHistoryPrimaryKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {

    @EmbeddedId
    private LoginHistoryPrimaryKey id;
    @Column(name = "ip_address")
    private String ipAddress;

    public UserLoginHistory() {
    }

    public UserLoginHistory(LoginHistoryPrimaryKey id, String ipAddress) {
        this.id = id;
        this.ipAddress = ipAddress;
    }

    public LoginHistoryPrimaryKey getId() {
        return id;
    }

    public void setId(LoginHistoryPrimaryKey id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
