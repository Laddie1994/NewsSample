package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.utils.LogUtils;

/**
 * Created by MBENBEN on 2016/1/19.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startY = 0;
        float endY = 0;
        ViewGroup.LayoutParams params = null;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ACTION_DOWN");
                startY = event.getY();
                params = getLayoutParams();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE");
                endY = event.getY();

                int dY = (int) (endY - startY);
                params.height = Math.abs(dY);
                setLayoutParams(params);
                LogUtils.e(dY + "");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                LogUtils.e("ACTION_UP");
                break;
        }
        return true;
    }
}
