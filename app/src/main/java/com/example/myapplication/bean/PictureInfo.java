package com.example.myapplication.bean;

/**
 * Created by MBENBEN on 2015/12/27.
 */
public class PictureInfo extends Entity {
    public String abs;
    public String album_di;
    public String album_obj_num;
    public String colum;
    public String date;
    public String desc;
    public String download_url;
    public String id;
    public String image_height;
    public String image_width;
    public String image_url;
    public String share_url;
    public String site_logo;
    public String site_name;
    public String site_url;
    public String[] tags;
    public int thumb_large_height;
    public int thumb_large_width;
    public String thumb_large_url;
    public int thumbnail_height;
    public int thumbnail_width;
    public String thumbnail_url;

    @Override
    public String toString() {
        return "PictureEntity{" +
                "thumbnail_width=" + thumbnail_width +
                ", thumbnail_height=" + thumbnail_height +
                ", id='" + id + '\'' +
                '}';
    }
}
