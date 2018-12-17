package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.ServerURL;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.board.BoardData;
import com.walk.angel.angelwalk.Data.board.BoardDetailData;
import com.walk.angel.angelwalk.Data.board.BoardList;
import com.walk.angel.angelwalk.Data.board.CommentData;
import com.walk.angel.angelwalk.Data.board.CommentList;
import com.walk.angel.angelwalk.Data.board.LikeData;
import com.walk.angel.angelwalk.R;
import com.walk.angel.angelwalk.UILocker;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardDetailActivity extends AppCompatActivity implements ServerURL {

    private UILocker uiLocker;
    private BoardData boardData;
    private ArrayList<CommentData> arrayListOfCommentData = new ArrayList<>();

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtNickName)
    TextView txtNickName;

    @BindView(R.id.txtCreateDate)
    TextView txtCreateDate;

    @BindView(R.id.txtContent)
    TextView txtContent;

    @BindView(R.id.txtCommentCount)
    TextView txtCommentCount;

    @BindView(R.id.txtLikeCount)
    TextView txtLikeCount;

    @BindView(R.id.imgComment)
    ImageView imgComment;

    @BindView(R.id.imgLike)
    ImageView imgLike;

    private int boardIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        ButterKnife.bind(this);
        uiLocker = new UILocker(BoardDetailActivity.this);
        uiLocker.lock();
        Intent intent = getIntent();
        boardIndex = intent.getIntExtra("boardIndex", -1);
        new ConnectServer().getBoard(boardIndex);

    }

    class ConnectServer {
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        public void getBoard(final int boardIndex) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<BoardDetailData> call = serverAPI.getBoard(boardIndex);
            call.enqueue(new Callback<BoardDetailData>() {
                @Override
                public void onResponse(Call<BoardDetailData> call, Response<BoardDetailData> response) {
                    Log.d("aaaa", call.request().url().toString());
                    try {
                        if (response.isSuccessful()) {
                            boardData = response.body().getBoardData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    txtTitle.setText(boardData.getTitle());
                                    txtNickName.setText(boardData.getNickName());
                                    txtCreateDate.setText(boardData.getCreateDate().substring(0, 10)+ " / " +boardData.getCreateDate().substring(11, 16));
                                    txtContent.setText(boardData.getContent());
                                    txtLikeCount.setText(String.valueOf(boardData.getLikeCount()));
                                    switch (boardData.getLikeStatus()){
                                        case 0:
                                            imgLike.setSelected(false);
                                            break;
                                        case 1:
                                            imgLike.setSelected(true);
                                            break;
                                    }


                                }
                            });

                            getComment(boardIndex);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    Toast.makeText(BoardDetailActivity.this, "게시글을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    Log.d("error",boardData.getMessage());
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                        Log.d("error", "댓글 부르기전에 에러...ㅠㅠ");
                    }
                }

                @Override
                public void onFailure(Call<BoardDetailData> call, Throwable t) {

                }
            });
        }

        public void getComment(int boardIndex){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<CommentList> call = serverAPI.getComment(boardIndex, 0);

            call.enqueue(new Callback<CommentList>() {
                @Override
                public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                    Log.d("aaaa", call.request().url().toString());
                    try {
                        if (response.isSuccessful()) {
                            arrayListOfCommentData = response.body().getArrayListOfCommentData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtCommentCount.setText(String.valueOf(arrayListOfCommentData.size()));
                                    imgComment.setOnTouchListener(onTouchListener);
                                    imgLike.setOnTouchListener(onTouchListener);
                                    uiLocker.unlock();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    Toast.makeText(BoardDetailActivity.this, "게시글을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                        uiLocker.unlock();
                    }
                }

                @Override
                public void onFailure(Call<CommentList> call, Throwable t) {

                }
            });
        }

        public void setLike(int boardIndex){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<LikeData> call = serverAPI.updateLike(boardIndex);

            call.enqueue(new Callback<LikeData>() {
                @Override
                public void onResponse(Call<LikeData> call, Response<LikeData> response) {
                    Log.d("aaaa", call.request().url().toString());
                    try {
                        if (response.isSuccessful()) {
                            final LikeData likeData = response.body();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int likeCount = Integer.parseInt(txtLikeCount.getText().toString());
                                    switch (likeData.getLikeStatus()){
                                        case 0:
                                            imgLike.setSelected(false);
                                            txtLikeCount.setText(String.valueOf(likeCount-1));
                                            break;
                                        case 1:
                                            imgLike.setSelected(true);
                                            txtLikeCount.setText(String.valueOf(likeCount+1));
                                            break;
                                    }
                                    uiLocker.unlock();
                                }
                            });
                        } else {
                            uiLocker.unlock();
                            Toast.makeText(BoardDetailActivity.this, "좋아요 등록 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                        uiLocker.unlock();
                    }
                }

                @Override
                public void onFailure(Call<LikeData> call, Throwable t) {
                    Log.d("error" , "Like Update Server Error is " + t.toString());
                    uiLocker.unlock();
                }
            });
        }
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (view.getId()){
                case R.id.imgComment:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Intent intent = new Intent(BoardDetailActivity.this, CommentActivity.class);
                        intent.putExtra("boardIndex", boardIndex);
                        intent.putExtra("arrayListOfCommentData", arrayListOfCommentData);
                        startActivity(intent);
                    }
                    break;
                case R.id.imgLike:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        new ConnectServer().setLike(boardIndex);
                        uiLocker.lock();
                    }
                    break;
            }
            return true;
        }
    };
}
