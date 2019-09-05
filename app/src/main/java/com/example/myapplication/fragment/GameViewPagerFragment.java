package com.example.myapplication.fragment;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerFragmentAdapter;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.fragment.page.GameListFragment;
import com.example.myapplication.fragment.page.TopicListFragment;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class GameViewPagerFragment extends BaseViewPagerFragment {
    private String[] mTitles;

    public static GameViewPagerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GameViewPagerFragment fragment = new GameViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addTab(ViewPagerFragmentAdapter mAdapter) {
        super.addTab(mAdapter);
        mTitles = UIUtils.getStringArray(R.array.game_tabs);
        mAdapter.addTab(mTitles[0], GameListFragment.class, getBundle(AppList.GAME_CATEGORY_FALLOW));
        mAdapter.addTab(mTitles[1], GameListFragment.class, getBundle(AppList.GAME_CATEGORY_MOTION));
        mAdapter.addTab(mTitles[2], GameListFragment.class, getBundle(AppList.GAME_CATEGORY_ROLE));
        mAdapter.addTab(mTitles[3], TopicListFragment.class, getBundle(AppList.GAME_CATEGORY_TOPIC));
    }

    public Bundle getBundle(int catagory) {
        Bundle bundle = new Bundle();
        bundle.putInt(GameListFragment.GAME_CATEGORY, catagory);
        return bundle;
    }
}
