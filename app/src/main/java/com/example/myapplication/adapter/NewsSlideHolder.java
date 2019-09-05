package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public class NewsSlideHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvTitle, tvTime;
    ImageView iv1, iv2, iv3;
    OnRecyclerViewItemClickListener mItemClickListener;

    public NewsSlideHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
        tvTitle = (TextView) itemView.findViewById(R.id.news_item_title);
        tvTime = (TextView) itemView.findViewById(R.id.news_item_time);
        iv1 = (ImageView) itemView.findViewById(R.id.news_item_image1);
        iv2 = (ImageView) itemView.findViewById(R.id.news_item_image2);
        iv3 = (ImageView) itemView.findViewById(R.id.news_item_image3);
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null){
            mItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
