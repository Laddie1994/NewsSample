package com.example.myapplication.bean;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicList {
    public int more;
    public int start;
    public ArrayList<TopicInfo> topiclist;

    public class TopicInfo{
        public String topicdesc;
        public int topicid;
        public String topiclogo;
        public String topictitle;
    }

}
