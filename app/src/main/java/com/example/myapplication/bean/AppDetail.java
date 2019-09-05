package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/2/8.
 */
public class AppDetail {

    public AppInfo gameinfo;
    public ArrayList<Similar> similarList;

    public class Similar{
        public int appid;
        public String applogo;
        public String apptitle;
    }
}
