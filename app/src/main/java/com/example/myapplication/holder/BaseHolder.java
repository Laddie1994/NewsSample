package com.example.myapplication.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by MBENBEN on 2016/2/24.
 */
public abstract class BaseHolder<T> {

    protected View mContentView;
    protected T mData;
    protected final LayoutInflater mInflater;
    protected Context mContext;

    public BaseHolder(Context context){
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
