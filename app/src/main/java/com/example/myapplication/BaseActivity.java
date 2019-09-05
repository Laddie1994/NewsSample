package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.myapplication.manager.ActivityManager;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public abstract class BaseActivity extends ActionBarCastActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
            initData();
            initView();
            initEvent();
        } else {
            throw new IllegalArgumentException("You must return a right_in contentView layout resource Id");
        }
    }

    public LayoutInflater getInflater(){
        return getLayoutInflater();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    protected void initView(){
    }

    protected void initEvent() {
    }


    protected void initData(){
    }

    public abstract int getLayoutId();
}
