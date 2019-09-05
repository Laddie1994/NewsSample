package com.example.myapplication.view;

/**
 * Created by MBENBEN on 2016/3/1.
 */
public interface BaseView<DataType> {
    void showError();
    void showSuccess(DataType newsMap);
    void showNoMore();
    void showLoading();
}
