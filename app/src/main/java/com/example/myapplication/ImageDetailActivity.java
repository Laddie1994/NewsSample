package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.myapplication.adapter.ImageDetailAdapter;
import com.example.myapplication.utils.SystemBarHelper;
import com.example.myapplication.widget.HorizontalScrollViewPager;

/**
 * Created by MBENBEN on 2016/1/14.
 */
public class ImageDetailActivity extends BaseActivity {

    public static final String CURRENT_POSITION = "current_position";
    public static final String STRING_URIS = "string_uris";

    private HorizontalScrollViewPager mViewPager;
    private int mPosition;
    private String[] mUris;
    private ImageDetailAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPosition = args.getInt(CURRENT_POSITION);
            mUris = args.getStringArray(STRING_URIS);
        }
    }

    @Override
    protected void initView() {
        initToolbar();
        mViewPager = (HorizontalScrollViewPager) findViewById(R.id.image_detail_vp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemBarHelper.setStatusbarColor(this, R.color.alpha_95_black);
        getToolbar().setBackgroundResource(R.color.alpha_95_black);
        setTitle(" ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SystemBarHelper.setStatusbarColor(this, R.color.colorPrimary);
        getToolbar().setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new ImageDetailAdapter(mUris, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPosition);
        setSwipeBackEnable(mPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSwipeBackEnable(position);
            }
        });
    }

    /**
     * 判断是否可以滑动返回
     * @param position
     */
    private void setSwipeBackEnable(int position){
        if(position > 0){
            setSwipeBackEnable(false);
        }else{
            setSwipeBackEnable(true);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
