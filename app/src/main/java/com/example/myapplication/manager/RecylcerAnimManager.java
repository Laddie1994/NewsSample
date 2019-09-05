package com.example.myapplication.manager;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.animation.OvershootInterpolator;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

/**
 * Created by MBENBEN on 2016/3/19.
 */
public class RecylcerAnimManager {

    private static final int ANIM_DURATION = 500;
    private static final float INTER_TENSION = 2f;

    public static SlideInLeftAnimationAdapter getSlidInLeftAdapter(Adapter adapter){
        SlideInLeftAnimationAdapter leftAnimationAdapter = new SlideInLeftAnimationAdapter(adapter);
        leftAnimationAdapter.setDuration(ANIM_DURATION);
        leftAnimationAdapter.setInterpolator(new OvershootInterpolator(1));
        return leftAnimationAdapter;
    }

    public static SlideInRightAnimationAdapter getSlidInRightAdapter(Adapter adapter){
        SlideInRightAnimationAdapter rightAnimationAdapter = new SlideInRightAnimationAdapter(adapter);
        rightAnimationAdapter.setDuration(ANIM_DURATION);
        rightAnimationAdapter.setInterpolator(new OvershootInterpolator(INTER_TENSION));
        return rightAnimationAdapter;
    }

    public static SlideInBottomAnimationAdapter getSlidInBottomAdapter(Adapter adapter){
        SlideInBottomAnimationAdapter bottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);
        bottomAnimationAdapter.setDuration(ANIM_DURATION);
        bottomAnimationAdapter.setInterpolator(new OvershootInterpolator(INTER_TENSION));
        return bottomAnimationAdapter;
    }

    public static ScaleInAnimationAdapter getScaleInAdapter(Adapter adapter){
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setDuration(ANIM_DURATION);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator(INTER_TENSION));
        return scaleInAnimationAdapter;
    }

    public static AlphaInAnimationAdapter getAlphaInAdapter(Adapter adapter){
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(ANIM_DURATION);
        return alphaInAnimationAdapter;
    }
}
