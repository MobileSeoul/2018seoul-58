package com.walk.angel.angelwalk.Data.board;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentList {
    @SerializedName("stat")
    private String result;

    @SerializedName("msg")
    private String message;

    @Nullable
    @SerializedName("data")
    private ArrayList<CommentData> arrayListOfCommentData = new ArrayList<>();


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

    public ArrayList<CommentData> getArrayListOfCommentData() {
        return arrayListOfCommentData;
    }

    public void setArrayListOfCommentData(ArrayList<CommentData> arrayListOfCommentData) {
        this.arrayListOfCommentData = arrayListOfCommentData;
    }
}
