package com.example.myapplication.fragment;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerFragmentAdapter;
import com.example.myapplication.bean.PictureList;
import com.example.myapplication.fragment.page.PictureListFragment;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class PictureViewPagerFragment extends BaseViewPagerFragment {

    private String[] mTitles;

    public static PictureViewPagerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PictureViewPagerFragment fragment = new PictureViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addTab(ViewPagerFragmentAdapter mAdapter) {
        super.addTab(mAdapter);
        mTitles = UIUtils.getStringArray(R.array.image_tabs);
        mAdapter.addTab(mTitles[0], PictureListFragment.class, getBundle(PictureList.IMAGE_CATEGORY_BELLE));
        mAdapter.addTab(mTitles[1], PictureListFragment.class, getBundle(PictureList.IMAGE_CATEGORY_CAR));
        mAdapter.addTab(mTitles[2], PictureListFragment.class, getBundle(PictureList.IMAGE_CATEGORY_CARTOON));
        mAdapter.addTab(mTitles[3], PictureListFragment.class, getBundle(PictureList.IMAGE_CATEGORY_SHOOT));
        mAdapter.addTab(mTitles[4], PictureListFragment.class, getBundle(PictureList.IMAGE_CATEGORY_STAR));
    }

    private Bundle getBundle(String type){
        Bundle bundle = new Bundle();
        bundle.putString(PictureListFragment.IMAGE_CATEGORY, type);
        return bundle;
    }
}
