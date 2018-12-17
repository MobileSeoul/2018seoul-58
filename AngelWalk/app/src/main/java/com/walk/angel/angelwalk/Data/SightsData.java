package com.walk.angel.angelwalk.Data;

import java.io.Serializable;

public class SightsData implements Serializable {

    private int mId;
    private double mLatitude, mLongitude;
    private String mName;
    private String mAddress;
    private String mUrl;
    private String mImage;

    public SightsData(int id,
                      double latitude,
                      double longitude,
                      String name, String address,
                      String url, String image) {
        mId = id;
        mLatitude = latitude;
        mLongitude = longitude;
        mName = name;
        mAddress = address;
        mUrl = url;
        mImage = image;
    }

    public int getId() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getURL() {
        return mUrl;
    }

    public String getImage() {
        return mImage;
    }

}
