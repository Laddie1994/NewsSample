package com.example.myapplication.view;

import com.example.myapplication.bean.TopicList;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public interface TopicListView extends BaseView<TopicList> {
    void showGameDetail(TopicList.TopicInfo topicInfo);
}
