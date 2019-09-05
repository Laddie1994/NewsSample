package com.example.myapplication.bean;

/**
 * Created by MBENBEN on 2016/1/24.
 */
public class NewsShare {
    public String sharetitle;
    public String sharelink;
    public String shareimgUrl;
    public String sharedesc;

    @Override
    public String toString() {
        return "NewsShare{" +
                "sharetitle='" + sharetitle + '\'' +
                ", sharelink='" + sharelink + '\'' +
                ", shareimgUrl='" + shareimgUrl + '\'' +
                ", sharedesc='" + sharedesc + '\'' +
                '}';
    }
}
