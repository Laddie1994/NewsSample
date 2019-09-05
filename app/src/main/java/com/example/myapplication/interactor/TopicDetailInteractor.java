package com.example.myapplication.interactor;

import com.example.myapplication.bean.TopicDetail;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicDetailInteractor extends BaseInteractor<TopicDetail> {

    @Override
    public TopicDetail parseData(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TopicDetail.class);
    }
}
