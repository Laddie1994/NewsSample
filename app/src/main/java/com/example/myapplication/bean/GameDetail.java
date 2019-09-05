package com.example.myapplication.bean;

/**
 * Created by MBENBEN on 2016/2/8.
 */
public class GameDetail {
    public GameInfo gameinfo;
    public SimilarList similarList;

    public class GameInfo{
        public String apptitle;
        public String applogo;
        public String categoryname;
        public String appsize;
        public String apptags;
        public GameDownUrl localurl;
        public String appversion;
        public String system;
        public String applanguage;
        public String username;
        public String appcrackdesc;
        public String appdesc;
    }

    public class SimilarList{
        public int appid;
        public String applogo;
        public String apptitle;
    }
}
