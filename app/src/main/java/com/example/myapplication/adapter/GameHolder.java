package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.DownloadInfo;
import com.example.myapplication.manager.DownloadManager;
import com.example.myapplication.utils.ToastUtils;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title, size, tag;
    public Button mBtnDown;
    public ImageView avatar;
    public FrameLayout roundedFrame;

    private TextView mTvProgress;
    private ProgressBar mPb;

    private DownloadManager mDownloadManager;
    public DownloadInfo downloadInfo;

    private OnRecyclerViewItemClickListener mItemClickListener;

    public GameHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.game_item_title);
        size = (TextView) itemView.findViewById(R.id.game_item_size);
        roundedFrame = (FrameLayout) itemView.findViewById(R.id.game_item_rounded_frame);
        mBtnDown = (Button) itemView.findViewById(R.id.game_item_btn_download);
        avatar = (ImageView) itemView.findViewById(R.id.game_item_avatar);
        tag = (TextView) itemView.findViewById(R.id.game_item_tag);
        mPb = (ProgressBar) itemView.findViewById(R.id.game_item_pb);
        mTvProgress = (TextView) itemView.findViewById(R.id.game_item_progress);
        mPb.setMax(100);
        mBtnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    public void download() {
        if (mDownloadManager == null)
            mDownloadManager = DownloadManager.getInstance();
        int state = downloadInfo.getDownloadState();
        if (state == DownloadManager.STATE_NONE
                || state == DownloadManager.STATE_PAUSE
                || state == DownloadManager.STATE_ERROR) {
            mDownloadManager.download(downloadInfo);
        } else if (state == DownloadManager.STATE_WAITING
                || state == DownloadManager.STATE_DOWNLOADING) {
            mDownloadManager.pause(downloadInfo);
        } else if (state == DownloadManager.STATE_DOWNLOED) {
            mDownloadManager.install(downloadInfo);
        }
    }

    public void refreshState(DownloadInfo info) {
        int progress = (int) (info.getCurrentSize() * 100 / info.getAppSize());
        switch (info.getDownloadState()) {
            case DownloadManager.STATE_NONE:
                mPb.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.GONE);
                mBtnDown.setVisibility(View.VISIBLE);
                mBtnDown.setText("下载");
                break;
            case DownloadManager.STATE_PAUSE:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mBtnDown.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.VISIBLE);
                mTvProgress.setText("暂停");
                break;
            case DownloadManager.STATE_ERROR:
                mPb.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.GONE);
                mBtnDown.setVisibility(View.VISIBLE);
                mBtnDown.setText("失败");
                break;
            case DownloadManager.STATE_WAITING:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mTvProgress.setVisibility(View.VISIBLE);
                mTvProgress.setText("请稍候");
                mBtnDown.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(progress);
                mTvProgress.setVisibility(View.VISIBLE);
                mTvProgress.setText(progress + "%");
                mBtnDown.setVisibility(View.GONE);
                break;
            case DownloadManager.STATE_DOWNLOED:
                mPb.setVisibility(View.GONE);
                mTvProgress.setVisibility(View.GONE);
                mBtnDown.setVisibility(View.VISIBLE);
                mBtnDown.setText("安装");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null){
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
        ToastUtils.showToast(getAdapterPosition() + "");
    }
}
