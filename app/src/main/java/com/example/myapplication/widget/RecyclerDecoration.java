package com.example.myapplication.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.adapter.NewsAdapter;

/**
 * Created by MBENBEN on 2016/3/15.
 */
public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public RecyclerDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildViewHolder(view).getItemViewType() == NewsAdapter.HEADER_TYPE) {
            return;
        }
        outRect.left = mSpace;
        outRect.right = mSpace;
    }
}
