package com.example.myapplication.interactor;

import com.example.myapplication.bean.NewsList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2015/12/27.
 */
public class NewsListInteractor extends BaseInteractor<Map<String, NewsList>> {

    @Override
    public Map<String, NewsList> parseData(String json) {
        Gson gson = new Gson();
        Map<String, NewsList> newsMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsList news = gson.fromJson(jsonArray.getString(i), NewsList.class);
                if (news.type.equals(NewsList.NEWS_CATAGORY_LIST)) {
                    newsMap.put(NewsList.NEWS_CATAGORY_LIST, news);
                } else if (news.type.equals(NewsList.NEWS_CATAGORY_FOCUS)) {
                    newsMap.put(NewsList.NEWS_CATAGORY_FOCUS, news);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!newsMap.isEmpty()) {
            NewsList newsList = newsMap.get(NewsList.NEWS_CATAGORY_LIST);
            if (newsList != null) {
                setIsMore(castIsMore(newsList.currentPage, newsList.totalPage));
            }
        }
        return newsMap;
    }

    public static boolean castIsMore(int cutPage, int totalPage) {
        return cutPage <= totalPage;
    }
}
