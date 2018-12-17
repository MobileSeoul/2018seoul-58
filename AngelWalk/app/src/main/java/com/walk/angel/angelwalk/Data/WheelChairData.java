package com.walk.angel.angelwalk.Data;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class WheelChairData {
    private String location = "";
    private JSONArray locationInfo = new JSONArray();

    public void setChargingLocation(AssetManager assets) {
        try {
            InputStream inputStream = assets.open("auto_wheelchair_charging_info.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();
            location = new String(buffer, "UTF-8");
        } catch (Exception e) {
            Log.d("error", "wheelchair json parsing error is " + e.toString());
        }
    }

    public void setLocationInfo() throws Exception {
        locationInfo = new JSONObject(location).getJSONArray("DATA");
    }

    public ArrayList<String> getLocationNames() throws Exception {
        ArrayList<String> arrayListOfLocation = new ArrayList<>();
        for (int index = 0; index < locationInfo.length(); index++) {
            arrayListOfLocation.add(locationInfo.getJSONObject(index).getString("cot_value_03"));
        }
        return arrayListOfLocation;
    }

    public ArrayList<String> getLocationLongitudes() throws Exception {
        ArrayList<String> arrayListOfLocation = new ArrayList<>();
        for (int index = 0; index < locationInfo.length(); index++) {
            arrayListOfLocation.add(locationInfo.getJSONObject(index).getString("cot_coord_x"));
        }
        return arrayListOfLocation;
    }

    public ArrayList<String> getLocationLatitudes() throws Exception {
        ArrayList<String> arrayListOfLocation = new ArrayList<>();
        for (int index = 0; index < locationInfo.length(); index++) {
            arrayListOfLocation.add(locationInfo.getJSONObject(index).getString("cot_coord_y"));
        }
        return arrayListOfLocation;
    }

    public ArrayList<String> getLocations() throws Exception {
        ArrayList<String> arrayListOfLocation = new ArrayList<>();
        for (int index = 0; index < locationInfo.length(); index++) {
            arrayListOfLocation.add(locationInfo.getJSONObject(index).getString("cot_conts_name"));
        }
        return arrayListOfLocation;
    }

    public ArrayList<String> getLocationAddresses() throws Exception {
        ArrayList<String> arrayListOfLocation = new ArrayList<>();
        for (int index = 0; index < locationInfo.length(); index++) {
            arrayListOfLocation.add(locationInfo.getJSONObject(index).getString("cot_addr_full_old"));
        }
        return arrayListOfLocation;
    }
}
