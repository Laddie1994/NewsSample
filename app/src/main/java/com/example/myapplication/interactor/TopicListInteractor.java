package com.example.myapplication.interactor;

import com.example.myapplication.bean.TopicList;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicListInteractor extends BaseInteractor<TopicList> {

    public TopicList parseData(String json) {
        Gson gson = new Gson();
        TopicList topicList = gson.fromJson(json, TopicList.class);
        if(topicList != null)
            setIsMore (topicList.more == 0 ? false : true);
        return topicList;
    }
}
