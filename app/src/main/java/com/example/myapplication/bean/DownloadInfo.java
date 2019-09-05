package com.example.myapplication.bean;

import com.example.myapplication.manager.DownloadManager;
import com.example.myapplication.utils.FileUtils;

public class DownloadInfo {
    private long id;// 唯一标示
    private String appName;// 软件的名字
    private Long appSize = 0l; // app的size
    private long currentSize = 0;
    private int downloadState = 0;//下载的状态
    private String url;//下载的地址
    private String path;// 保存的路径

    public static DownloadInfo clone(AppInfo info) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = info.appid;
        downloadInfo.appName = info.packname;
        downloadInfo.appSize = info.pageSize;
        downloadInfo.currentSize = 0;
        downloadInfo.downloadState = DownloadManager.STATE_NONE;
        downloadInfo.url = info.localurl.url;
        downloadInfo.path = FileUtils.getDownloadDir() + downloadInfo.appName + ".apk";
        return downloadInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
