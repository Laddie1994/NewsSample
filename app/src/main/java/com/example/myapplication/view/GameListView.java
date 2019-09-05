package com.example.myapplication.view;

import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppList;

/**
 * Created by MBENBEN on 2016/2/8.
 */
public interface GameListView extends BaseView<AppList> {
    void showGameDetail(AppInfo appInfo);
    void hideloading();
}
