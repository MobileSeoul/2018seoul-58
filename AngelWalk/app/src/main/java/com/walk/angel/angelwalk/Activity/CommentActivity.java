package com.walk.angel.angelwalk.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walk.angel.angelwalk.Adapter.CommentListAdapter;
import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.ServerURL;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.CommonData;
import com.walk.angel.angelwalk.Data.board.BoardDetailData;
import com.walk.angel.angelwalk.Data.board.CommentData;
import com.walk.angel.angelwalk.Data.board.CommentList;
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

public class CommentActivity extends AppCompatActivity implements ServerURL{

    @BindView(R.id.recyclerViewComment)
    RecyclerView recyclerViewComment;

    @BindView(R.id.editComment)
    EditText editComment;

    @BindView(R.id.btnCreateComment)
    Button btnCreateComment;

    private ArrayList<CommentData> arrayListOfCommentData = new ArrayList<>();

    private CommentListAdapter commentListAdapter;

    private LinearLayoutManager linearLayoutManager;

    private UILocker uiLocker;

    private int boardIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        uiLocker = new UILocker(CommentActivity.this);
        boardIndex = getIntent().getIntExtra("boardIndex", -1);
        arrayListOfCommentData = (ArrayList<CommentData>)getIntent().getSerializableExtra("arrayListOfCommentData");
        commentListAdapter = new CommentListAdapter(arrayListOfCommentData);
        linearLayoutManager = new LinearLayoutManager(CommentActivity.this);
        recyclerViewComment.setAdapter(commentListAdapter);
        recyclerViewComment.setLayoutManager(linearLayoutManager);
        btnCreateComment.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnCreateComment:
                    uiLocker.lock();
                    new ConnectServer().createComment(boardIndex, editComment.getText().toString());
                    break;
            }
        }
    };

    class ConnectServer {
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        public void createComment(final int boardIndex, String content) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<CommonData> call = serverAPI.createComment(boardIndex, content);
            call.enqueue(new Callback<CommonData>() {
                @Override
                public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                    Log.d("aaaa", call.request().url().toString());
                    try {
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.lock();
                                }
                            });
                            getComment(boardIndex);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    Toast.makeText(CommentActivity.this, "댓글을 작성하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<CommonData> call, Throwable t) {

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
                    try {
                        if (response.isSuccessful()) {
                            arrayListOfCommentData = response.body().getArrayListOfCommentData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    commentListAdapter = new CommentListAdapter(arrayListOfCommentData);
                                    recyclerViewComment.setAdapter(commentListAdapter);
                                    recyclerViewComment.setLayoutManager(linearLayoutManager);
                                    editComment.setText("");
                                    uiLocker.unlock();
                                }
                            });
                        } else {
                            Toast.makeText(CommentActivity.this, "댓글을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("error", "Gson Converter Error is " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<CommentList> call, Throwable t) {

                }
            });
        }
    }
}
