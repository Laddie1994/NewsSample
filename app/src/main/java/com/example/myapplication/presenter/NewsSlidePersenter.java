package com.example.myapplication.presenter;

import com.example.myapplication.bean.NewsDetail;
import com.example.myapplication.interactor.NewsDetailInteracor;
import com.example.myapplication.presenter.BasePresenter;
import com.example.myapplication.view.NewsSlideView;

/**
 * Created by MBENBEN on 2016/1/19.
 */
public class NewsSlidePersenter extends BasePresenter<NewsDetail> {

    public NewsSlidePersenter(NewsSlideView view){
        super(new NewsDetailInteracor(), view);
    }

    @Override
    protected void formatData(NewsDetail newsDetail) {
        if(newsDetail != null){
            mView.showSuccess(newsDetail);
        }
    }
}
