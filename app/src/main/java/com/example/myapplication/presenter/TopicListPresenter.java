package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.bean.TopicList;
import com.example.myapplication.interactor.TopicListInteractor;
import com.example.myapplication.view.TopicListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicListPresenter extends BaseListPresenter<TopicList> {

    public TopicListPresenter(TopicListView view) {
        super(new TopicListInteractor(), view);
    }

    public void onItemClick(View view, TopicList.TopicInfo topicInfo) {
        if (topicInfo != null) {
            ((TopicListView)mView).showGameDetail(topicInfo);
        }
    }

    @Override
    protected Map<String, String> getParams(int page, Object... categorys) {
        Map<String, String> params = new HashMap<>();
        params.put("start", String.valueOf(page * AppContext.PAGE_SIZE));
        params.put("count", String.valueOf(AppContext.PAGE_SIZE));
        params.put("cat_id", String.valueOf(categorys[0]));
        return params;
    }

    @Override
    protected void formatData(TopicList topicList) {
        if(topicList != null){
            mView.showSuccess(topicList);
        }else{
            mView.showError();
        }
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.HULU_URL + "topic/list/ANDROID/3.6";
    }
}
