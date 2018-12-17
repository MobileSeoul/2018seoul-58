package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.R;

public class SightInfoActivity extends AppCompatActivity {

    TextView txtSightInfoName;
    TextView txtSightInfoAddress;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_info);

        Intent intent = getIntent();
        SightsData data = (SightsData) intent.getSerializableExtra("sightsInfo");

        txtTitle = (TextView) findViewById(R.id.txt_sight_name);
        txtSightInfoName = (TextView) findViewById(R.id.sightInfo_name);
        txtSightInfoAddress = (TextView) findViewById(R.id.sightInfo_address);

        // Data TextView에 적용
        txtSightInfoName.setText(data.getName());
        txtSightInfoAddress.setText(data.getAddress());
        txtTitle.setText(data.getName());
    }
}
