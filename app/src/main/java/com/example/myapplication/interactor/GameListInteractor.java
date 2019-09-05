package com.example.myapplication.interactor;

import com.example.myapplication.bean.AppList;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2016/2/4.
 */
public class GameListInteractor extends BaseInteractor<AppList> {

    @Override
    public AppList parseData(String json) {
        Gson gson= new Gson();
        AppList appList = gson.fromJson(json, AppList.class);
        if(appList != null)
            setIsMore(appList.more == 0 ? false : true);
        return appList;
    }
}
