package com.aaronnebbs.thesociallink.plum.Objects;


import java.io.Serializable;

public class Session implements Serializable {

    private User user;

    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
