package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.Data.WheelchairChargingData;
import com.walk.angel.angelwalk.R;

public class WheelchairChargingInfoActivity extends AppCompatActivity {

    TextView txtWheelchairChargingInfoAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheelchair_charging_info);


        Intent intent = getIntent();
        WheelchairChargingData data = (WheelchairChargingData) intent.getSerializableExtra("WheelchairChargingAddress");

        txtWheelchairChargingInfoAddress = (TextView) findViewById(R.id.txtWheelchairChargingInfoAddress);

        // Data TextView에 적용
        txtWheelchairChargingInfoAddress.setText(data.getAddress());
    }
}
