package com.example.myapplication.presenter;

import android.os.SystemClock;

import com.example.myapplication.interactor.BaseInteractor;
import com.example.myapplication.manager.ThreadManager;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.BaseView;

/**
 * Created by MBENBEN on 2016/3/1.
 */
public abstract class BasePresenter<DataType> {
    protected BaseInteractor<DataType> mInteractor;
    protected BaseView<DataType> mView;

    public BasePresenter(BaseInteractor interactor, BaseView view) {
        this.mInteractor = interactor;
        this.mView = view;
    }

    public void requestData(String url) {
        ThreadManager.creatLongPool().execute(new RequestTask(url));
    }

    public class RequestTask implements Runnable {

        private String url;

        public RequestTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            final DataType dataType = mInteractor.requestData(url);
            //避免NavigationView的卡顿
            SystemClock.sleep(1000);
            UIUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    formatData(dataType);
                }
            });
        }
    }

    protected abstract void formatData(DataType dataType);
}
