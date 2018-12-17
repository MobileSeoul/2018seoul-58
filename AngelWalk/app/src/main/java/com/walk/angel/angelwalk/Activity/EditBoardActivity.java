package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.ServerURL;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.CommonData;
import com.walk.angel.angelwalk.R;
import com.walk.angel.angelwalk.UILocker;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditBoardActivity extends AppCompatActivity implements ServerURL{

    @BindView(R.id.editTitle)
    EditText editTitle;

    @BindView(R.id.editContent)
    EditText editContent;

    @BindView(R.id.btnCreateBoard)
    Button btnCreateBoard;

    private UILocker uiLocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);
        ButterKnife.bind(this);
        uiLocker = new UILocker(EditBoardActivity.this);
        btnCreateBoard.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnCreateBoard:
                    uiLocker.lock();
                    String title = editTitle.getText().toString();
                    String content = editContent.getText().toString();
                    new ConnectServer().createBoard(title, content);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditBoardActivity.this, BoardActivity.class);
        startActivity(intent);
        finish();
    }

    class ConnectServer {
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        public void createBoard(String title, String content) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<CommonData> call = serverAPI.createBoard(title, content);

            call.enqueue(new Callback<CommonData>() {
                @Override
                public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                    try {
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    Toast.makeText(EditBoardActivity.this, "게시글을 게시하는데 성공 하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditBoardActivity.this, BoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    uiLocker.unlock();
                                    Toast.makeText(EditBoardActivity.this, "게시글을 게시하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
    }
}
