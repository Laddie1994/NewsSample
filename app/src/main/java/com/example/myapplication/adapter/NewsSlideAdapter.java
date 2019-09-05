package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AppContext;
import com.example.myapplication.bean.NewsDetail;
import com.example.myapplication.manager.ImageLoaderManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by MBENBEN on 2016/1/19.
 */
public class NewsSlideAdapter extends PagerAdapter {

    private ArrayList<NewsDetail.Slides> mSlides = new ArrayList<>();
    private Context mContext;

    public NewsSlideAdapter(Context context){
        this.mContext = context;
    }

    public void addAll(ArrayList<NewsDetail.Slides> slides){
        if(slides != null){
            mSlides.addAll(slides);
        }
        notifyDataSetChanged();
    }

    public NewsDetail.Slides get(int position){
        if(position >=0 && position < getCount()){
            NewsDetail.Slides slides = mSlides.get(position);
            if(slides != null){
                return slides;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mSlides.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);
        NewsDetail.Slides slides = mSlides.get(position);
        ImageLoader.getInstance().displayImage(slides.image, photoView,
                ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions());
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
