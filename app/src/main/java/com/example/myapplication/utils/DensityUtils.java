package com.example.myapplication.utils;

import com.example.myapplication.AppContext;

/**
 * Created by MBENBEN on 2015/12/28.
 */
public class DensityUtils {

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = AppContext.getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */
    public static int px2dip(int px) {
        final float scale = AppContext.getScreenDensity();
        return (int) (px / scale + 0.5f);
    }
}
