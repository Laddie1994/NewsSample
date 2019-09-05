package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myapplication.bean.NewsDetail;
import com.example.myapplication.bean.NewsShare;
import com.example.myapplication.presenter.NewsDocPresenter;
import com.example.myapplication.utils.FontSizeUtils;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.NewsDocView;
import com.example.myapplication.widget.MultiStateView;
import com.google.gson.Gson;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by MBENBEN on 2016/1/4.
 */
public class NewsDocDetailActivity extends BaseActivity implements NewsDocView {

    private MultiStateView mMultiState;
    private WebView mWebView;
    private String mDetailUrl;
    private NewsDocPresenter mPersenter;
    private NewsShare mNewsShare;

    private FrameLayout vedio;
    private TextView mTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_doc_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        mPersenter = new NewsDocPresenter(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mDetailUrl = extras.getString(Constans.Detail.DETAIL_ID);
            mPersenter.requestData(mDetailUrl);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        ShareSDK.stopSDK(AppContext.getContext());
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mMultiState = (MultiStateView) findViewById(R.id.app_msl);
        mWebView = (WebView) findViewById(R.id.detail_web_view);
        vedio = (FrameLayout) findViewById(R.id.detail_video);
        mTitle = (TextView) findViewById(R.id.toolbarTitle);
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setBuiltInZoomControls(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setDisplayZoomControls(false);
        }
        mWebView.addJavascriptInterface(new CodeBoyJsInterface(), "codeboy");
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
//        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setBackgroundColor(0);
        mWebView.getBackground().setAlpha(255);
        mWebView.setBackgroundResource(R.drawable.list_item_bg_normal);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        /*mMultiState.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(this);*/
    }

    private void onWebTitle(WebView view, String title) {
        if (mWebView != null) {
            setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_desc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_font_size:
                mPersenter.handleFont();
                break;
            case R.id.menu_share:
                mPersenter.handleShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showError(String error) {
        mMultiState.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void handleFont() {
        String[] fonts = UIUtils.getStringArray(R.array.fonts_size);
        new MaterialDialog.Builder(this)
                .items(fonts)
                .itemsCallbackSingleChoice(FontSizeUtils.getSaveFontIndex(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        FontSizeUtils.saveFontSize(which);
                        mWebView.getSettings().setTextSize(FontSizeUtils.getFontSize(which));
                        return true;
                    }
                })
                .negativeText("取消")
                .show();
    }

    @Override
    public void handleShare() {
        if(mNewsShare != null){
            ShareSDK.initSDK(AppContext.getContext());
            OnekeyShare oks = new OnekeyShare();
            oks.disableSSOWhenAuthorize();
            oks.setTitle(mNewsShare.sharetitle);
            oks.setImageUrl(mNewsShare.shareimgUrl);
            oks.setText(Html.fromHtml(mNewsShare.sharedesc).toString());
            oks.setUrl(mNewsShare.sharelink);
            oks.setSiteUrl(mNewsShare.sharelink);
            oks.show(AppContext.getContext());
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess(NewsDetail newsDetail) {
        if (mWebView == null) {
            return;
        }
        mMultiState.getView(MultiStateView.VIEW_STATE_CONTENT);
        mDetailUrl = newsDetail.body.shareurl;
        StringBuffer body = new StringBuffer("<body>");
        body.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">");
        body.append(String.format("<div class='title'>%s</div>", newsDetail.body.title));
        body.append(String.format("<div class='authortime'>%s</div>", newsDetail.body.editTime));
        body.append(newsDetail.body.text);
        body.append("</body>");
        mWebView.loadDataWithBaseURL("http://d.ifengimg.com", body.toString(), "text/html", "utf-8", null);
//        setTitle();
        mTitle.setText(newsDetail.body.title);
    }

    @Override
    public void showNoMore() {
    }

    @Override
    public void showLoading() {
        mMultiState.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            showLoading();
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDetailUrl = url;
            mWebView.loadUrl("javascript:codeboy.callme(JSON.stringify({'sharetitle':sharetitle.toString(),'sharelink':sharelink.toString(),'shareimgUrl':shareimgUrl.toString(),'sharedesc':sharedesc.toString()}))");
            mMultiState.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebTitle(view, title);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null ;
                return;
            }
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            parent.removeView(mWebView);
            vedio.addView(view);
            myView = view;
            setFullScreen();
            myCallback = callback;
        }

        private View myView = null;
        private CustomViewCallback myCallback = null;

        public void onHideCustomView() {

            if (myView != null) {

                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null ;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView( mWebView);
                myView = null;
                quitFullScreen();
            }
        }
    }

    class CodeBoyJsInterface{
        @JavascriptInterface
        public void callme(final String str){
            Gson gson = new Gson();
            mNewsShare = gson.fromJson(str, NewsShare.class);
            LogUtils.e(mNewsShare.toString());
        }
    }
}