package com.walk.angel.angelwalk.Data.board;

import com.google.gson.annotations.SerializedName;
import com.walk.angel.angelwalk.Data.CommonData;

public class LikeData extends CommonData{

    @SerializedName("data")
    private int likeStatus;

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }
}
