package com.example.myapplication.fragment.page;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.widget.MultiStateView;
import com.example.myapplication.widget.RecyclerDecoration;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

/**
 * Created by MBENBEN on 2016/2/4.
 */
public class BaseListFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    protected MultiStateView mMultiStateView;
    protected SwipyRefreshLayout mSwipeRefresh;
    protected RecyclerView mListView;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMultiStateView = (MultiStateView) view.findViewById(R.id.app_msl);
        mSwipeRefresh = (SwipyRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mListView = (RecyclerView) view.findViewById(R.id.list_view);
        mLayoutManager = getLayoutManager();
        mListView.setLayoutManager(mLayoutManager);
        mListView.addItemDecoration(getRecylerDecoration());
//        PauseOnScrollListener onScrollListener = new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
//        mListView.setOnScrollListener(onScrollListener);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    protected RecyclerDecoration getRecylerDecoration() {
        return new RecyclerDecoration(16);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefresh.setColorSchemeResources(
                R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4);
        mSwipeRefresh.setOnRefreshListener(this);
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR)
                .findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            }
        });
    }

    protected void onRetry() {
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
    }
}
