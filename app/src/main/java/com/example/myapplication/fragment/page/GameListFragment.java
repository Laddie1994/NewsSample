package com.example.myapplication.fragment.page;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.myapplication.Constans;
import com.example.myapplication.adapter.GameHolder;
import com.example.myapplication.adapter.GameListAdapter;
import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.AppList;
import com.example.myapplication.bean.DownloadInfo;
import com.example.myapplication.manager.DownloadManager;
import com.example.myapplication.manager.RecylcerAnimManager;
import com.example.myapplication.presenter.GameListPresenter;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.GameListView;
import com.example.myapplication.widget.MultiStateView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

/**
 * Created by MBENBEN on 2016/2/4.
 */
public class GameListFragment extends BaseListFragment implements GameListView, OnRecyclerViewItemClickListener, DownloadManager.DownloadObserver {

    public static final String GAME_CATEGORY = "game_catagory";

    private int mCatagory;
    private int mCurrentPage;
    private GameListPresenter mPresenter;
    private GameListAdapter mAdapter;

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mPresenter = new GameListPresenter(this);
            mCatagory = args.getInt(GAME_CATEGORY);
            requestData();
        } else {
            throw new RuntimeException("you have to pass a parameter");
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.requestData(mCurrentPage, new Integer[]{mCatagory});
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new GameListAdapter(this);
        mListView.setAdapter(RecylcerAnimManager.getScaleInAdapter(mAdapter));
    }

    @Override
    protected void onRetry() {
        mPresenter.requestData(mCurrentPage, new Integer[]{mCatagory});
    }

    @Override
    public void onResume() {
        super.onResume();
        startObserver();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopObserver();
    }

    @Override
    public void showGameDetail(AppInfo appInfo) {
        Bundle extras = new Bundle();
        extras.putLong(Constans.Detail.DETAIL_ID, appInfo.appid);
        extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.VIDEO_DETAIL);
        UIUtils.skipGameDetail(getContext(), extras);
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

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            mCurrentPage = 0;
            mPresenter.requestData(mCurrentPage,new Integer[]{mCatagory});
        } else {
            mCurrentPage++;
            mPresenter.requestData(mCurrentPage, new Integer[]{mCatagory});
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        AppInfo item = mAdapter.getItem(position);
        if(item != null)
            mPresenter.onItemClick(view, item);
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {
        int firstPosition = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
        int lastPosition = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        for (int i = firstPosition; i <= lastPosition; i ++){
            GameHolder holder = (GameHolder) mListView.getChildViewHolder(mListView.getChildAt(firstPosition));
            if(holder.downloadInfo.getId() == info.getId()){
                holder.refreshState(info);
            }
        }
    }
}