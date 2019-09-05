package com.example.myapplication.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.R;
import com.example.myapplication.TopicDetailActivity;
import com.example.myapplication.adapter.AppAdapter;
import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.adapter.TopicHolder;
import com.example.myapplication.bean.TopicList;
import com.example.myapplication.bean.TopicList.TopicInfo;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.manager.RecylcerAnimManager;
import com.example.myapplication.presenter.TopicListPresenter;
import com.example.myapplication.view.TopicListView;
import com.example.myapplication.widget.MultiStateView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicListFragment extends BaseListFragment implements TopicListView, OnRecyclerViewItemClickListener {

    private int mCategory;
    private int mCurrentPage;
    private TopicListPresenter mPresenter;
    private AppAdapter<TopicList.TopicInfo> mAdapter;

    @Override
    protected void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mCategory = args.getInt(GameListFragment.GAME_CATEGORY);
            mCurrentPage = 0;
            mPresenter = new TopicListPresenter(this);
            requestData();
        } else {
            throw new RuntimeException("you have to pass a parameter");
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.requestData(mCurrentPage, new Integer[]{mCategory});
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new AppAdapter<TopicInfo>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getInflater().inflate(R.layout.topic_list_item, null);
                return new TopicHolder(view, TopicListFragment.this);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TopicHolder topicHolder = (TopicHolder) holder;
                TopicInfo topicInfo = getItem(position);
                ImageLoader.getInstance().displayImage(topicInfo.topiclogo, topicHolder.icon,
                        ImageLoaderManager.getInstance(getContext()).getDisplayOptions());
                topicHolder.title.setText(topicInfo.topictitle);
            }
        };
        mListView.setAdapter(RecylcerAnimManager.getAlphaInAdapter(mAdapter));
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        mPresenter.requestData(mCurrentPage, new Integer[]{mCategory});
    }

    @Override
    public void showGameDetail(TopicInfo topicInfo) {
        Bundle extras = new Bundle();
        extras.putInt(Constans.Detail.DETAIL_ID, topicInfo.topicid);
        extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.VIDEO_DETAIL);
        Intent intent = new Intent(AppContext.getContext(), TopicDetailActivity.class);
        intent.putExtras(extras);
        getActivity().startActivity(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            mCurrentPage = 0;
            mPresenter.requestData(mCurrentPage,new Integer[]{mCategory});
        } else {
            mCurrentPage++;
            mPresenter.requestData(mCurrentPage, new Integer[]{mCategory});
        }
    }

    @Override
    public void showSuccess(TopicList topicList) {
        if (mCurrentPage < 1) {
            mAdapter.clearData();
        }
        mAdapter.addData(topicList.topiclist);
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
    public void onItemClick(View view, int position) {
        TopicInfo item = mAdapter.getItem(position);
        mPresenter.onItemClick(view, item);
    }
}
