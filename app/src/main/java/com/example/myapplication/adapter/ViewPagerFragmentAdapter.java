package com.example.myapplication.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myapplication.AppContext;
import com.example.myapplication.bean.ViewPagerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<ViewPagerInfo> fragments = new ArrayList<>();

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addTab(String title, Class<?> clazz, Bundle bundle) {
        ViewPagerInfo viewPagerInfo = new ViewPagerInfo(title, clazz, bundle);
        fragments.add(viewPagerInfo);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerInfo viewPagerInfo = fragments.get(position);
        return Fragment.instantiate(AppContext.getContext(), viewPagerInfo.clazz.getName(), viewPagerInfo.args);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).title;
    }
}
