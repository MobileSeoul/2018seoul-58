package com.walk.angel.angelwalk.Data.board;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoardList {
    @SerializedName("stat")
    private String result;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private ArrayList<BoardData> arrayListOfBoardData = new ArrayList<>();

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

    public ArrayList<BoardData> getArrayListOfBoardData() {
        return arrayListOfBoardData;
    }

    public void setArrayListOfBoardData(ArrayList<BoardData> arrayListOfBoardData) {
        this.arrayListOfBoardData = arrayListOfBoardData;
    }
}
