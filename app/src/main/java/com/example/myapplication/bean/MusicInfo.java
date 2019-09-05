package com.example.myapplication.bean;

/**
 * Created by MBENBEN on 2016/3/18.
 */
public class MusicInfo {
    private String _id;
    private String title;
    private String duration;
    private String data;
    private String albumId;
    private String artist;

    public MusicInfo(String _id, String title, String duration, String data
            , String albumId, String artist) {
        this._id = _id;
        this.title = title;
        this.duration = duration;
        this.data = data;
        this.albumId = albumId;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
