package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class NewsList {

    public static final String NEWS_CATAGORY_HEANDER = "SYLB10";
    public static final String NEWS_CATAGORY_MILLITARY = "JS83";
    public static final String NEWS_CATAGORY_HOUSING = "FC81";
    public static final String NEWS_CATAGORY_SCIENCE = "KJ123";
    public static final String NEWS_CATAGORY_CAR = "QC45";

    public static final String NEWS_CATAGORY_MILLITARY_FOCUS = "FOCUSJS83";
    public static final String NEWS_CATAGORY_HEANDER_FOCUS = "SYDT10";
    public static final String NEWS_CATAGORY_HOUSING_FOCUS = "FOCUSFC81";
    public static final String NEWS_CATAGORY_SCIENCE_FOCUS = "FOCUSKJ123";
    public static final String NEWS_CATAGORY_CAR_FOCUS = "FOCUSQC45";

    public static final String NEWS_CATAGORY_FOCUS = "focus";
    public static final String NEWS_CATAGORY_LIST = "list";

    public int currentPage;
    public int totalPage;
    public String type;
    public ArrayList<NewsInfo> item;
}
