package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class PictureList {

    public static final String IMAGE_CATEGORY_BELLE = "美女";
    public static final String IMAGE_CATEGORY_CARTOON = "动漫";
    public static final String IMAGE_CATEGORY_STAR = "明星";
    public static final String IMAGE_CATEGORY_CAR = "汽车";
    public static final String IMAGE_CATEGORY_SHOOT = "摄影";

    public ArrayList<PictureInfo> data;
    public int return_number;
    public int start_index;
    public String tag1;
    public String tag2;
    public int totalNum;

    @Override
    public String toString() {
        return "PictureList{" +
                "data=" + data +
                ", return_number=" + return_number +
                ", start_index=" + start_index +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", totalNum=" + totalNum +
                '}';
    }
}
