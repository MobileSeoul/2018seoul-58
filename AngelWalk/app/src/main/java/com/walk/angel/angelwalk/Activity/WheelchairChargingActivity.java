package com.walk.angel.angelwalk.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Toast;

import com.walk.angel.angelwalk.Adapter.WheelchairChargingAdapter;
import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.Data.WheelChairData;
import com.walk.angel.angelwalk.Data.WheelchairChargingData;
import com.walk.angel.angelwalk.DaumMapSchemeURL;
import com.walk.angel.angelwalk.ItemClickSupport;
import com.walk.angel.angelwalk.R;

import java.util.ArrayList;

public class WheelchairChargingActivity extends AppCompatActivity {

    WheelchairChargingAdapter chargingListAdapter;
    RecyclerView recyclerView;

    private ArrayList<String> arrayListOfLocation = new ArrayList<>();
    private ArrayList<String> arrayListOfAddress = new ArrayList<>();
    private ArrayList<String> arrayListOfLongitude = new ArrayList<>();
    private ArrayList<String> arrListOfLatitude = new ArrayList<>();

    private GestureDetector gestureDetector;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheelchair_charging);

        try {
            WheelChairData wheelChairData = new WheelChairData();
            wheelChairData.setChargingLocation(getAssets());
            wheelChairData.setLocationInfo();

           arrayListOfLocation = wheelChairData.getLocations();
           arrayListOfAddress = wheelChairData.getLocationAddresses();
           arrayListOfLongitude = wheelChairData.getLocationLongitudes();
           arrListOfLatitude = wheelChairData.getLocationLatitudes();

        } catch (Exception e) {
            Log.d("error", "WheelChairData Error is " + e.toString());
        }

        setupViews();
    }

    void setupViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_wheelchair);

        // Setup RecyclerView, associated adapter, and layout manager.
            chargingListAdapter = new WheelchairChargingAdapter(arrayListOfLocation, arrayListOfAddress);
        //RecyclerView에 Adapter세팅
        recyclerView.setAdapter(chargingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                //SightsData data = new SightsData(position, listOfSightData.get(position).getName(), listOfSightData.get(position).getAddress(), listOfSightData.get(position).getCategory());
                //WheelchairChargingData data = arrayListOfLocation.get(position);
                Toast.makeText(WheelchairChargingActivity.this, "클릭한 아이템의 이름은 " + arrayListOfLocation.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(WheelchairChargingActivity.this, WheelchairChargingInfoActivity.class);
//                intent.putExtra("WheelchairChargingAddress", data);
//                startActivity(intent);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WheelchairChargingActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("길찾기 안내");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("충전소까지 길찾기 안내를 해드릴까요?")
                        .setCancelable(false)
                        .setPositiveButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("안내",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //dialog.cancel();
                                        Intent intent = getDaumIntent(Double.valueOf(arrListOfLatitude.get(position)), Double.valueOf(arrayListOfLongitude.get(position)));
                                        if (intent != null) {
                                            startActivity(intent);
                                        }
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
            }
        });

    }

    private Intent getDaumIntent(double latitude, double longitude) {

        SharedPreferences pref = getSharedPreferences("currentPosition", MODE_PRIVATE);
        String currentLatitude = pref.getString("currentLatitude", "");
        String currentLongitude = pref.getString("currentLongitude", "");

        String url = "daummaps://route?sp=" + currentLatitude + "," + currentLongitude + "&ep=" + latitude + "," + longitude + "&by=PUBLICTRANSIT";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        DaumMapSchemeURL daumMapSchemeURL = new DaumMapSchemeURL(this, intent) {
            @Override
            public boolean canOpenDaummapURL() {
                return super.canOpenDaummapURL();
            }
        };

        if (daumMapSchemeURL.existDaummapApp()) {
            return intent;
        } else {
            DaumMapSchemeURL.openDaummapDownloadPage(this);
        }
        return null;
    }

}
