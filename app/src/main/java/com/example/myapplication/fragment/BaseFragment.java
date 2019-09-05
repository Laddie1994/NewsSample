package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.utils.LogUtils;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class BaseFragment extends Fragment {

    public static final String FRAGMENT_TITLE = "fragment_title";
    private static final String TAG = BaseFragment.class.getCanonicalName();
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
            initView(mRootView);
        } else {
            throw new IllegalArgumentException("需要传递一个布局文件");
        }
        return mRootView;
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected LayoutInflater getInflater() {
        return ((BaseActivity) getActivity()).getInflater();
    }

    protected void initData() {
        Bundle args = getArguments();
        if (args != null) {
            ((BaseActivity) getActivity()).setTitle(args.getString(FRAGMENT_TITLE));
        }
    }

    public void requestData(){
    }

    protected void initEvent() {
    }

    protected void initView(View view) {
        LogUtils.e(TAG, "---->initView");
    }

    public int getLayoutId() {
        return 0;
    }
}
