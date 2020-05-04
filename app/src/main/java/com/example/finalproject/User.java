package com.example.finalproject;


public class User {
    String email, status, nick, location;
    User() {
    }
    public User(String email, String status, String nick, String location) {
        this.email = email;
        this.status = status;
        this.nick = nick;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
