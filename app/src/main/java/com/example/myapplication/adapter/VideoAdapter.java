package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.bean.AppInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by MBENBEN on 2016/3/16.
 */
public class VideoAdapter extends AppAdapter<AppInfo> {

    private OnRecyclerViewItemClickListener mItemClickListener;
    private OnVideoClickLstener mVideoClickLstener;

    public void setmVideoClickLstener(OnVideoClickLstener mVideoClickLstener) {
        this.mVideoClickLstener = mVideoClickLstener;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.video_list_item, null);
        return new VideoHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoHolder videoholder = (VideoHolder) holder;
        final AppInfo appInfo = getItem(position);
        videoholder.itemLayout.setTag(appInfo);
        ImageLoader.getInstance().displayImage(appInfo.applogo, videoholder.ivAvatar
                , new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(8)).build());
        videoholder.tvTitle.setText(appInfo.apptitle);
        videoholder.tvSystem.setText(appInfo.system);
        videoholder.tvDesc.setText(appInfo.shortdesc);
        videoholder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoClickLstener != null)
                    mVideoClickLstener.onDownloadClick(appInfo);
            }
        });
        switch (appInfo.getMovieType()) {
            case 0:
                videoholder.tvType.setBackgroundResource(R.mipmap.icon_movie_type_gao_qing);
                videoholder.tvType.setText("高清");
                break;
            case 1:
                videoholder.tvType.setBackgroundResource(R.mipmap.icon_movie_type_bd);
                videoholder.tvType.setText("标清");
                break;
            case 2:
                videoholder.tvType.setBackgroundResource(R.mipmap.icon_movie_type_ts);
                videoholder.tvType.setText("枪版");
                break;
        }
    }

    public interface OnVideoClickLstener {
        void onDownloadClick(AppInfo appInfo);
    }
}
