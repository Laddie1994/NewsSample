package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.interactor.GameListInteractor;
import com.example.myapplication.view.GameListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2016/2/4.
 */
public class GameListPresenter extends BaseListPresenter<AppList> {

    public GameListPresenter(GameListView gameListView){
        super(new GameListInteractor(), gameListView);
    }

    public void onItemClick(View view, AppInfo appInfo) {
        if(appInfo != null){
            ((GameListView)mView).showGameDetail(appInfo);
        }
    }

    @Override
    protected Map<String, String> getParams(int page, Object... categorys) {
        Map<String,String> params = new HashMap<>();
        params.put("start", String.valueOf(page * AppContext.PAGE_SIZE));
        params.put("count", String.valueOf(AppContext.PAGE_SIZE));
        params.put("cat_id", String.valueOf(categorys[0]));
        return params;
    }

    @Override
    protected void formatData(AppList appList) {
        if(appList != null){
            mView.showSuccess(appList);
        }else{
            mView.showError();
        }
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.HULU_URL + "filter/ANDROID/3.6";
    }
}
