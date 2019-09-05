package com.example.myapplication.view;

import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.TopicDetail;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public interface TopicDetailView extends BaseView<TopicDetail> {
    void showGameDetail(AppInfo detailItem);
    void hideloading();
}
