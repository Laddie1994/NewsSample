package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.utils.FileUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class AppContext extends Application {

    public static final int PAGE_SIZE = 20;
    public static String TAG_LOG;

    private static Context mContext;
    private static int mScreenWidth;
    private static int mMainThreadId;
    private static int mScreenHeight;
    private static float mScreenDensity;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.density;
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        TAG_LOG = this.getClass().getSimpleName();
        initImageLoader(mContext);
    }

    private void initImageLoader(Context mContext){
        ImageLoader.getInstance()
                .init(ImageLoaderManager.getInstance(mContext).getImageLoaderConfiguration(FileUtils.getIconDir()));
    }

    /**
     * 返回主线程的pid
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getHandler(){
        return mHandler;
    }

    public static Context getContext(){
        return mContext;
    }

    public static int getScreenWidth(){
        return mScreenWidth;
    }

    public static int getScreenHeight(){
        return mScreenHeight;
    }

    public static float getScreenDensity(){
        return mScreenDensity;
    }
}
