package com.example.myapplication;

import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.fragment.GameViewPagerFragment;
import com.example.myapplication.fragment.NewsViewPagerFragment;
import com.example.myapplication.fragment.PictureViewPagerFragment;
import com.example.myapplication.fragment.VideoViewPagerFragment;
import com.example.myapplication.fragment.page.MusicFragment;

/**
 * Created by MBENBEN on 2015/12/25.
 */
public enum DrawerPage {
    NEWS(R.id.nav_news, R.mipmap.ic_news, R.string.nav_news, NewsViewPagerFragment.newInstance()),
    PICTURE(R.id.nav_picture, R.mipmap.ic_picture, R.string.nav_picture, PictureViewPagerFragment.newInstance()),
    VIDEO(R.id.nav_video, R.mipmap.ic_video, R.string.nav_video, VideoViewPagerFragment.newInstance()),
    GAME(R.id.nav_game, R.mipmap.ic_music, R.string.nav_game, GameViewPagerFragment.newInstance()),
    MUSICE(R.id.nav_musice, R.mipmap.ic_tab_songs_unselected, R.string.nav_musice, MusicFragment.newInstance());
    public int id;
    private int icon;
    private int title;
    private BaseFragment fragment;

    DrawerPage(int id, int icon, int title, BaseFragment fragment) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.fragment = fragment;
    }

    public static DrawerPage getPageById(int id){
        for (DrawerPage page : DrawerPage.values()) {
            if (page.getId() == id)
                return page;
        }
        return null;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
