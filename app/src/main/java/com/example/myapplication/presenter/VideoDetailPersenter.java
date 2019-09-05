package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.bean.AppDetail.Similar;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.interactor.VideoDetailInteractor;
import com.example.myapplication.view.AppDetailView;
import com.example.myapplication.view.BaseView;

/**
 * Created by MBENBEN on 2016/1/12.
 */
public class VideoDetailPersenter extends BasePresenter<AppDetail> {

    public VideoDetailPersenter(BaseView view) {
        super(new VideoDetailInteractor(), view);
    }

    public void heandleShare(AppInfo appInfo) {
        if (appInfo != null) {
            ((AppDetailView)mView).heandleShare(appInfo);
        }
    }
    public void heandleFavor() {
    }

    public void heandleDown(AppDetail appDetail) {
        AppInfo appInfo = appDetail.gameinfo;
        if (appInfo.clouddownlist.size() > 0 && appInfo.clouddownlist != null) {
            showDownDialog(appInfo);
        } else if (appInfo.localurl != null) {
            ((AppDetailView)mView).downGame(appDetail);
        }
    }

    public void showDownDialog(AppInfo appInfo) {
        if (appInfo != null && appInfo.clouddownlist != null) {
            if (appInfo.clouddownlist.size() == 1) {
                ((AppDetailView)mView).startSkip(appInfo.clouddownlist.get(0));
            } else {
                ((AppDetailView)mView).showDialog(appInfo);
            }
        }
    }

    public void showScrrenImage(View view, int position, String[] imageresource) {
        if (imageresource != null && position >= 0 && position < imageresource.length) {
            ((AppDetailView)mView).showImage(view, position, imageresource);
        }
    }

    public void showSimail(Similar similar) {
        if (similar != null) {
            ((AppDetailView)mView).showSimail(similar);
        }
    }

    @Override
    protected void formatData(AppDetail appDetail) {
        if (appDetail != null) {
            ((AppDetailView)mView).showSuccess(appDetail);
        }
    }
}
