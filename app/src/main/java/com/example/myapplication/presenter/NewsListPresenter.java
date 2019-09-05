package com.example.myapplication.presenter;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.myapplication.Constans;
import com.example.myapplication.bean.NewsInfo;
import com.example.myapplication.bean.NewsList;
import com.example.myapplication.interactor.NewsListInteractor;
import com.example.myapplication.view.NewsListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2015/12/27.
 */
public class NewsListPresenter extends BaseListPresenter<Map<String, NewsList>> {

    public NewsListPresenter(NewsListView newsListView) {
        super(new NewsListInteractor(), newsListView);
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.IFENG_NEWS_URLS;
    }

    public Map<String, String> getParams(int page, Object... categorys) {
        HashMap<String, String> params = new HashMap<>();
        String id = "";
        for (Object value : categorys) {
            if (id.length() > 0)
                id += ",";
            id += value;
        }
        params.put("id", id);
        params.put("page", String.valueOf(page));
        return params;
    }

    @Override
    protected void formatData(Map<String, NewsList> newsMap) {
        if (newsMap != null && !newsMap.isEmpty()) {
            mView.showSuccess(newsMap);
            NewsList newsList = newsMap.get(NewsList.NEWS_CATAGORY_LIST);
            if (newsList != null)
                ((NewsListView) mView).initNewsList(newsList);
            NewsList focusList = newsMap.get(NewsList.NEWS_CATAGORY_FOCUS);
            if (focusList != null)
                ((NewsListView) mView).initNewsFocus(focusList);
        } else {
            mView.showError();
        }
    }

    public void onItemClick(View view, NewsInfo newsEntity) {
        if (newsEntity != null) {
            if (newsEntity.type.equals(NewsInfo.TYPE_DOC)) {
                ((NewsListView)mView).showDocDetail(newsEntity);
            } else if (newsEntity.type.equals(NewsInfo.TYPE_SLIDE)) {
                ((NewsListView)mView).showSlideDetail(newsEntity);
            } else {
                Snackbar.make(view, "还未处理", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
