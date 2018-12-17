package com.walk.angel.angelwalk.Api.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {
    private Context context;

    public CookieInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("token")){
            request = request.newBuilder().addHeader("token", sharedPreferences.getString("token", "")).build();
        }else{
            Log.d("error", "Login Token is Empty");
        }
        Response response = chain.proceed(request);
        return response;
    }
}
