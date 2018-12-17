package com.walk.angel.angelwalk.Data;

import com.google.gson.annotations.SerializedName;

public class LoginData extends CommonData{
    @SerializedName("data")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}