package com.example.myapplication.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.example.myapplication.AppContext;
import com.example.myapplication.ImageDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.adapter.PictureListAdapter;
import com.example.myapplication.bean.PictureInfo;
import com.example.myapplication.bean.PictureList;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.presenter.ImageListPresenter;
import com.example.myapplication.view.ImageListView;
import com.example.myapplication.widget.MultiStateView;
import com.github.library.PinterestLikeAdapterView.MultiColumnListView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * Created by MBENBEN on 2015/12/26.
 */
public class PictureListFragment extends BaseFragment implements ImageListView, SwipyRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {

    public static final String IMAGE_CATEGORY = "image_category";

    private SwipyRefreshLayout mSwipeRefresh;
    private MultiColumnListView mListView;
    private ImageListPresenter mPresenter;
    private String mCatagory;
    private int mCurrentPage;
    private PictureListAdapter mAdapter;
    private MultiStateView mMultiStateView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image_list;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new ImageListPresenter(this);
        Bundle args = getArguments();
        if (args != null) {
            mCatagory = args.getString(IMAGE_CATEGORY);
            requestData();
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.requestData(mCurrentPage, new String[]{mCatagory});
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMultiStateView = (MultiStateView) view.findViewById(R.id.app_msl);
        mSwipeRefresh = (SwipyRefreshLayout) view.findViewById(R.id.image_swipe_refresh);
        mListView = (MultiColumnListView) view.findViewById(R.id.image_list_view);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.requestData(mCurrentPage, new String[]{mCatagory});
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    }
                });

        mAdapter = new PictureListAdapter(getContext());
        mAdapter.setOnRecyclerViewItemClickListener(this);
        mListView.setAdapter(mAdapter);
        mSwipeRefresh.setColorSchemeResources(
                R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void showImage(View view, List<PictureInfo> entitys, int position) {
        String[] urls = new String[entitys.size()];
        for (int i = 0; i < entitys.size(); i ++){
            urls[i] = entitys.get(i).image_url;
        }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view
                , view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
        Bundle bundle = new Bundle();
        bundle.putInt(ImageDetailActivity.CURRENT_POSITION, position);
        bundle.putStringArray(ImageDetailActivity.STRING_URIS, urls);
        Intent intent = new Intent(AppContext.getContext(), ImageDetailActivity.class);
        intent.putExtras(bundle);
        getContext().startActivity(intent, options.toBundle());
    }

    @Override
    public void showLoading() {
        if (mSwipeRefresh != null && mCurrentPage == 0) {
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
    public void showSuccess(PictureList pictureList) {
        if (mCurrentPage == 0) {
            mAdapter.clear();
        } else if (mListView != null) {
            mListView.onLoadMoreComplete();
        }
        mAdapter.addData(pictureList.data);
        if(mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
        if(mMultiStateView.getViewState() == MultiStateView.VIEW_STATE_LOADING){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public void showNoMore() {
        Snackbar.make(mSwipeRefresh, "没有数据了", Snackbar.LENGTH_SHORT).show();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            mCurrentPage = 0;
            mPresenter.requestData(mCurrentPage,new String[]{mCatagory});
        } else {
            mCurrentPage++;
            mPresenter.requestData(mCurrentPage, new String[]{mCatagory});
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.onItemClick(view, mAdapter.getData(), position);
    }
}
