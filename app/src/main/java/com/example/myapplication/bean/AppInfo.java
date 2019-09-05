package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/1/7.
 */
public class AppInfo {

    public String appcrackdesc;
    public String appdesc;
    public Long appid;
    public String applanguage;
    public String applogo;
    public float appsize;
    public String apptags;
    public String apptitle;
    public String appversion;
    public String categoryname;
    public String packname;
    public Long pageSize;
    public String shareurl;
    public String shortdesc;
    public String system;
    public String username;
    public ArrayList<CloudDownload> clouddownlist;
    public Localurl localurl;
    public String[] imageresource;

    public int getMovieType() {
        if ("高清".equals(appversion)) {
            return 0;
        } else if ("TC标清".equals(appversion)) {
            return 1;
        } else if ("枪版".equals(appversion)) {
            return 2;
        }
        return 0;
    }

    public class CloudDownload {
        public String name;
        public String url;
        public String urlType;
    }

    public class Localurl{
        public String name;
        public String url;
        public String urlType;
    }
}
