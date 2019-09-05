package com.example.myapplication.view;

/**
 * Created by MBENBEN on 2016/1/6.
 */
public interface BaseVieew<T> {
    void showLoding();
    void hideLoading();
    void showError(String error);
    void showFinish(T detailData);
}
