package com.example.myapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.widget.TextSliderView;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2015/12/29.
 */
public class NewsTopAdapter extends PagerAdapter {

    private ArrayList<TextSliderView> mData = new ArrayList<>();

    public void addFocus(ArrayList<TextSliderView> newsFocus) {
        if (newsFocus != null) {
            mData.addAll(newsFocus);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealCount(){
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % getRealCount();
        View view = mData.get(realPosition).getView();
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
