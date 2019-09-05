package com.example.myapplication.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseViewHolder;
import com.example.myapplication.bean.PictureInfo;
import com.example.myapplication.manager.ImageLoaderManager;
import com.github.library.PinterestLikeAdapterView.PLAImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2016/3/15.
 */
public class PictureItemHolder extends BaseViewHolder<PictureInfo> {

    private PLAImageView imageView;
    private TextView textView;

    public PictureItemHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = mInflater.inflate(R.layout.picture_list_item, null);
        imageView = (PLAImageView) view.findViewById(R.id.pictureImageView);
        textView = (TextView) view.findViewById(R.id.pictureTextView);
        return view;
    }

    @Override
    public void initData() {
        if(!TextUtils.isEmpty(mData.thumbnail_url)){
            ImageLoader.getInstance().displayImage(mData.thumbnail_url, imageView,
                    ImageLoaderManager.getInstance(mContext).getDisplayOptions());
            imageView.setImageWidth(mData.thumbnail_width);
            imageView.setImageHeight(mData.thumbnail_height);
            textView.setText(mData.abs);
        }
    }
}
