package com.pubwar.tv;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Button;
import android.provider.Settings;

public class MainActivity extends Activity {

    WebView myWebView;
    String url = "";
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        url  = getIntent().getStringExtra("url");
//
//        assert url != null;
        if(url.isEmpty())
            url = getString(R.string.base_url);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.d(TAG, "onCreate: " + width + ":" + height);

        @SuppressLint("HardwareIds") String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG, "androidID: " + androidID);

        url= url + androidID;

        Log.d(TAG, "onCreate: " + url);
//        androidID: 1a7c6d02ab7f4200
//        uniqueID: aeb7fff1-d7f4-4ada-b0f9-6fa4034ee345

//        c575a4c179978062
    }

    @SuppressLint({"SetJavaScriptEnabled", "WebViewApiAvailability"})
    @Override
    protected void onResume() {
        super.onResume();
//        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);

        myWebView = (WebView) findViewById(R.id.main_webview);

        myWebView.setWebViewClient(new MyWebClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // This cancels the load and shows default Android SSL error page
                handler.cancel();  // Do NOT use handler.proceed() in production
            }
        });

        myWebView.loadUrl(url);



        WebView.setWebContentsDebuggingEnabled(true);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // Set the initial scale (100 means 100%)
//        myWebView.setInitialScale(20);


        // Enable zoom controls
        webSettings.setSupportZoom(true);
//        webSettings.setDisplayZoomControls(true);

        Log.d(TAG, "onResume: " + webSettings.getTextZoom());

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        Log.d(TAG, "onResume: " + density);
//        webSettings.setTextZoom((int)(100/density));
        webSettings.setBuiltInZoomControls(true);

        Button refresh = findViewById(R.id.refresh_button);
        refresh.requestFocus();
        findViewById(R.id.refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });

        findViewById(R.id.zoom_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });

        findViewById(R.id.zoom_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();
            }
        });


        findViewById(R.id.clear_cache_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWebViewCache();
            }
        });
    }


    private void clearWebViewCache() {
        // Clear cache
        myWebView.clearCache(true);

        // Clear cookies
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.flush();

        // Clear Web Storage
        WebStorage.getInstance().deleteAllData();
    }

    private void refreshPage() {
        if (myWebView != null) {
            myWebView.reload();
        }
    }

    private void zoomIn()
    {
        if (myWebView != null) {
            myWebView.zoomIn();
        }
    }

    private void zoomOut()
    {
        if (myWebView != null) {
            myWebView.zoomOut();
        }
    }
}
