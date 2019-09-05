package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.interactor.VideoListInteractor;
import com.example.myapplication.view.VideoListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by MBENBEN on 2016/1/7.
 */
public class VideoListPersenter extends BaseListPresenter<AppList> {

    public VideoListPersenter(VideoListView view) {
        super(new VideoListInteractor(), view);
    }

    public void onItemClick(View view, AppInfo appInfo) {
        if (appInfo != null)
            ((VideoListView) mView).showVideoDetail(appInfo);
    }

    public void showDownDialog(AppInfo appInfo) {
        ArrayList<AppInfo.CloudDownload> clouddownlist = appInfo.clouddownlist;
        if (appInfo != null && clouddownlist != null) {
            if (clouddownlist.size() == 1) {
                ((VideoListView) mView).skipDownload(clouddownlist.get(0));
            } else {
                ((VideoListView) mView).showDownDialog(appInfo);
            }
        }
    }

    public void handleDownload(AppInfo.CloudDownload cloudDownload) {
        if (cloudDownload != null) {
            ((VideoListView) mView).skipDownload(cloudDownload);
        }
    }

    @Override
    protected Map<String, String> getParams(int page, Object[] categorys) {
        Map<String, String> params = new HashMap<>();
        params.put("start", String.valueOf(page * AppContext.PAGE_SIZE));
        params.put("count", String.valueOf(AppContext.PAGE_SIZE));
        params.put("cat_id", String.valueOf(categorys[0]));
        params.put("order_type", String.valueOf(categorys[1]));
        return params;
    }

    @Override
    protected void formatData(AppList appList) {
        if (appList != null) {
            mView.showSuccess(appList);
        }else{
            mView.showError();
        }
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.HULU_URL + "category/list/ANDROID/3.6";
    }
}
