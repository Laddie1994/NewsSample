package com.example.myapplication.interactor;

import com.example.myapplication.bean.AppList;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/1/8.
 */
public class VideoListInteractor extends BaseInteractor<AppList> {

    public AppList parseData(String json) {
        Gson gson = new Gson();
        AppList appList = gson.fromJson(json, AppList.class);
        if(appList != null)
            setIsMore (appList.more == 0 ? false : true);
        return appList;
    }
}
