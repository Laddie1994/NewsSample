package com.example.myapplication.bean;

import android.os.Bundle;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class ViewPagerInfo {
    public final String title;
    public final Class<?> clazz;
    public final Bundle args;

    public ViewPagerInfo(String title, Class<?> clazz, Bundle args) {
        this.title = title;
        this.clazz = clazz;
        this.args = args;
    }
}
