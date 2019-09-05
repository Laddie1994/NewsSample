package com.example.myapplication.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerFragmentAdapter;
import com.github.library.SmartTabLayout.SmartTabLayout;

import static com.example.myapplication.R.id.viewpager;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class BaseViewPagerFragment extends BaseFragment {

    private ViewPager mViewPager;
    private ViewPagerFragmentAdapter mAdapter;
    private SmartTabLayout mPagerTab;

    protected void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(viewpager);
        mAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);
        addTab(mAdapter);
        mPagerTab = (SmartTabLayout) view.findViewById(R.id.viewPagerTab);
        mPagerTab.setViewPager(mViewPager);
        //设置预加载的页数
//        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_view_pager;
    }

    protected void addTab(ViewPagerFragmentAdapter mAdapter) {
    }
}
