package com.example.myapplication.fragment.page;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myapplication.Constans;
import com.example.myapplication.DownloadDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.adapter.VideoAdapter;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppInfo.CloudDownload;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.manager.RecylcerAnimManager;
import com.example.myapplication.presenter.VideoListPersenter;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.VideoListView;
import com.example.myapplication.widget.MultiStateView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/1/7.
 */
public class VideoListFragment extends BaseListFragment implements SwipyRefreshLayout.OnRefreshListener, VideoListView, OnRecyclerViewItemClickListener, VideoAdapter.OnVideoClickLstener {

    public static final String EXTAR_CATAGORY = "extar_catagory";
    public static final String EXTAR_ORDER = "extar_order";

    private VideoAdapter mAdapter;
    private int mCurrentPage = 0;
    private int mCategory;
    private int mOrder;
    private VideoListPersenter mPersenter;

    @Override
    protected void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mCategory = args.getInt(EXTAR_CATAGORY);
            mOrder = args.getInt(EXTAR_ORDER);
            mPersenter = new VideoListPersenter(this);
            requestData();
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        mPersenter.requestData(mCurrentPage, new Integer[]{mCategory, mOrder});
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPage = 1;
                mPersenter.requestData(mCurrentPage, new Integer[]{mCategory, mOrder});
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            }
        });

        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter = new VideoAdapter();
        mAdapter.setItemClickListener(this);
        mAdapter.setmVideoClickLstener(this);
        mListView.setAdapter(RecylcerAnimManager.getScaleInAdapter(mAdapter));
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        mPersenter.requestData(mCurrentPage, new Integer[]{mCategory, mOrder});
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            mCurrentPage = 0;
            mPersenter.requestData(mCurrentPage, new Integer[]{mCategory, mOrder});
        } else {
            mCurrentPage++;
            mPersenter.requestData(mCurrentPage, new Integer[]{mCategory, mOrder});
        }
    }

    @Override
    public void showVideoDetail(AppInfo appInfo) {
        Bundle extras = new Bundle();
        extras.putLong(Constans.Detail.DETAIL_ID, appInfo.appid);
        UIUtils.skipAppDetail(getContext(), extras);
    }

    @Override
    public void showLoading() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(true);
            mSwipeRefresh.setEnabled(false);
        }
    }

    @Override
    public void hideloading() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
            mSwipeRefresh.setEnabled(true);
        }
    }

    @Override
    public void showError() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void showSuccess(AppList appList) {
        if (mCurrentPage < 1) {
            mAdapter.clearData();
        }
        mAdapter.addData(appList.gameapps);
        if(mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
        if(mMultiStateView.getViewState() == MultiStateView.VIEW_STATE_LOADING){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public void showNoMore() {
        Snackbar.make(mListView, "没有数据了", Snackbar.LENGTH_SHORT).show();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showDownDialog(AppInfo appInfo) {
        String[] downStr = new String[appInfo.clouddownlist.size()];
        final ArrayList<AppInfo.CloudDownload> clouddownlist = appInfo.clouddownlist;
        for (int i = 0; i < clouddownlist.size(); i++) {
            downStr[i] = clouddownlist.get(i).name;
        }
        new MaterialDialog.Builder(getContext())
                .items(downStr)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        mPersenter.handleDownload(clouddownlist.get(which));
                    }
                })
                .show();
    }

    @Override
    public void skipDownload(CloudDownload cloudDownload) {
        String url = cloudDownload.url;
        if (!TextUtils.isEmpty(url)) {
            Bundle extras = new Bundle();
            extras.putString(DownloadDetailActivity.DETAIL_URI, url);
            extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.DOWNLOAD_DETAIL);
            UIUtils.skipDownDetail(getContext(), extras);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        AppInfo appInfo = mAdapter.getItem(position);
        if(appInfo != null){
            mPersenter.onItemClick(view, appInfo);
        }
    }

    @Override
    public void onDownloadClick(AppInfo appInfo) {
        mPersenter.showDownDialog(appInfo);
    }
}
