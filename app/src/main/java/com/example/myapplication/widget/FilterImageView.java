package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Created by MBENBEN on 2016/1/14.
 */
public class FilterImageView extends ImageView implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    public FilterImageView(Context context) {
        this(context, null);
    }

    public FilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_CANCEL:
                removeFilter();
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    private void setFilter() {
        Drawable drawable = getDrawable();
        if(drawable == null){
            drawable = getBackground();
        }
        if(drawable != null){
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
        invalidate();
    }

    private void removeFilter() {
        Drawable drawable = getDrawable();
        if(drawable == null){
            drawable = getBackground();
        }
        if(drawable != null){
            drawable.clearColorFilter();
        }
        invalidate();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        setFilter();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        removeFilter();
        performClick();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        performClick();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
