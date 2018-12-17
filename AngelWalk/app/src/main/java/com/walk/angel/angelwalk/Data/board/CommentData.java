package com.walk.angel.angelwalk.Data.board;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentData implements Serializable {
    @Nullable
    @SerializedName("seq")
    private int commentIndex;

    @Nullable
    @SerializedName("content")
    private String content;

    @Nullable
    @SerializedName("date")
    private String createDate;

    @Nullable
    @SerializedName("nickName")
    private String nickName;

    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
