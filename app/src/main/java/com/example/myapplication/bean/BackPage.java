package com.example.myapplication.bean;

import com.example.myapplication.DownloadDetailActivity;
import com.example.myapplication.ImageDetailActivity;
import com.example.myapplication.AppDetailActivity;

/**
 * Created by MBENBEN on 2016/1/4.
 */
public enum BackPage {
    VIDEO("video_detail", AppDetailActivity.class),
    IMAGE_DETAIL("image_detail", ImageDetailActivity.class),
    DOWNLOAD_DETAIL("download_detail", DownloadDetailActivity.class);

    public String type;
    public Class<?> clazz;

    BackPage(String type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static BackPage getPageByType(String type){
        for (BackPage page : values()){
            if(page.type.equals(type)){
                return page;
            }
        }
        return null;
    }
}
