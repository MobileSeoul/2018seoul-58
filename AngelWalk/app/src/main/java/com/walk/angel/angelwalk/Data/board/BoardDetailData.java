package com.walk.angel.angelwalk.Data.board;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BoardDetailData {
    @Nullable
    @SerializedName("stat")
    private String result;

    @Nullable
    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private BoardData boardData;

    @Nullable
    public String getResult() {
        return result;
    }

    public void setResult(@Nullable String result) {
        this.result = result;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    public BoardData getBoardData() {
        return boardData;
    }

    public void setBoardData(BoardData boardData) {
        this.boardData = boardData;
    }
}
