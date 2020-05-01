package com.example.finalproject;

import com.google.android.gms.maps.model.LatLng;

public class User {
    String email, status, nick;
    User() {
        
    }

    public User(String email, String status, String nick) {
        this.email = email;
        this.status = status;
        this.nick = nick;
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
