package com.example.myapplication.interactor;

import com.example.myapplication.bean.AppDetail;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/1/12.
 */
public class VideoDetailInteractor extends BaseInteractor<AppDetail>{

    public AppDetail parseData(String json){
        Gson gson = new Gson();
        AppDetail appDetail = gson.fromJson(json, AppDetail.class);
        return appDetail;
    }
}
