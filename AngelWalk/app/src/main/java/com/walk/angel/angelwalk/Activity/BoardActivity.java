package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.walk.angel.angelwalk.Adapter.BoardListAdapter;
import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.ServerURL;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.board.BoardData;
import com.walk.angel.angelwalk.Data.board.BoardList;
import com.walk.angel.angelwalk.R;
import com.walk.angel.angelwalk.UILocker;
import com.walk.angel.angelwalk.scroll.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardActivity extends AppCompatActivity implements ServerURL {


    @BindView(R.id.boardRecyclerView)
    RecyclerView boardRecyclerView;

    @BindView(R.id.btnEditBoard)
    Button btnEditBoard;

    private ArrayList<BoardData> arrayListOfBoardData = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;

    private BoardListAdapter boardListAdapter;

    private UILocker uiLocker;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.bind(this);
        btnEditBoard.setOnClickListener(onClickListener);
        uiLocker = new UILocker(BoardActivity.this);
        uiLocker.lock();
        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        new ConnectServer().run(0);


    }

    class ConnectServer{
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        public void run(int page) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<BoardList> call = serverAPI.getBoardList(page);

            call.enqueue(new Callback<BoardList>() {
                @Override
                public void onResponse(Call<BoardList> call, Response<BoardList> response) {
                    try {
                        if (response.isSuccessful()) {
                            arrayListOfBoardData = response.body().getArrayListOfBoardData();
                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    boardListAdapter = new BoardListAdapter(arrayListOfBoardData);
                                    boardRecyclerView.setAdapter(boardListAdapter);
                                    boardRecyclerView.setLayoutManager(linearLayoutManager);
                                    boardRecyclerView.addOnItemTouchListener(onItemTouchListener);
                                    boardRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                        @Override
                                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                            uiLocker.lock();
                                            new ConnectServer().reload(page);
                                        }
                                    });
                                    uiLocker.unlock();
                                }
                            });
                        } else {
                            Toast.makeText(BoardActivity.this, "게시글을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            uiLocker.unlock();
                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<BoardList> call, Throwable t) {

                }
            });
        }

        private void reload(int page){
            page = (page-1)*10;
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<BoardList> call = serverAPI.getBoardList(page);
            call.enqueue(new Callback<BoardList>() {
                @Override
                public void onResponse(Call<BoardList> call, Response<BoardList> response) {
                    try {
                        if (response.isSuccessful() && "Success".equals(response.body().getResult())) {
                            final ArrayList<BoardData> arrayListOfReloadedBoardData = response.body().getArrayListOfBoardData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int currentBoardSize = arrayListOfBoardData.size();
                                    arrayListOfBoardData.addAll(arrayListOfReloadedBoardData);
                                    boardListAdapter.notifyItemRangeInserted(currentBoardSize, arrayListOfReloadedBoardData.size());
                                    uiLocker.unlock();
                                }
                            });
                        } else {
                            Toast.makeText(BoardActivity.this, "게시글을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<BoardList> call, Throwable t) {

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
                Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                intent.putExtra("boardIndex", arrayListOfBoardData.get(currentPosition).getBoardIndex());
                Log.d("aaaa", "boardIndex is " +  arrayListOfBoardData.get(currentPosition).getBoardIndex());
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnEditBoard:
                    Intent intent = new Intent(BoardActivity.this, EditBoardActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
