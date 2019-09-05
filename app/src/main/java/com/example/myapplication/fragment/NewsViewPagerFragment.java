package com.example.myapplication.fragment;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerFragmentAdapter;
import com.example.myapplication.bean.NewsList;
import com.example.myapplication.fragment.page.NewsListFragment;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class NewsViewPagerFragment extends BaseViewPagerFragment {

    private String[] mTitles;

    public static NewsViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        NewsViewPagerFragment fragment = new NewsViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addTab(ViewPagerFragmentAdapter mAdapter) {
        super.addTab(mAdapter);
        mTitles = UIUtils.getStringArray(R.array.news_tabs);
        mAdapter.addTab(mTitles[0], NewsListFragment.class, getBundle(NewsList.NEWS_CATAGORY_HEANDER, NewsList.NEWS_CATAGORY_HEANDER_FOCUS));
        mAdapter.addTab(mTitles[1], NewsListFragment.class, getBundle(NewsList.NEWS_CATAGORY_HOUSING, NewsList.NEWS_CATAGORY_HOUSING_FOCUS));
        mAdapter.addTab(mTitles[2], NewsListFragment.class, getBundle(NewsList.NEWS_CATAGORY_MILLITARY, NewsList.NEWS_CATAGORY_MILLITARY_FOCUS));
        mAdapter.addTab(mTitles[3], NewsListFragment.class, getBundle(NewsList.NEWS_CATAGORY_SCIENCE, NewsList.NEWS_CATAGORY_SCIENCE_FOCUS));
        mAdapter.addTab(mTitles[4], NewsListFragment.class, getBundle(NewsList.NEWS_CATAGORY_CAR, NewsList.NEWS_CATAGORY_CAR_FOCUS));
    }

    private Bundle getBundle(String catagory, String focus){
        Bundle bundle = new Bundle();
        bundle.putString(NewsListFragment.NEWS_CATEGORY, catagory);
        bundle.putString(NewsListFragment.NEWS_FOCUS, focus);
        return bundle;
    }
}
