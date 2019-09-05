package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/3/13.
 */
public abstract class AppAdapter<DataType> extends RecyclerView.Adapter {

    private ArrayList<DataType> mData = new ArrayList();
    public static final int NORMAL_TYPE = 0x00;

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public DataType getItem(int position) {
        return mData.get(position);
    }

    public ArrayList getData() {
        return mData;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return NORMAL_TYPE;
    }

    public void addData(ArrayList<DataType> data) {
        if (data != null) {
            mData.addAll(data);
        }
    }
}
