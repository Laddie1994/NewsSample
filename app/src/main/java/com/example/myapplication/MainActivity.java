package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;

import com.example.myapplication.manager.ActivityManager;
import com.example.myapplication.service.MusicService;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long firstTime;

    protected void initView() {
        super.initView();
        initToolbar();
        setSwipeBackEnable(false);
        DrawerPage page = DrawerPage.getPageById(R.id.nav_news);
        switchFragment(R.id.content, page.getFragment());
        setTitle(page.getTitle());
    }

    @Override
    protected void initData() {
        super.initData();
        startService(new Intent(this, MusicService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicService.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (getDrawerLayout() != null ) {
            if(getDrawerLayout().isDrawerOpen(GravityCompat.START)){
                getDrawerLayout().closeDrawers();
                return;
            }
        }
        if (System.currentTimeMillis() - firstTime < 2000) {
            ActivityManager.getInstance().clearAll();
            super.onBackPressed();
        } else {
            firstTime = System.currentTimeMillis();
            Snackbar.make(getToolbar(), "点击两次退出", Snackbar.LENGTH_SHORT).show();
        }
    }
}
