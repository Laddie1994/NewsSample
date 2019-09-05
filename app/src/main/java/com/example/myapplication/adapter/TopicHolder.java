package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public class TopicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView icon;
    public TextView title;
    private OnRecyclerViewItemClickListener mItemClickListener;

    public TopicHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
        mItemClickListener = itemClickListener;
        icon = (ImageView) itemView.findViewById(R.id.topic_item_icon);
        title = (TextView) itemView.findViewById(R.id.topic_item_title);
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null){
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
