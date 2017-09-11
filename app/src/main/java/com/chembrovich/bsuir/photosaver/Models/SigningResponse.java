package com.chembrovich.bsuir.photosaver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SigningResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private User user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

