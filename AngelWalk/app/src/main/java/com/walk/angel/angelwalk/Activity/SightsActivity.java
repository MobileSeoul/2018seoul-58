package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walk.angel.angelwalk.Adapter.SightsAdapter;
import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.SightList;
import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.R;
import com.walk.angel.angelwalk.scroll.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.walk.angel.angelwalk.Api.ServerURL.BASE_URL;

public class SightsActivity extends AppCompatActivity {

    SightsAdapter sightListAdapter;
    RecyclerView recyclerView;

    private ArrayList<SightsData> arrayListOfSightData = new ArrayList<>();
    private GestureDetector gestureDetector;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        //new ConnectServer().run();
        SightList manualSightList = new SightList();
        manualSightList.setArrayListOfSightData();
        arrayListOfSightData = manualSightList.getArrayListOfSightData();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        sightListAdapter = new SightsAdapter(arrayListOfSightData);

        setupViews();
    }

    void setupViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sight);

        // Setup RecyclerView, associated adapter, and layout manager.

        //RecyclerView에 Adapter세팅
        recyclerView.setAdapter(sightListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(onItemTouchListener);

    }

    class ConnectServer {
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        public void run() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<SightList> call = serverAPI.getSightList();

            call.enqueue(new Callback<SightList>() {
                @Override
                public void onResponse(Call<SightList> call, Response<SightList> response) {
                    try {
                        if (response.isSuccessful() && "Success".equals(response.body().getResult())) {
                            arrayListOfSightData = response.body().getArrayListOfSightData();
                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    sightListAdapter = new SightsAdapter(arrayListOfSightData);
                                    recyclerView.setAdapter(sightListAdapter);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.addOnItemTouchListener(onItemTouchListener);
                                    recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                        @Override
                                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                            //uiLocker.lock();
                                            //new SightsActivity.ConnectServer().reload(page);
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(SightsActivity.this, "관광지를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            //uiLocker.unlock();
                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<SightList> call, Throwable t) {

                }
            });
        }
    }

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View chileView = rv.findChildViewUnder(e.getX(), e.getY());

            if (chileView != null && gestureDetector.onTouchEvent(e)) {
                int currentPosition = rv.getChildAdapterPosition(chileView);

                SightsData data = arrayListOfSightData.get(currentPosition);
                //Toast.makeText(SightsActivity.this, "클릭한 아이템의 이름은 " + arrayListOfSightData.get(currentPosition).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SightsActivity.this, SightInfoWebActivity.class);
                intent.putExtra("sightsInfo", data);
                startActivity(intent);

                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

}
