package com.example.myapplication.bean;

import java.util.Arrays;

/**
 * Created by MBENBEN on 2015/12/28.
 */
public class NewsInfo extends Entity {

    public static final String TYPE_DOC = "doc";
    public static final String TYPE_SLIDE = "slide";

    public String comments;
    public String commentsUrl;
    public String commentsall;
    public String documentId;
    public String id;
    public Link link;
    public String online;
    public String source;
    public Style style;
    public String thumbnail;
    public String title;
    public String type;
    public boolean hasSlide;
    public String updateTime;

    public class Style{
        public String[] images;
        public String type;

        @Override
        public String toString() {
            return "Style{" +
                    "images=" + Arrays.toString(images) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "style=" + style +
                '}';
    }
}
