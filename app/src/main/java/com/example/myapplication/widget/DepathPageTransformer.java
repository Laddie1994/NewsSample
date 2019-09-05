package com.example.myapplication.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myapplication.utils.LogUtils;

/**
 * Created by MBENBEN on 2016/3/23.
 */
public class DepathPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        if(position < -1){
            LogUtils.e(page.toString(), position + "");
            page.setAlpha(0);
        }else if(position <= 0){
            LogUtils.e(page.toString(), position + "");
            page.setAlpha(1);
            page.setTranslationX(1);
            page.setScaleX(1);
            page.setScaleY(1);
        }else if(position <= 1){
            LogUtils.e(page.toString(), position + "");
            page.setAlpha(1 - position);
            page.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }else{
            LogUtils.e(page.toString(), position + "");
            page.setAlpha(0);
        }
    }
}
