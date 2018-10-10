package com.cegeka.entities.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
    private String surname;
    private String name;
    private String phone;
    private String mail;
    @Enumerated(EnumType.STRING)
    private Role clearance = Role.ROLE_USER;
    private Boolean status;
    private String accessToken;

    public User(String username, String password, String surname, String name, String phone, String mail, Role clearance, Boolean status, String accessToken) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.clearance = clearance;
        this.status = status;
        this.accessToken = accessToken;
    }

    public User() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getClearance() {
        return clearance;
    }

    public void setClearance(Role clearance) {
        this.clearance = clearance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
