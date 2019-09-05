package com.example.myapplication.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AppContext;
import com.example.myapplication.manager.ImageLoaderManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by MBENBEN on 2016/1/14.
 */
public class ImageDetailAdapter extends PagerAdapter {

    private String[] uris;
    private PhotoView photoView;
    private FragmentActivity activity;

    public ImageDetailAdapter(String[] uris, FragmentActivity activity){
        this.uris = uris;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return uris.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        photoView = new PhotoView(AppContext.getContext());
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                activity.finish();
            }
        });
        DisplayImageOptions options = ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions();
        ImageLoader.getInstance().displayImage(uris[position], photoView, options);
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
