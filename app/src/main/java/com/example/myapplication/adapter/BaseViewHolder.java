package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapplication.bean.AppInfo;

/**
 * Created by MBENBEN on 2015/12/26.
 */
public abstract class BaseViewHolder<T> extends AppInfo {
    protected View mContentView;
    protected T mData;
    protected final LayoutInflater mInflater;
    protected Context mContext;

    public BaseViewHolder(Context context){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.from(context);
        mContentView = initView();
        mContentView.setTag(this);
    }

    public View getContentView(){
        return mContentView;
    }

    public T getData(){
        return mData;
    }

    public void setData(T data){
        if(data != null){
            mData = data;
            initData();
        }
    }

    public abstract View initView();

    public abstract void initData();
}
