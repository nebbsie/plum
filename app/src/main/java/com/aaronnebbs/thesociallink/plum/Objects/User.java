package com.aaronnebbs.thesociallink.plum.Objects;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String profilepic;
    private String lastlogged;

    public User(String id,String firstname, String lastname, String username,String password, String profilepic, String lastlogged){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.profilepic = profilepic;
        this.lastlogged = lastlogged;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getLastlogged() {
        return lastlogged;
    }

    public void setLastlogged(String lastlogged) {
        this.lastlogged = lastlogged;
    }
}
