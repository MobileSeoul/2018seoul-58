package com.walk.angel.angelwalk.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.ServerURL;
import com.walk.angel.angelwalk.Data.LoginData;
import com.walk.angel.angelwalk.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements ServerURL {

    private Button btnLogin;
    private Button btnRegister;
    private LinearLayout layoutLogin;
    private EditText editId;
    private EditText editPassword;
    private InputMethodManager touchBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnClickListener);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btnClickListener);

        touchBackground = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutLogin.setOnClickListener(backClickListener);

        checkAutoLogin();
    }

    public void checkAutoLogin(){
        boolean check = getPreferences();
        if (check){
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        }
        else {
            // to do nothing
        }
    }

    private View.OnClickListener backClickListener = new View.OnClickListener(){
        @Override
        public  void onClick(View v)
        {
            touchBackground.hideSoftInputFromWindow(editId.getWindowToken(), 0);
            touchBackground.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
        }
    };

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnLogin:
                    connectServer(editId.getText().toString(), editPassword.getText().toString());
                    break;

                case R.id.btnRegister:
                    Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intentRegister);
                    break;

            }
        }
    };

    public void connectServer(final String userId, final String userPassword){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<LoginData> call = serverAPI.sendLoginData(userId, userPassword);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                try{
                    if(response.isSuccessful()) { // 200번대
                        LoginData loginData = response.body();
                        String result = loginData.getResult();

                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                        if ("Success".equals(result)){
                            setPreferences(userId, userPassword, loginData.getToken());
                            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intentMain);
                            finish();
                        }
                        else {
                            //to do nothing
                        }
                    }else{
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    Log.d("error", "Login Error is " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

    // 값 불러오기
    private boolean getPreferences(){
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        String myid = pref.getString("id", "");
        String mypassword = pref.getString("password", "");
        if(myid == null || myid.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    // 값 저장하기
    private void setPreferences(String id, String password, String token){
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", id);
        editor.putString("password", password);
        editor.putString("token", token);
        editor.commit();
    }

}
