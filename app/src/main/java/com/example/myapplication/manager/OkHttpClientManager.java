package com.example.myapplication.manager;

import com.example.myapplication.utils.UIUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * Created by MBENBEN on 2016/2/28.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步GET请求
     *
     * @param url
     * @return 返回Response类型
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        return getInstance()._getAsyn(url);
    }

    /**
     * 异步GET请求
     *
     * @param url
     * @return 返回String类型
     * @throws IOException
     */
    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    /**
     * 同步GET请求
     *
     * @param url
     * @param callback
     */
    public static void get(String url, StringCallback callback) {
        getInstance()._getAsyn(url, callback);
    }

    /**
     * 同步POST请求
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     * @throws IOException
     */
    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        return getInstance()._post(url, files, fileKeys, params);
    }

    /**
     * 同步POST请求
     *
     * @param url
     * @param file
     * @param fileKey
     * @return
     * @throws IOException
     */
    public static Response post(String url, File file, String fileKey) throws IOException {
        return getInstance()._post(url, file, fileKey);
    }

    /**
     * 同步POST请求
     *
     * @param url
     * @param file
     * @param fileKey
     * @param params
     * @return
     * @throws IOException
     */
    public static Response post(String url, File file, String fileKey, Param... params) throws IOException {
        return getInstance()._post(url, file, fileKey, params);
    }

    /**
     * 同步POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static Response post(String url, Param... params) throws IOException {
        return getInstance()._post(url, params);
    }

    /**
     * 异步POST请求
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @param params
     * @throws IOException
     */
    public static void postAsyn(String url, StringCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }

    /**
     * 异步POST请求
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    public static void postAsyn(String url, StringCallback callback, File file, String fileKey) throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }

    /**
     * 异步POST请求
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    public static void postAsyn(String url, StringCallback callback, File file, String fileKey, Param... params) throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    /**
     * 异步POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String postAsString(String url, Param... params) throws IOException {
        return getInstance()._postAsString(url, params);
    }

    /**
     * 异步POST请求
     *
     * @param url
     * @param callback
     * @param params
     */
    public static void postAsyn(String url, final StringCallback callback, Param... params) {
        getInstance()._postAsyn(url, callback, params);
    }

    /**
     * 异步POST请求
     * @param url
     * @param callback
     * @param params
     */
    public static void postAsyn(String url, final StringCallback callback, Map<String, String> params) {
        getInstance()._postAsyn(url, callback, params);
    }

    /**
     * 异步download下载
     * @param url
     * @param callback
     */
    public static void downloadAsyn(String url, Callback callback) {
        getInstance()._downloadAsyn(url, callback);
    }

    /**
     * 同步download下载
     * @param url
     * @return
     * @throws IOException
     */
    public static Response download(String url) throws IOException {
        return getInstance()._download(url);
    }

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    private Response _getAsyn(String url, Object tag) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    //*************对外公布的方法************

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final StringCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        handleResult(callback, request);
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final StringCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        handleResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final StringCallback callback, Map<String, String> params) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        handleResult(callback, request);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormatRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 同步基于post的文件上传
     *
     * @param url
     * @param file
     * @param fileKey
     * @return
     * @throws IOException
     */
    private Response _post(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormatRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 同步基于post的文件上传
     *
     * @param url
     * @param file
     * @param fileKey
     * @param params
     * @return
     * @throws IOException
     */
    private Response _post(String url, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormatRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, StringCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormatRequest(url, files, fileKeys, params);
        handleResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, StringCallback callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormatRequest(url, new File[]{file}, new String[]{fileKey}, null);
        handleResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, StringCallback callback, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormatRequest(url, new File[]{file}, new String[]{fileKey}, params);
        handleResult(callback, request);
    }

    /**
     * 异步下载文件
     * @param url
     * @param callback
     */
    private void _downloadAsyn(final String url, final Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    private Response _download(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return getOkHttpClient().newCall(request).execute();
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private Request buildMultipartFormatRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        params = checkParam(params);

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 校验参数
     * @param params
     * @return
     */
    private Param[] checkParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else
            return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private void handleResult(final StringCallback callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    sendSuccessStringCallback(string, callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final IOException e, final StringCallback callback) {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onFailure(request, e);
            }
        });
    }

    private void sendSuccessStringCallback(final String string, final StringCallback callback) {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onResponse(string);
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    public interface StringCallback {
        void onFailure(Request request, IOException e);

        void onResponse(String response);
    }

    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
