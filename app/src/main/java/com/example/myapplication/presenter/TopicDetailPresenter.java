package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.TopicDetail;
import com.example.myapplication.interactor.TopicDetailInteractor;
import com.example.myapplication.view.TopicDetailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicDetailPresenter extends BaseListPresenter<TopicDetail> {

    public TopicDetailPresenter(TopicDetailView view) {
        super(new TopicDetailInteractor(), view);
    }

    public void onItemClick(View view, AppInfo detailItem) {
        if (detailItem != null) {
            ((TopicDetailView) mView).showGameDetail(detailItem);
        }
    }

    @Override
    protected Map<String, String> getParams(int page, Object... categorys) {
        Map<String, String> params = new HashMap<>();
        params.put("start", String.valueOf(page));
        params.put("count", String.valueOf(AppContext.PAGE_SIZE));
        params.put("topic_id", String.valueOf(categorys[0]));
        return params;
    }

    @Override
    protected void formatData(TopicDetail topicDetail) {
        if (topicDetail != null) {
            mView.showSuccess(topicDetail);
        }
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.HULU_URL + "topic/detail/ANDROID/3.6";
    }
}
