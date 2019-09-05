package com.example.myapplication.bean;

import com.example.myapplication.fragment.BaseFragment;

/**
 * Created by MBENBEN on 2015/12/25.
 */
public class NaviItem {
    private int icon;
    private int name;

    public NaviItem(int icon, int name, BaseFragment fragment) {
        this.icon = icon;
        this.name = name;
        this.fragment = fragment;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    private BaseFragment fragment;

    public NaviItem(int icon, int name){
        this.icon = icon;
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
