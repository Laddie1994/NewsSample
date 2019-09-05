package com.example.myapplication.presenter;

import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.bean.PictureInfo;
import com.example.myapplication.bean.PictureList;
import com.example.myapplication.interactor.ImageListInteractor;
import com.example.myapplication.view.ImageListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by MBENBEN on 2015/12/27.
 */
public class ImageListPresenter extends BaseListPresenter<PictureList> {

    public ImageListPresenter(ImageListView imageListView) {
        super(new ImageListInteractor(), imageListView);
    }

    public void onItemClick(View view, List<PictureInfo> entitys, int position) {
        if (entitys != null && entitys.size() > 0)
            ((ImageListView)mView).showImage(view, entitys, position);
    }

    @Override
    public String getUrl() {
        return Constans.UrlConstants.BAIDU_IMAGES_URLS;
    }

    @Override
    protected Map<String, String> getParams(int page, Object[] categorys) {
        Map<String, String> params = new HashMap<>();
        params.put("pn", String.valueOf(page * AppContext.PAGE_SIZE));
        params.put("rn", String.valueOf(AppContext.PAGE_SIZE));
        params.put("tag1", String.valueOf(categorys[0]));
        params.put("tag2", "全部");
        return params;
    }

    @Override
    protected void formatData(PictureList pictureList) {
        if(pictureList != null){
            mView.showSuccess(pictureList);
        }else{
            mView.showError();
        }
    }
}
