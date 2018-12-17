package com.walk.angel.angelwalk.Data;

import com.google.gson.annotations.SerializedName;

public class CommonData {
    @SerializedName("stat")
    private String result;

    @SerializedName("msg")
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
