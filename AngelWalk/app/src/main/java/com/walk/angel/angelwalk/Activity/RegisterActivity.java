package com.walk.angel.angelwalk.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Data.SignupData;
import com.walk.angel.angelwalk.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.walk.angel.angelwalk.Api.ServerURL.BASE_URL;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText editId;
    private EditText editPassword;
    private EditText editPasswordCheck;
    private EditText editUserNickName;
    private ImageView setImage;
    private Boolean bPasswordCheck;
    private InputMethodManager touchBackground;
    private LinearLayout layoutRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bPasswordCheck = false;

        layoutRegister = (LinearLayout)findViewById(R.id.layoutRegister);
        //setImage = (ImageView)findViewById(R.id.setImage);

        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPasswordCheck = (EditText) findViewById(R.id.editPasswordCheck);
        editUserNickName = (EditText) findViewById(R.id.editUserNickname);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btnClickListener);

        touchBackground = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutRegister.setOnClickListener(backClickListener);

        editPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editPassword.getText().toString();
                String confirm = editPasswordCheck.getText().toString();

                if(password.equals(confirm)){
                   // setImage.setImageResource(R.drawable.sign_up_password_pass);
                    editPasswordCheck.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.sign_up_password_pass,0);
                    bPasswordCheck = true;
                }
                else {
                    //setImage.setImageResource(R.drawable.sign_up_password_fail);
                    editPasswordCheck.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.sign_up_password_fail,0);
                    bPasswordCheck = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    // inputmethod
    private View.OnClickListener backClickListener = new View.OnClickListener(){
        @Override
        public  void onClick(View v)
        {
            touchBackground.hideSoftInputFromWindow(editId.getWindowToken(), 0);
            touchBackground.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
            touchBackground.hideSoftInputFromWindow(editPasswordCheck.getWindowToken(), 0);
            touchBackground.hideSoftInputFromWindow(editUserNickName.getWindowToken(), 0);

        }
    };

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnRegister:
                    if(bPasswordCheck){

                        connectServer(editId.getText().toString(),
                                editPassword.getText().toString(),
                                editPasswordCheck.getText().toString(),
                                editUserNickName.getText().toString());

                    }else{

                        Toast.makeText(RegisterActivity.this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

    public void connectServer(String userId, String userPassword, String userPasswordCheck, String userNickname){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<SignupData> call = serverAPI.sendSignupData(userId, userNickname, userPassword, userPasswordCheck);

        call.enqueue(new Callback<SignupData>() {
            @Override
            public void onResponse(Call<SignupData> call, final Response<SignupData> response) {
                try{

                    Log.d("info", "response :: " + response.code());
                    if(response.isSuccessful()) { // 200번대

                        SignupData signupData = response.body();
                        String result = signupData.getResult();

                        Log.d("info", "response :: " + response.code());
                        if("Success".equals(result)){

                            SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("nickName", editUserNickName.getText().toString());
                            editor.commit();

                            Intent intentMain = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intentMain);
                            finish();;
                        }

                    }else{
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    Log.d("error", "Login Error is " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<SignupData> call, Throwable t) {

            }
        });
    }
}
