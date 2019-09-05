package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.LogUtils;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public class NewsDescHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvTitle, tvTime;
    ImageView ivAvatar;
    View itemLayout;
    OnRecyclerViewItemClickListener mItemClickListener;

    public NewsDescHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
        ivAvatar = (ImageView) itemView.findViewById(R.id.news_item_icon);
        tvTime = (TextView) itemView.findViewById(R.id.news_item_time);
        tvTitle = (TextView) itemView.findViewById(R.id.news_item_title);
        itemLayout = itemView;
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null){
            mItemClickListener.onItemClick(v, getLayoutPosition());
            LogUtils.e(getLayoutPosition() + "---------");
        }
    }
}
