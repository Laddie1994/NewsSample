package com.example.myapplication.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.manager.ImageLoaderManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2016/2/24.
 */
public class AppDetailHeanderHolder extends BaseHolder<AppDetail> {

    private TextView mTvVersion;
    private TextView mTvName;
    public ImageView mIvAvatar;

    public AppDetailHeanderHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = mInflater.inflate(R.layout.app_detail_heander, null);
        mIvAvatar = (ImageView) view.findViewById(R.id.app_detail_avatar);
        mTvName = (TextView) view.findViewById(R.id.app_detail_name);
        mTvVersion = (TextView) view.findViewById(R.id.app_detail_version);
        return view;
    }

    @Override
    public void initData() {
        mTvName.setText(mData.gameinfo.apptitle);
        mTvVersion.setText(String.format("版本:%s", mData.gameinfo.appversion));
        DisplayImageOptions options = ImageLoaderManager.getInstance(mContext).getDisplayOptions(6);
        ImageLoader.getInstance()
                .displayImage(mData.gameinfo.applogo, mIvAvatar, options);
    }
}
