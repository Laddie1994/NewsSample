package com.example.myapplication.view;

import android.view.View;

import com.example.myapplication.bean.PictureInfo;
import com.example.myapplication.bean.PictureList;

import java.util.List;

/**
 * Created by MBENBEN on 2015/12/27.
 */
public interface ImageListView extends BaseView<PictureList> {
    void showImage(View view, List<PictureInfo> entitys, int position);
    void hideloading();
}
