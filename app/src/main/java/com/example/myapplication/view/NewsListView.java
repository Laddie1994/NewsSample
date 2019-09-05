package com.example.myapplication.view;

import com.example.myapplication.bean.NewsInfo;
import com.example.myapplication.bean.NewsList;

import java.util.Map;

/**
 * Created by MBENBEN on 2015/12/28.
 */
public interface NewsListView extends BaseView<Map<String, NewsList>> {
    void initNewsList(NewsList newsList);
    void initNewsFocus(NewsList newsFocus);
    void showDocDetail(NewsInfo newsEntity);
    void showSlideDetail(NewsInfo newsEntity);
    void hideloading();
}
