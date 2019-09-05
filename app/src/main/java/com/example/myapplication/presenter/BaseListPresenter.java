package com.example.myapplication.presenter;

import com.example.myapplication.interactor.BaseInteractor;
import com.example.myapplication.view.BaseView;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by MBENBEN on 2016/3/1.
 */
public abstract class BaseListPresenter<DataType> extends BasePresenter<DataType> {

    public BaseListPresenter(BaseInteractor interactor, BaseView view) {
        super(interactor, view);
    }

    public void requestData(int page, Object[] categorys) {
        String url = getUrl();
        url += formatParams(getParams(page, categorys));
        if (!mInteractor.isIsMore()) {
            mView.showNoMore();
        } else {
            requestData(url);
        }
    }

    public String formatParams(Map<String, String> paramsMap) {
        if (paramsMap == null) {
            return "";
        }
        StringBuilder params = new StringBuilder("?");
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (params.length() > 0) {
                params.append("&");
            }
            params.append(entry.getKey());
            params.append("=");
            params.append(entry.getValue());
        }
        return params.toString();
    }

    protected abstract Map<String, String> getParams(int page, Object[] categorys);

    public abstract String getUrl();
}
