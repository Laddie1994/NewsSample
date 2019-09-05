package com.example.myapplication.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.AppContext;
import com.example.myapplication.Constans;
import com.example.myapplication.NewsDocDetailActivity;
import com.example.myapplication.NewsSlideDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.adapter.NewsTopAdapter;
import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.bean.NewsInfo;
import com.example.myapplication.bean.NewsList;
import com.example.myapplication.manager.RecylcerAnimManager;
import com.example.myapplication.pageTranformer.FixedSpeedScroller;
import com.example.myapplication.pageTranformer.ForegroundToBackgroundTransformer;
import com.example.myapplication.presenter.NewsListPresenter;
import com.example.myapplication.view.NewsListView;
import com.example.myapplication.widget.MultiStateView;
import com.example.myapplication.widget.TextSliderView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MBENBEN on 2015/12/24.
 */
public class NewsListFragment extends BaseListFragment implements SwipyRefreshLayout.OnRefreshListener, NewsListView, OnRecyclerViewItemClickListener {

    public static final String NEWS_CATEGORY = "news_category";
    public static final String NEWS_FOCUS = "news_focus";
    private static final long ROTATION_DELAY = 5000;
    private NewsListPresenter mPresenter;
    private String mCatagory;
    private String mFocus;
    private int mCurrentPage;
    private ViewPager mViewPager;
    private TextView mTvIndex;
    private RelativeLayout mHeader;
    private NewsAdapter mAdapter;
    private NewsTopAdapter mTopAdapter;
    private long mDelay;
    private long mDuration;
    private boolean mAutoRecover = true;
    private Timer mCycleTimer;
    private TimerTask mCycleTask;
    private Timer mResuminTimer;
    private TimerTask mResuminTask;
    private boolean mAutoCycle;
    private boolean mCycling;
    private Handler mRotationHndler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            moveNextPosition();
        }
    };

    private void moveNextPosition() {
        moveNextPosition(true);
    }

    private void moveNextPosition(boolean smoothScroll) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, smoothScroll);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mCatagory = args.getString(NEWS_CATEGORY);
            mFocus = args.getString(NEWS_FOCUS);
            mCurrentPage = 1;
            mPresenter = new NewsListPresenter(this);
            requestData();
        } else {
            throw new RuntimeException("you have to pass a parameter");
        }
    }

    @Override
    public void requestData() {
        mPresenter.requestData(mCurrentPage, new String[]{mCatagory, mFocus});
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mHeader = (RelativeLayout) getInflater().inflate(R.layout.news_focus, null);
        mViewPager = (ViewPager) mHeader.findViewById(R.id.news_view_pager);
        mTvIndex = (TextView) mHeader.findViewById(R.id.news_focus_indicator);
        initViewPager();
    }

    private void initViewPager() {
        //设置viewpager切换效果
        mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
        try {
            //设置viewpager滑动时间
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            field.set(mViewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRetry() {
        mPresenter.requestData(mCurrentPage, new String[]{mCatagory, mFocus});
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new NewsAdapter();
        mAdapter.setHeaderView(mHeader);
        mAdapter.setItemClickListener(this);
        mTopAdapter = new NewsTopAdapter();
        mListView.setAdapter(RecylcerAnimManager.getScaleInAdapter(mAdapter));
        mViewPager.setAdapter(mTopAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
       /* mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        pauseAutoCycle();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        startAutoCycle();
                        break;
                }
                return false;
            }
        });*/
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTvIndex.setText(getRealPosition(position));
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        pauseAutoCycle();
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            mCurrentPage = 1;
            mPresenter.requestData(mCurrentPage, new String[]{mCatagory, mFocus});
        } else {
            mCurrentPage++;
            mPresenter.requestData(mCurrentPage, new String[]{mCatagory});
        }
    }

    public void initNewsFocus(final NewsList newsFocus) {
        if (mCurrentPage <= 1) {
            mTopAdapter.clearData();
        }
        if (newsFocus.item.size() == 0) {
            mTvIndex.setVisibility(View.GONE);
        } else {
            mTvIndex.setVisibility(View.VISIBLE);
        }
        ArrayList<TextSliderView> imageViews = new ArrayList<>();
        for (final NewsInfo info : newsFocus.item) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(info.thumbnail);
            textSliderView.setScaleType(TextSliderView.ScaleType.Fit);
            textSliderView.description(info.title);
            textSliderView.setOnSliderClickListener(new TextSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(TextSliderView slider) {
                    mPresenter.onItemClick(slider.getView(), info);
                }
            });
            imageViews.add(textSliderView);
        }
        mTopAdapter.addFocus(imageViews);
        //默认设置为第一个
        int i = Integer.MAX_VALUE / 2;
        int i1 = mTopAdapter.getRealCount() % Integer.MAX_VALUE;
        mViewPager.setCurrentItem(i - i1);

//        try {
//            Field firstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
//            firstLayout.setAccessible(true);
//            firstLayout.set(mViewPager, true);
//            mTopAdapter.notifyDataSetChanged();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mTvIndex.setText(getRealPosition(mViewPager.getCurrentItem()));
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if (mTopAdapter.getRealCount() > 1) {
//            startAutoCycle();
        }
    }

    private String getRealPosition(int i) {
        return String.format("%s/%s", i % mTopAdapter.getRealCount() + 1, mTopAdapter.getRealCount());
    }

    public void initNewsList(NewsList newsList) {
        if (mCurrentPage <= 1) {
            mAdapter.clearData();
        }
        mAdapter.addData(newsList.item);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
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
    public void showSuccess(Map<String, NewsList> newsMap) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        if (mMultiStateView.getViewState() == MultiStateView.VIEW_STATE_LOADING) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public void showNoMore() {
        Snackbar.make(mMultiStateView, "没有数据了", Snackbar.LENGTH_SHORT).show();
        hideloading();
    }

    @Override
    public void showDocDetail(NewsInfo newsEntity) {
        Intent intent = new Intent(AppContext.getContext(), NewsDocDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constans.Detail.DETAIL_ID, newsEntity.id);
        bundle.putString(Constans.Detail.DETAIL_TYPE, newsEntity.type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showSlideDetail(NewsInfo newsEntity) {
        Intent intent = new Intent(AppContext.getContext(), NewsSlideDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constans.Detail.DETAIL_ID, newsEntity.id);
        bundle.putString(Constans.Detail.DETAIL_TYPE, newsEntity.type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        NewsInfo item = mAdapter.getItem(position - 1);
        if (item != null)
            mPresenter.onItemClick(view, item);
    }

//    public class RotationRun implements Runnable {
//
//        @Override
//        public void run() {
//            if (mIsRotation) {
//                mRotationHndler.postDelayed(mRotationRun, ROTATION_DELAY);
//                LogUtils.e("run");
//            }
//        }
//
//        public void start() {
//            if (!mIsRotation) {
//                mIsRotation = true;
//                mRotationHndler.removeCallbacks(mRotationRun);
//                mRotationHndler.postDelayed(mRotationRun, ROTATION_DELAY);
//            }
//        }
//
//        public void stop() {
//            if (mIsRotation) {
//                mIsRotation = false;
//                mRotationHndler.postDelayed(mRotationRun, ROTATION_DELAY);
//            }
//
//        }
//    }

//    public void startAutoCycle() {
//        startAutoCycle(0, ROTATION_DELAY, mAutoRecover);
//    }
//
//    public void stopAutoCycle() {
//        if (mResuminTask != null)
//            mResuminTask.cancel();
//        if (mResuminTimer != null)
//            mResuminTimer.cancel();
//        if (mCycleTask != null)
//            mCycleTask.cancel();
//        if (mCycleTimer != null)
//            mCycleTimer.cancel();
//        mAutoCycle = false;
//        mAutoRecover = false;
//    }
//
//    public void pauseAutoCycle() {
//        if (mCycling) {
//            mCycleTask.cancel();
//            mCycleTask.cancel();
//            mCycling = false;
//        } else if (mResuminTimer != null && mResuminTask != null) {
//            recoverCycle();
//        }
//    }
//
//    private void recoverCycle() {
//        if (!mAutoRecover || !mAutoCycle) {
//            return;
//        }
//        if (!mCycling) {
//            if (mResuminTask != null && mResuminTimer != null) {
//                mResuminTask.cancel();
//                mResuminTimer.cancel();
//            }
//            mResuminTimer = new Timer();
//            mResuminTask = new TimerTask() {
//                @Override
//                public void run() {
//                    startAutoCycle();
//                }
//            };
//            mResuminTimer.schedule(mResuminTask, 6000);
//        }
//    }
//
//    public void startAutoCycle(long delay, long duration, boolean autoRecover) {
//        if (mCycleTimer != null)
//            mCycleTimer.cancel();
//        if (mCycleTask != null)
//            mCycleTask.cancel();
//        if (mResuminTask != null)
//            mResuminTask.cancel();
//        if (mResuminTimer != null)
//            mResuminTimer.cancel();
//        mDelay = delay;
//        mDuration = duration;
//        mAutoRecover = autoRecover;
//        mCycleTimer = new Timer();
//        mCycleTask = new TimerTask() {
//            @Override
//            public void run() {
//                mRotationHndler.sendEmptyMessage(0);
//            }
//        };
//        mCycleTimer.schedule(mCycleTask, mDelay, mDuration);
//        mCycling = true;
//        mAutoCycle = true;
//    }
}
