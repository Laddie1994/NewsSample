package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.holder.AppDetailCenterHolder;
import com.example.myapplication.holder.AppDetailFooterHolder;
import com.example.myapplication.holder.AppDetailHeanderHolder;
import com.example.myapplication.presenter.VideoDetailPersenter;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.AppDetailView;
import com.example.myapplication.widget.MultiStateView;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by MBENBEN on 2016/1/12.
 */
public class AppDetailActivity extends BaseActivity implements AppDetailView {

    public VideoDetailPersenter mPresenter;
    public FrameLayout mHeander;
    public FrameLayout mFooter;
    public FrameLayout mCenter;
    protected AppDetailHeanderHolder mDetailHeander;
    private MultiStateView mMultiLayout;
    private boolean isFavor;
    private AppDetailCenterHolder mDetailCenter;
    private AppDetailFooterHolder mDetailFooter;
    private String mDetailUrl;
    private Toolbar mToolbar;

    @Override
    protected void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong(Constans.Detail.DETAIL_ID);
            mPresenter = new VideoDetailPersenter(this);
            mDetailUrl = Constans.UrlConstants.HULU_URL + "detail/ANDROID/3.6" + "?app_id=" + id;
            mPresenter.requestData(mDetailUrl);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        mMultiLayout = (MultiStateView) findViewById(R.id.app_msl);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mHeander = (FrameLayout) findViewById(R.id.app_detail_heander);
        mFooter = (FrameLayout) findViewById(R.id.app_detail_footer);
        mCenter = (FrameLayout) findViewById(R.id.app_detail_center);

        mDetailHeander = new AppDetailHeanderHolder(this);
        mHeander.addView(mDetailHeander.getContentView());
        mDetailFooter = new AppDetailFooterHolder(this);
        mDetailFooter.stopObserver();
        mFooter.addView(mDetailFooter.getContentView());
        mDetailCenter = new AppDetailCenterHolder(this);
        mCenter.addView(mDetailCenter.getContentView());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mMultiLayout.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestData(mDetailUrl);
            }
        });

        mDetailCenter.setListener(new AppDetailCenterHolder.OnAppCenterListener() {
            @Override
            public void onScrrenShotClick(View view, int position, String[] images) {
                mPresenter.showScrrenImage(view, position, images);
            }

            @Override
            public void onSimilarClick(AppDetail.Similar similar) {
                mPresenter.showSimail(similar);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDetailFooter != null) {
            mDetailFooter.startObserver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDetailFooter != null) {
            mDetailFooter.stopObserver();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mDetailFooter != null) {
            mDetailFooter.stopObserver();
        }
        ShareSDK.stopSDK(AppContext.getContext());
    }

    @Override
    public void heandleShare(AppInfo appInfo) {
        if (appInfo == null) {
            return;
        }
        ShareSDK.initSDK(AppContext.getContext());
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(appInfo.apptitle);
        oks.setImageUrl(appInfo.applogo);
        oks.setText(Html.fromHtml(appInfo.appdesc.substring(0, 40)).toString());
        oks.setUrl(appInfo.shareurl);
        oks.setSiteUrl(appInfo.shareurl);
        oks.show(AppContext.getContext());
    }

    @Override
    public void showDialog(AppInfo appInfo) {
        final ArrayList<AppInfo.CloudDownload> downList = appInfo.clouddownlist;
        String[] downStr = new String[downList.size()];
        for (int i = 0; i < downList.size(); i++) {
            downStr[i] = downList.get(i).name;
        }
        new MaterialDialog.Builder(this)
                .items(downStr)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        String url = downList.get(which).url;
                        if (!TextUtils.isEmpty(url)) {
                            Bundle extras = new Bundle();
                            extras.putString(DownloadDetailActivity.DETAIL_URI, url);
                            extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.DOWNLOAD_DETAIL);
                            UIUtils.skipDownPage(dialog.getContext(), extras);
                        }
                    }
                }).show();
    }

    @Override
    public void heandleFavor() {
        isFavor = !isFavor;
        mDetailFooter.mIvFavor.setSelected(isFavor);
    }

    @Override
    public void downGame(AppDetail appDetail) {
        mDetailFooter.startObserver();
        mDetailFooter.download();
    }

    @Override
    public void showImage(View view, int position, String[] imageresource) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        Bundle bundle = new Bundle();
        bundle.putInt(ImageDetailActivity.CURRENT_POSITION, position);
        bundle.putStringArray(ImageDetailActivity.STRING_URIS, imageresource);
        Intent intent = new Intent(AppContext.getContext(), ImageDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void showSimail(AppDetail.Similar similar) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constans.Detail.DETAIL_ID, similar.appid);
        UIUtils.skipAppDetail(this, bundle);
        Intent intent = new Intent(AppContext.getContext(), AppDetailActivity.class);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.transation_in, R.anim.transation_out);
        startActivity(intent, options.toBundle());
//        finish();
    }

    @Override
    public void startSkip(AppInfo.CloudDownload cloudDownload) {
        Bundle extras = new Bundle();
        extras.putString(DownloadDetailActivity.DETAIL_URI, cloudDownload.url);
        extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.DOWNLOAD_DETAIL);
        UIUtils.skipDownPage(this, extras);
    }

    @Override
    public void showError() {
    }

    @Override
    public void showSuccess(AppDetail appDetail) {
        mDetailHeander.setData(appDetail);
        mDetailCenter.setData(appDetail);
        mDetailFooter.setData(appDetail);
        mMultiLayout.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void showLoading() {

    }
}
