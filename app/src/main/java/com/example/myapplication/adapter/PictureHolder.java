package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by MBENBEN on 2016/3/15.
 */
public class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ImageView imageView;

    public PictureHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
    }

    @Override
    public void onClick(View v) {

    }
}
