package com.example.myapplication.fragment;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerFragmentAdapter;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.fragment.page.VideoListFragment;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class VideoViewPagerFragment extends BaseViewPagerFragment {
    private String[] mTitles;

    public static VideoViewPagerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        VideoViewPagerFragment fragment = new VideoViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addTab(ViewPagerFragmentAdapter mAdapter) {
        super.addTab(mAdapter);
        mTitles = UIUtils.getStringArray(R.array.video_tabs);
        mAdapter.addTab(mTitles[0], VideoListFragment.class, getBundle(AppList.VIDEO_CATAGORY_HOT, 2));
        mAdapter.addTab(mTitles[1], VideoListFragment.class, getBundle(AppList.VIDEO_CATAGORY_NEW, 1));
        mAdapter.addTab(mTitles[2], VideoListFragment.class, getBundle(AppList.VIDEO_CATAGORY_CLASSIC, 1));
    }

    private Bundle getBundle(int catagory, int order){
        Bundle bundle = new Bundle();
        bundle.putInt(VideoListFragment.EXTAR_CATAGORY, catagory);
        bundle.putInt(VideoListFragment.EXTAR_ORDER, order);
        return bundle;
    }
}
