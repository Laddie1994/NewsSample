package com.example.myapplication.interactor;

import com.example.myapplication.bean.PictureList;
import com.google.gson.Gson;

/**
 * Created by MBENBEN on 2015/12/27.
 */
public class ImageListInteractor extends BaseInteractor<PictureList> {

    @Override
    public PictureList parseData(String json){
        Gson gson = new Gson();
        PictureList pictureList = gson.fromJson(json, PictureList.class);
        if(pictureList != null){
            setIsMore(castIsMore(pictureList.start_index, pictureList.totalNum));
        }
        return pictureList;
    }

    private static boolean castIsMore(int cutPage, int totalPage){
        return cutPage < totalPage;
    }
}
