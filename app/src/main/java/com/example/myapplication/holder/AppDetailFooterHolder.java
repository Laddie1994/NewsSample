package com.example.myapplication.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.AppDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.DownloadInfo;
import com.example.myapplication.manager.DownloadManager;
import com.example.myapplication.presenter.VideoDetailPersenter;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2016/2/24.
 */
public class AppDetailFooterHolder extends BaseHolder<AppDetail> implements View.OnClickListener, DownloadManager.DownloadObserver {

    public ImageView mIvFavor;
    private ImageView mIvShare;
    private Button mBtnDownload;
    private TextView mTvProgress;
    private ProgressBar mPb;
    private DownloadManager mDownloadManager;
    private DownloadInfo mDownloadInfo;
    private CheckBox mCbState;
    private ImageView mIvDel;

    public AppDetailFooterHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = mInflater.inflate(R.layout.app_detail_footer, null);
        mIvFavor = (ImageView) view.findViewById(R.id.app_detail_favor);
        mIvShare = (ImageView) view.findViewById(R.id.app_detail_share);
        mBtnDownload = (Button) view.findViewById(R.id.app_detail_download);
        mTvProgress = (TextView) view.findViewById(R.id.app_detail_progress);
        mPb = (ProgressBar) view.findViewById(R.id.app_detail_pb);
        mCbState = (CheckBox) view.findViewById(R.id.app_detail_state);
        mIvDel = (ImageView) view.findViewById(R.id.app_detail_del);
        mPb.setMax(100);
        mIvFavor.setOnClickListener(this);
        mIvShare.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
        mPb.setOnClickListener(this);
        mCbState.setOnClickListener(this);
        mIvDel.setOnClickListener(this);
        mCbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDownloadManager.pause(mDownloadInfo);
                } else {
                    mDownloadManager.download(mDownloadInfo);
                }
            }
        });
        return view;
    }

    @Override
    public void initData() {
        refreshState(mDownloadInfo);
    }

    private void refreshState(DownloadInfo downloadInfo) {
        int state = downloadInfo.getDownloadState();
        int progress = 0;
        switch (state) {
            case DownloadManager.STATE_NONE:
                mPb.setVisibility(View.GONE);
                mBtnDownload.setVisibility(View.VISIBLE);
                mBtnDownload.setText("下载");
                mCbState.setVisibility(View.GONE);
                mIvDel.setVisibility(View.GONE);
                mIvShare.setVisibility(View.VISIBLE);
                mIvFavor.setVisibility(View.VISIBLE);
                break;
            case DownloadManager.STATE_PAUSE:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mBtnDownload.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.VISIBLE);
                mTvProgress.setText("暂停");
                mCbState.setVisibility(View.VISIBLE);
                mIvDel.setVisibility(View.VISIBLE);
                mIvFavor.setVisibility(View.GONE);
                mIvShare.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_ERROR:
                mPb.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.GONE);
                mBtnDownload.setVisibility(View.VISIBLE);
                mBtnDownload.setText("失败");
                mCbState.setVisibility(View.VISIBLE);
                mIvDel.setVisibility(View.VISIBLE);
                mIvFavor.setVisibility(View.GONE);
                mIvShare.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_WAITING:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mTvProgress.setVisibility(View.VISIBLE);
                mTvProgress.setText("请稍候");
                mBtnDownload.setVisibility(View.GONE);
                mCbState.setVisibility(View.VISIBLE);
                mIvDel.setVisibility(View.VISIBLE);
                mIvFavor.setVisibility(View.GONE);
                mIvShare.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mTvProgress.setVisibility(View.VISIBLE);
                progress = (int) (downloadInfo.getCurrentSize() * 100 / downloadInfo.getAppSize());
                mTvProgress.setText(progress + "%");
                mBtnDownload.setVisibility(View.GONE);
                mCbState.setVisibility(View.VISIBLE);
                mIvDel.setVisibility(View.VISIBLE);
                mIvFavor.setVisibility(View.GONE);
                mIvShare.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOED:
                mPb.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.GONE);
                mBtnDownload.setVisibility(View.VISIBLE);
                mBtnDownload.setText("安装");
                mCbState.setVisibility(View.GONE);
                mIvDel.setVisibility(View.GONE);
                mIvFavor.setVisibility(View.VISIBLE);
                mIvShare.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void startObserver(){
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver(){
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void setData(AppDetail data) {
        if(mDownloadManager == null){
            mDownloadManager = DownloadManager.getInstance();
        }
        mDownloadInfo = mDownloadManager.getDownloadInfo(data.gameinfo.appid);
        if(mDownloadInfo == null){
            mDownloadInfo = DownloadInfo.clone(data.gameinfo);
        }
        super.setData(data);
    }

    @Override
    public void onClick(View v) {
        VideoDetailPersenter presenter = ((AppDetailActivity)mContext).mPresenter;
        switch (v.getId()) {
            case R.id.app_detail_favor:
                presenter.heandleFavor();
                break;
            case R.id.app_detail_share:
                presenter.heandleShare(mData.gameinfo);
                break;
            case R.id.app_detail_download:
                presenter.heandleDown(mData);
                break;
            case R.id.app_detail_del:
                mDownloadManager.cancel(mDownloadInfo);
                break;
            case R.id.app_detail_state:

        }
    }

    public void download(){
        int state = mDownloadInfo.getDownloadState();
        if (state == DownloadManager.STATE_NONE
                || state == DownloadManager.STATE_PAUSE
                || state == DownloadManager.STATE_ERROR) {
            mDownloadManager.download(mDownloadInfo);
        } else if (state == DownloadManager.STATE_WAITING
                || state == DownloadManager.STATE_DOWNLOADING) {
            mDownloadManager.pause(mDownloadInfo);
        } else if (state == DownloadManager.STATE_DOWNLOED) {
            mDownloadManager.install(mDownloadInfo);
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshProgress(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshProgress(info);
    }

    private void refreshProgress(final DownloadInfo info) {
        if(info != null && getData() != null){
            AppInfo appInfo = getData().gameinfo;
            if (appInfo.appid == info.getId()) {
                UIUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshState(mDownloadInfo);
                    }
                });
            }
        }
    }
}
