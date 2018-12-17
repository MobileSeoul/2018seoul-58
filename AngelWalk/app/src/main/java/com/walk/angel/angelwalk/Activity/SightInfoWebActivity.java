package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.R;

import org.w3c.dom.Text;

public class SightInfoWebActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_info_web);

        Intent intent = getIntent();
        SightsData data = (SightsData) intent.getSerializableExtra("sightsInfo");
        String url = data.getURL();

        TextView sightTitle = (TextView) findViewById(R.id.sightTitle);
        sightTitle.setText(data.getName());


        if(url != null && !url.equals("")){
            onLoadWebView(url);
        }
    }

    public void onLoadWebView(String url){

        mWebView = (WebView) findViewById(R.id.sightInfo_webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebView.loadUrl(url);

    }
}
