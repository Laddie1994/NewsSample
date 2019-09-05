package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by MBENBEN on 2016/1/15.
 */
public class DownloadDetailActivity extends BaseActivity {

    public static final String DETAIL_URI = "detail_uri";

    private String mDetailUrl;
    private WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_download_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mDetailUrl = args.getString(DETAIL_URI);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        mWebView = (WebView) findViewById(R.id.down_detail_web_view);
        initWebView();
        mWebView.loadUrl(mDetailUrl);
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setDisplayZoomControls(false);
        }
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mDetailUrl = url;
//            showLoding();
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            hideLoading();
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            onWebTitle(view, title);
        }
    }
}
