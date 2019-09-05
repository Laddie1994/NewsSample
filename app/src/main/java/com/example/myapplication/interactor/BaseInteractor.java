package com.example.myapplication.interactor;

import android.text.TextUtils;

import com.example.myapplication.manager.OkHttpClientManager;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.IOUtils;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.MD5Encoder;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by MBENBEN on 2016/2/29.
 */
public abstract class BaseInteractor<DataType> {

    public static final String TAG = BaseInteractor.class.getCanonicalName();

    public static final int CACHE_TIME = 1000 * 60 * 60;

    private boolean mIsMore = true;

    public boolean isIsMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public DataType requestData(String url) {
        LogUtils.e(TAG, "--------->requestData");
        LogUtils.e(url);
        String result = requestFormLocal(url);
        DataType data = null;
        if (TextUtils.isEmpty(result)) {
            result = requestFromNet(url);
            if (!TextUtils.isEmpty(result)) {
                data = parseData(result);
                if (data != null) {
                    saveToLocal(url, result);
                }
            }
        } else {
            data = parseData(result);
        }
        return data;
    }

    public String requestFormLocal(String url) {
        LogUtils.e(TAG + "--------->requestFormLocal");
        File cacheDir;
        BufferedReader br = null;
        try {
            cacheDir = new File(FileUtils.getCacheDir(), MD5Encoder.encode(url));
            br = new BufferedReader(new FileReader(cacheDir));
            String line = br.readLine();
            long oldTime = Long.parseLong(line);
            if (oldTime > System.currentTimeMillis()) {
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = br.readLine()) != null) {
                    sb.append(result);
                }
                return sb.toString();
            }else{
                LogUtils.e("delete" + cacheDir.delete());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(br);
        }
        return "";
    }

    private String requestFromNet(String url) {
        LogUtils.e(TAG + "--------->requestFromNet");
        try {
            Response response = OkHttpClientManager.get(url);
            if (response.code() == 200) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void saveToLocal(String url, String result) {
        LogUtils.e(TAG + "--------->saveToLocal");
        File cacheDir;
        BufferedWriter bw = null;
        try {
            cacheDir = new File(FileUtils.getCacheDir(), MD5Encoder.encode(url));
            bw = new BufferedWriter(new FileWriter(cacheDir));
            long cutTime = System.currentTimeMillis() + CACHE_TIME;
            bw.write(cutTime + "\r\n");
            bw.write(result);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bw);
        }
    }

    public abstract DataType parseData(String json);
}
