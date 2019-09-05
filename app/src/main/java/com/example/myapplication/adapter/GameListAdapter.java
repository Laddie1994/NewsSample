package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AppContext;
import com.example.myapplication.R;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.DownloadInfo;
import com.example.myapplication.manager.DownloadManager;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.utils.UIUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2016/2/27.
 */
public class GameListAdapter extends AppAdapter<AppInfo> implements DownloadManager.DownloadObserver {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public GameListAdapter(OnRecyclerViewItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, null);
        GameHolder gameHolder = new GameHolder(view, mItemClickListener);
        return gameHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GameHolder gameHolder = (GameHolder) holder;
        AppInfo appInfo = getItem(position);
        gameHolder.downloadInfo = DownloadManager.getInstance().getDownloadInfo(appInfo.appid);
        if(gameHolder.downloadInfo == null){
            gameHolder.downloadInfo = DownloadInfo.clone(appInfo);
        }
        String category = appInfo.categoryname;
        if ("角色扮演".equals(category)) {
            gameHolder.tag.setTextColor(UIUtils.getColor(R.color.fallow_color));
            gameHolder.roundedFrame.setBackgroundResource(R.drawable.rounded_image_fallow);
        } else if ("动作射击".equals(category)) {
            gameHolder.tag.setTextColor(UIUtils.getColor(R.color.motion_color));
            gameHolder.roundedFrame.setBackgroundResource(R.drawable.rounded_image_motion);
        } else if ("休闲益智".equals(category)) {
            gameHolder.tag.setTextColor(UIUtils.getColor(R.color.role_color));
            gameHolder.roundedFrame.setBackgroundResource(R.drawable.rounded_image_role);
        }
        DisplayImageOptions options = ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions(12);
        ImageLoader.getInstance().displayImage(appInfo.applogo, gameHolder.avatar, options);
        gameHolder.title.setText(appInfo.apptitle);
        gameHolder.size.setText(appInfo.appsize + "M");
        gameHolder.tag.setText(appInfo.categoryname);
    }
}
