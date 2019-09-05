package com.example.myapplication.presenter;

import com.example.myapplication.bean.NewsDetail;
import com.example.myapplication.interactor.NewsDetailInteracor;
import com.example.myapplication.view.BaseView;
import com.example.myapplication.view.NewsDocView;

/**
 * Created by MBENBEN on 2016/1/6.
 */
public class NewsDocPresenter extends BasePresenter<NewsDetail> {

    public NewsDocPresenter(BaseView view) {
        super(new NewsDetailInteracor(), view);
    }

    public void handleFont() {
        ((NewsDocView)mView).handleFont();
    }

    public void handleShare() {
        ((NewsDocView)mView).handleShare();
    }

    @Override
    protected void formatData(NewsDetail newsDetail) {
        if(newsDetail != null)
            mView.showSuccess(newsDetail);
    }
}
