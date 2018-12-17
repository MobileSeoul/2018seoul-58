package com.walk.angel.angelwalk.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.walk.angel.angelwalk.Activity.LoginActivity;
import com.walk.angel.angelwalk.Activity.RegisterActivity;
import com.walk.angel.angelwalk.Api.ServerAPI;
import com.walk.angel.angelwalk.Api.interceptor.CookieInterceptor;
import com.walk.angel.angelwalk.Data.CommonData;
import com.walk.angel.angelwalk.Data.SignupData;
import com.walk.angel.angelwalk.R;

import org.w3c.dom.Text;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import static android.content.Context.MODE_PRIVATE;
import static com.walk.angel.angelwalk.Api.ServerURL.BASE_URL;

public class SettingFragment extends Fragment {

    private EditText editNickname;
    private EditText editPassword;
    private TextView txtUserId;
    private Button btnNicknameChange;
    private Button btnPasswordChange;
    private Button btnLogout;

    private String token;
    private String userId;
    private String userPassword;
    private String userNickName;

    private SharedPreferences pref;
    private LinearLayout layoutSetting;
    private InputMethodManager touchBackground;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup settingFragment = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);



        return settingFragment;
    }

    @Override
    public void onViewCreated(@NonNull View settingFragment, @Nullable Bundle savedInstanceState) {
        pref = settingFragment.getContext().getSharedPreferences("login", MODE_PRIVATE);
        token = pref.getString("token", "");
        userId = pref.getString("id", "");
        userPassword = pref.getString("password", "");
        userNickName = pref.getString("nickName","");

        txtUserId = (TextView) settingFragment.findViewById(R.id.textId);
        txtUserId.setText(userId+" ");

        editNickname = (EditText) settingFragment.findViewById(R.id.editNickName);
        editNickname.setText(userNickName);

        editPassword = (EditText) settingFragment.findViewById(R.id.editPassword);
        editPassword.setText(userPassword);

        btnNicknameChange = (Button) settingFragment.findViewById(R.id.btnChangeNickname);
        btnPasswordChange = (Button) settingFragment.findViewById(R.id.btnChangePassword);
        btnLogout = (Button) settingFragment.findViewById(R.id.btnLogout);

        btnNicknameChange.setOnClickListener(btnClickListener);
        btnPasswordChange.setOnClickListener(btnClickListener);
        btnLogout.setOnClickListener(btnClickListener);

        layoutSetting = (LinearLayout) settingFragment.findViewById(R.id.layoutSetting);

        touchBackground = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutSetting.setOnClickListener(backClickListener);
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnChangeNickname:
                    if (editNickname.getText().toString() != null || !"".equals(editNickname.getText().toString())) {
                        connectServerForNickname(editNickname.getText().toString());
                    }else{
                        Toast.makeText(getView().getContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnChangePassword:
                    if (editPassword.getText().toString() == null || "".equals(editPassword.getText().toString())) {
                        Toast.makeText(getView().getContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }else if(editNickname.getText().toString() == userPassword || editNickname.getText().toString().equals(userPassword)){
                        Toast.makeText(getView().getContext(), "이전 비밀번호와 똑같습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        connectServerForPassword(userPassword, editPassword.getText().toString());
                    }
                    break;

                case R.id.btnLogout:
                    removePreferences();
                    Intent intentLogout = new Intent(getView().getContext(), LoginActivity.class);
                    startActivity(intentLogout);
                    getActivity().finish();
            }
        }
    };

    // inputmethod
    private View.OnClickListener backClickListener = new View.OnClickListener(){
        @Override
        public  void onClick(View v)
        {
            touchBackground.hideSoftInputFromWindow(editNickname.getWindowToken(), 0);
            touchBackground.hideSoftInputFromWindow(editPassword.getWindowToken(), 0);
        }
    };

    public void connectServerForNickname(final String userNickname){
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getActivity().getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<CommonData> call = serverAPI.sendNicknameUpdate(userNickname);

        call.enqueue(new Callback<CommonData>() {
            @Override
            public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                try{

                    if(response.isSuccessful()) { // 200번대

                        CommonData commonData = response.body();
                        String result = commonData.getResult();

                        if("Success".equals(result)){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("nickName", userNickname);
                            Toast.makeText(getView().getContext(), "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        Toast.makeText(getView().getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("error", "Login Error is " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<CommonData> call, Throwable t) {

            }
        });
    }

    public void connectServerForPassword(final String currentPassword, final String newPassword){
        CookieInterceptor cookieInterceptor = new CookieInterceptor(getActivity().getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(cookieInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);
        Call<CommonData> call = serverAPI.sendPasswordUpdate(currentPassword,  newPassword);

        call.enqueue(new Callback<CommonData>() {
            @Override
            public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                try{

                    if(response.isSuccessful()) { // 200번대

                        CommonData commonData = response.body();
                        String result = commonData.getResult();

                        Toast.makeText(getView().getContext(), result, Toast.LENGTH_SHORT).show();

                        if("Success".equals(result)){

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("password", newPassword);
                            Toast.makeText(getView().getContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getView().getContext(), commonData.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getView().getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("error", "Modify Password Error is " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<CommonData> call, Throwable t) {

            }
        });
    }
    //값 삭제하기
    private  void  removePreferences(){
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
