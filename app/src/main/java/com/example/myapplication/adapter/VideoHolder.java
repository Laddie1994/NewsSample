package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView.*;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public class VideoHolder extends ViewHolder implements View.OnClickListener {

    public ImageView ivAvatar;
    public TextView tvType, tvTitle, tvDesc, tvSystem;
    public Button btnDown;
    public View itemLayout;
    private OnRecyclerViewItemClickListener mItemClickListener;

    public VideoHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        this.mItemClickListener = itemClickListener;
        itemLayout = itemView;
        itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
        ivAvatar = (ImageView) itemView.findViewById(R.id.video_avatar);
        tvTitle = (TextView) itemView.findViewById(R.id.video_title);
        tvSystem = (TextView) itemView.findViewById(R.id.video_system);
        tvType = (TextView) itemView.findViewById(R.id.video_type);
        tvDesc = (TextView) itemView.findViewById(R.id.video_shortdesc);
        btnDown = (Button) itemView.findViewById(R.id.video_download);
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null){
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
