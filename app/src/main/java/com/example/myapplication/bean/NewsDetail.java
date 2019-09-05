package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/1/6.
 */
public class NewsDetail {
    public NewsBody body;
    public NewsMeta meta;

    public class NewsBody{
        public String author;
        public String editTime;
        public String editorcode;
        public String shareurl;
        public String source;
        public String text;
        public String thumbnail;
        public String title;
        public String videoPoster;
        public String videoSrc;
        public ArrayList<Relate> relateDocs;
        public ArrayList<Slides> slides;
        public ArrayList<VideoItem> videos;
        public String hasVideo;

        public boolean hasVideo(){
            return hasVideo.equals("Y") ? true : false;
        }
    }

    public class VideoItem {
        public String duration;
        public String thumbnail;
        public Video video;
    }

    public class Video{
        public Normal HD;
    }

    public class Normal {
        public String src;
    }

    public class Slides{
        public String description;
        public String image;
        public String title;
    }

    public class NewsMeta{
        public String type;
    }

    public class Relate{
        public String id;
        public String thumbnail;
        public Link link;
        public String title;
        public String type;
    }
}
