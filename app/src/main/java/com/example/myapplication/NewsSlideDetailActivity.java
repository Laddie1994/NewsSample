package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.adapter.NewsSlideAdapter;
import com.example.myapplication.bean.NewsDetail;
import com.example.myapplication.presenter.NewsSlidePersenter;
import com.example.myapplication.utils.SystemBarHelper;
import com.example.myapplication.view.NewsSlideView;
import com.example.myapplication.widget.MultiStateView;

/**
 * Created by MBENBEN on 2016/1/19.
 */
public class NewsSlideDetailActivity extends BaseActivity implements NewsSlideView {

    private ViewPager mViewPager;
    private String mDetailId;
    private NewsSlidePersenter mPersenter;
    private NewsSlideAdapter mAdapter;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private RelativeLayout mRlDesc;
    private TextView mTvIndicator;
    private MultiStateView mMultiStateView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_slide_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        mPersenter = new NewsSlidePersenter(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mDetailId = extras.getString(Constans.Detail.DETAIL_ID);
            mPersenter.requestData(mDetailId);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        mMultiStateView = (MultiStateView) findViewById(R.id.app_msl);
        mViewPager = (ViewPager) findViewById(R.id.news_slide_view_pager);
        mRlDesc = (RelativeLayout) findViewById(R.id.news_slide_desc_layout);
        mTvTitle = (TextView) findViewById(R.id.news_slide_title);
        mTvDesc = (TextView) findViewById(R.id.news_slide_desc);
        mTvIndicator = (TextView) findViewById(R.id.news_slide_indicater);
        initDescView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemBarHelper.setStatusbarColor(this, R.color.alpha_95_black);
        getToolbar().setBackgroundResource(R.color.alpha_95_black);
        setTitle(" ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SystemBarHelper.setStatusbarColor(this, R.color.colorPrimary);
        getToolbar().setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new NewsSlideAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                NewsDetail.Slides slides = mAdapter.get(position);
                if (slides != null) {
                    mTvDesc.setText(slides.description);
                    mTvIndicator.setText(String.format("%s/%s", position + 1, mAdapter.getCount()));
                    initDescView();
                }
            }
        });

        mRlDesc.setOnTouchListener(new View.OnTouchListener() {
            ViewGroup.LayoutParams params;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = (int) event.getRawY();
                        params = mTvDesc.getLayoutParams();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaY = (int) (event.getRawY() - startY);
                        startY = (int) event.getRawY();
                        int height = params.height - deltaY;
                        if (height < getDescMinHeight()) {
                            break;
                        }
                        params.height = height;
                        mTvDesc.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }

    private void initDescView() {
        ViewGroup.LayoutParams params = mTvDesc.getLayoutParams();
        params.height = getDescMinHeight();
        mTvDesc.setLayoutParams(params);
    }

    private int getDescMinHeight() {
        TextView textView = new TextView(this);
        textView.setMaxLines(3);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setLines(3);

        int measuredWidth = mTvDesc.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    @Override
    public void showError() {
        if(mMultiStateView != null){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void showSuccess(NewsDetail detailData) {
        mAdapter.addAll(detailData.body.slides);
        NewsDetail.Slides slides = mAdapter.get(0);
        if (slides != null) {
            mTvTitle.setText(slides.title);
            mTvDesc.setText(slides.description);
            mTvIndicator.setText(String.format("%s/%s", mViewPager.getCurrentItem() + 1, mAdapter.getCount()));
        }
        if(mMultiStateView != null){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public void showNoMore() {
    }

    @Override
    public void showLoading() {
        if(mMultiStateView != null){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }
    }
}
