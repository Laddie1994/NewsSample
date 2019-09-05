package com.example.myapplication.manager;

import android.content.Intent;
import android.net.Uri;

import com.example.myapplication.bean.DownloadInfo;
import com.example.myapplication.utils.IOUtils;
import com.example.myapplication.utils.UIUtils;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理下载
 * Created by MBENBEN on 2016/2/24.
 */
public class DownloadManager {
    /**
     * 默认
     */
    public static final int STATE_NONE = 0;
    /**
     * 等待
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSE = 3;
    /**
     * 错误
     */
    public static final int STATE_ERROR = 4;
    /**
     * 下载完成
     */
    public static final int STATE_DOWNLOED = 5;

    private static DownloadManager instance;

    private DownloadManager() {
    }

    /**
     * 用于记录下载信息,如果是正式项目,需要持久化保存
     */
    private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<>();
    /**
     * 用于记录所有下载的任务,方便取消下载时,能通过id找到该任务进行删除
     */
    private Map<Long, DownloadTask> mTaskMap = new ConcurrentHashMap<>();

    /**
     * 用于记录所有注册了被观察者,方便通知下载时,能通过id找到该任务进行通知
     */
    private List<DownloadObserver> mObservers = new ArrayList<>();

    /**
     * 注册观察者
     */
    public void registerObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (!mObservers.contains(observer)) {
                mObservers.add(observer);
            }
        }
    }

    /**
     * 反注册观察者
     */
    public void unRegisterObserver(DownloadObserver observer) {
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                mObservers.remove(observer);
            }
        }
    }

    /**
     * 当下载状态发送改变的时候回调
     */
    public void notifyDownloadStateChanged(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadStateChanged(info);
            }
        }
    }

    /**
     * 当下载进度发送改变的时候回调
     */
    public void notifyDownloadProgressed(DownloadInfo info) {
        synchronized (mObservers) {
            for (DownloadObserver observer : mObservers) {
                observer.onDownloadProgressed(info);
            }
        }
    }

    // 单例
    public static synchronized DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }

    public synchronized void download(DownloadInfo downloadInfo) {
        DownloadInfo info = mDownloadMap.get(downloadInfo.getId());
        if (info == null) {
            mDownloadMap.put(downloadInfo.getId(), downloadInfo);// 保存到集合中
        }
        if (downloadInfo.getDownloadState() == STATE_NONE
                || info.getDownloadState() == STATE_PAUSE
                || info.getDownloadState() == STATE_ERROR) {
            // 下载之前，把状态设置为STATE_WAITING，
            // 因为此时并没有产开始下载，只是把任务放入了线程池中，
            // 当任务真正开始执行时，才会改为STATE_DOWNLOADING
            downloadInfo.setDownloadState(STATE_WAITING);
            // 通知更新界面
            notifyDownloadStateChanged(downloadInfo);
            DownloadTask task = mTaskMap.get(downloadInfo.getId());
            if(task == null){
                task = new DownloadTask(downloadInfo);
            }
            mTaskMap.put(downloadInfo.getId(), task);
            ThreadManager.creatDownLoadPool().execute(task);
        }
    }

    /**
     * 安装应用
     */
    public synchronized void install(DownloadInfo downloadInfo) {
        stopDownload(downloadInfo);
        DownloadInfo info = mDownloadMap.get(downloadInfo.getId());// 找出下载信息
        if (info != null) {// 发送安装的意图
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(installIntent);
        }
        notifyDownloadStateChanged(info);
    }

    /**
     * 暂停下载
     */
    public synchronized void pause(DownloadInfo downloadInfo) {
        stopDownload(downloadInfo);
        DownloadInfo info = mDownloadMap.get(downloadInfo.getId());
        if (info != null) {// 修改下载状态
            info.setDownloadState(STATE_PAUSE);
            notifyDownloadStateChanged(info);
        }
    }

    /**
     * 取消下载，逻辑和暂停类似，只是需要删除已下载的文件
     */
    public synchronized void cancel(DownloadInfo downloadInfo) {
        stopDownload(downloadInfo);
        DownloadInfo info = mDownloadMap.get(downloadInfo.getId());// 找出下载信息
        if (info != null) {// 修改下载状态并删除文件
            info.setDownloadState(STATE_NONE);
            notifyDownloadStateChanged(info);
            info.setCurrentSize(0);
            File file = new File(info.getPath());
            file.delete();
        }
    }

    private void stopDownload(DownloadInfo downloadInfo) {
        DownloadTask task = mTaskMap.remove(downloadInfo.getId());
        if (task != null) {
            ThreadManager.creatDownLoadPool().cancel(task);
        }
    }

    public class DownloadTask implements Runnable {

        private DownloadInfo info;
        private File file;

        public DownloadTask(DownloadInfo info) {
            this.info = info;
            file = new File(info.getPath());
        }

        @Override
        public void run() {
            info.setDownloadState(STATE_DOWNLOADING);
            notifyDownloadStateChanged(info);

            InputStream is = null;
            FileOutputStream fos = null;
            Response response = null;
            if (info.getCurrentSize() == 0 || !file.exists()
                    || file.length() != info.getCurrentSize()) {
                info.setCurrentSize(0);
                file.delete();
                try {
                    response = OkHttpClientManager.get(info.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 不需要重新下载
                // 文件存在且长度和进度相等，采用断点下载
                try {
                    response = OkHttpClientManager.get(info.getUrl() + "&range=" + info.getCurrentSize());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (response == null || (response.body().byteStream() == null)) {
                    info.setDownloadState(STATE_ERROR);
                    notifyDownloadStateChanged(info);
                } else {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file, true);
                    int count = 0;
                    byte[] buffer = new byte[1024];
                    while ((count = is.read(buffer)) != -1 && info.getDownloadState() == STATE_DOWNLOADING) {
                        fos.write(buffer, 0, count);
                        fos.flush();
                        info.setCurrentSize(info.getCurrentSize() + count);
                        notifyDownloadProgressed(info);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                info.setDownloadState(STATE_ERROR);
                notifyDownloadStateChanged(info);
                info.setCurrentSize(0);
                file.delete();
            } finally {
                IOUtils.close(fos);
            }
            // 判断进度是否和App相等
            if (info.getCurrentSize() == info.getAppSize()) {
                info.setDownloadState(STATE_DOWNLOED);
                notifyDownloadStateChanged(info);
            } else if (info.getDownloadState() == STATE_PAUSE) {
                notifyDownloadStateChanged(info);
            } else if (info.getDownloadState() == STATE_NONE){
                notifyDownloadStateChanged(info);
            } else {
                info.setDownloadState(STATE_ERROR);
                notifyDownloadStateChanged(info);
                info.setCurrentSize(0);
                file.delete();
            }
            mTaskMap.remove(info.getId());
        }
    }

    public interface DownloadObserver {

        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgressed(DownloadInfo info);
    }

    public DownloadInfo getDownloadInfo(long id) {
        // TODO Auto-generated method stub
        return mDownloadMap.get(id);
    }
}
