package com.example.myapplication.view;

import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppList;

/**
 * Created by MBENBEN on 2016/1/7.
 */
public interface VideoListView extends BaseView<AppList> {
    void showVideoDetail(AppInfo appInfo);
    void hideloading();
    void showDownDialog(AppInfo appInfo);
    void skipDownload(AppInfo.CloudDownload cloudDownload);
}
