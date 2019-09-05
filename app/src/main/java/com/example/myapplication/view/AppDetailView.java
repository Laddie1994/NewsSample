package com.example.myapplication.view;

import android.view.View;

import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppInfo.CloudDownload;

/**
 * Created by MBENBEN on 2016/1/12.
 */
public interface AppDetailView extends BaseView<AppDetail> {
    void heandleShare(AppInfo appInfo);
    void showDialog(AppInfo appInfo);
    void heandleFavor();
    void downGame(AppDetail appInfo);
    void showImage(View view, int position, String[] imageresource);
    void showSimail(AppDetail.Similar similar);
    void startSkip(CloudDownload cloudDownload);
}
