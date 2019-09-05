package com.example.myapplication.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2016/3/24.
 */
public class TextSliderView {

    protected Context mContext;
    protected OnSliderClickListener mOnSliderClickListener;
    private Bundle mBundle;
    private String mUrl;
    private String mDescription;

    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    public TextSliderView(Context context) {
        mContext = context;
    }

    /**
     * the description of a slider image.
     *
     * @param description
     * @return
     */
    public TextSliderView description(String description) {
        mDescription = description;
        return this;
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    public TextSliderView image(String url) {
        mUrl = url;
        return this;
    }

    /**
     * lets users add a bundle of additional information
     *
     * @param bundle
     * @return
     */
    public TextSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    protected void bindEventAndShow(final View v, ImageView imageView) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null)
                    mOnSliderClickListener.onSliderClick(TextSliderView.this);
            }
        });
        if (imageView == null)
            return;
        if (mUrl != null)
            ImageLoader.getInstance().displayImage(mUrl, imageView);
    }

    public String getDescription() {
        return mDescription;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set a slider image click listener
     *
     * @param l
     * @return
     */
    public TextSliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public TextSliderView setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    public enum ScaleType {
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    public interface OnSliderClickListener {
        void onSliderClick(TextSliderView slider);
    }
}
