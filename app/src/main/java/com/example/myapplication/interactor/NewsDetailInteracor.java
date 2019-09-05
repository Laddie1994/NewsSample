package com.example.myapplication.interactor;

import com.example.myapplication.bean.NewsDetail;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/1/6.
 */
public class NewsDetailInteracor extends BaseInteractor<NewsDetail> {
    @Override
    public NewsDetail parseData(String json) {
        Gson gson = new Gson();
        NewsDetail newsDetail = gson.fromJson(json, NewsDetail.class);
        return newsDetail;
    }
}
