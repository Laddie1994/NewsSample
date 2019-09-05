package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.fragment.BaseFragment;
import com.github.library.swipebacklayout.lib.SwipeBackLayout;
import com.github.library.swipebacklayout.lib.Utils;
import com.github.library.swipebacklayout.lib.app.SwipeBackActivityBase;
import com.github.library.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by MBENBEN on 2016/3/15.
 */
public class ActionBarCastActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeBackActivityBase {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToogle;
    private boolean mIsInitToolbar;
    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (mDrawerToogle != null) mDrawerToogle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (mDrawerToogle != null) mDrawerToogle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (mDrawerToogle != null) mDrawerToogle.onDrawerClosed(drawerView);
//            mCurFragment.requestData();
//            if (mItemToOpenWhenDrawerCloses >= 0) {
//                DrawerPage page = DrawerPage.getPageById(mItemToOpenWhenDrawerCloses);
//                if (page != null) {
//                    setTitle(page.getTitle());
//                    Fragment fragment = page.getFragment();
//                    switchFragment(R.id.content, fragment);
//                }
//            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (mDrawerToogle != null) mDrawerToogle.onDrawerStateChanged(newState);
        }
    };

    //右滑退出
    private SwipeBackActivityHelper mBackHelper;
    private BaseFragment mCurFragment;

    protected void initToolbar() {
        mBackHelper = new SwipeBackActivityHelper(this);
        mBackHelper.onActivityCreate();
        setStatusBarColor(R.color.colorPrimary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id toolbar'");
        }
        mToolbar.inflateMenu(R.menu.main);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null) {
            initDrawer();
        } else {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mIsInitToolbar = true;
    }

    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi_view);
        if (navigationView == null) {
            throw new IllegalStateException("Layoutt id required a NavigationView with id 'nav_view'");
        }
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerListener);
        navigationView.setNavigationItemSelectedListener(this);
        updateDrawerToggle();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mIsInitToolbar) {
            throw new IllegalStateException("you must run super.initToolBar at the end of your onCreate method");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToogle != null) {
            mDrawerToogle.syncState();
        }
        mBackHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mBackHelper != null) {
            return mBackHelper.findViewById(id);
        }
        return view;
    }

    protected void updateDrawerToggle() {
        if (mDrawerToogle == null) {
            return;
        }
        mDrawerToogle.setDrawerIndicatorEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mDrawerToogle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        item.setChecked(true);
        if (item.getItemId() >= 0) {
            DrawerPage page = DrawerPage.getPageById(item.getItemId());
            if (page != null) {
                setTitle(page.getTitle());
                mCurFragment = page.getFragment();
                switchFragment(R.id.content, mCurFragment);
            }
        }
        return true;
    }

    protected void switchFragment(int content, Fragment fragment) {
        if (null == fragment) {
            throw new NullPointerException("replace 的 fragment 不能为空");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(content, fragment)
                .commit();
//        if (mCurFragment == null) {
//            beginTransaction.add(content, fragment).commit();
//        } else if (fragment != mCurFragment) {
//            if (!fragment.isAdded()) {
//                beginTransaction.hide(mCurFragment).add(content, fragment);
//            } else {
//                beginTransaction.hide(mCurFragment).show(fragment);
//            }
//            beginTransaction.commit();
//        }
//        mCurFragment = fragment;
    }

    public void setStatusBarColor(int color) {
        getSwipeBackLayout().setStatusBarColor(color);
    }

    public SwipeBackActivityHelper getBackHelper() {
        return mBackHelper;
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title))
            getSupportActionBar().setTitle(title);
    }

    public void setTitle(int id) {
        String title = getResources().getString(id);
        if (!TextUtils.isEmpty(title))
            getSupportActionBar().setTitle(title);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mBackHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
