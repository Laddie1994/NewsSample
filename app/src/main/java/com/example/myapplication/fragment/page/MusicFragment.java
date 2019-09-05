package com.example.myapplication.fragment.page;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ActionBarCastActivity;
import com.example.myapplication.R;
import com.example.myapplication.bean.MusicInfo;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.service.MusicBinder;
import com.example.myapplication.service.MusicService;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.MusiceHelper;
import com.example.myapplication.utils.SystemBarHelper;
import com.example.myapplication.utils.StringUtils;
import com.example.myapplication.widget.PlayBitmap;
import com.example.myapplication.widget.PlayButton;
import com.example.myapplication.widget.Rotate3DAnimation;
import com.gc.materialdesign.views.Slider;

/**
 * Created by MBENBEN on 2016/3/20.
 */
public class MusicFragment extends BaseFragment implements MusicBinder.OnPlayStartListener, MusicBinder.OnPlayPauseListener, MusicBinder.OnPlayComplateListener, MusicBinder.OnPlayErrorListener, MusicBinder.OnPlayDurationChangedListener {

    private static final String TAG = MusicFragment.class.getCanonicalName();

    private PlayBitmap mPlayBitmap;
    private PlayButton mPlayBtn;
    private MusicServiceConnection mServiceSonnection;
    private ImageView mPrevious;
    private ImageView mNext;
    private Slider mSlider;
    private MusicBinder mBinder;
    private TextView mTitle;
    private TextView mEndTime;
    private TextView mStartTime;
    private TextView mArtist;

    public static MusicFragment newInstance() {
        Bundle args = new Bundle();
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_musice;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mPlayBitmap = (PlayBitmap) view.findViewById(R.id.playBitmap);
        mPlayBtn = (PlayButton) view.findViewById(R.id.playBtn);
        mNext = (ImageView) view.findViewById(R.id.next);
        mPrevious = (ImageView) view.findViewById(R.id.previous);
        mSlider = (Slider) view.findViewById(R.id.slider);
        mTitle = (TextView) view.findViewById(R.id.title);
        mArtist = (TextView) view.findViewById(R.id.artist);
        mStartTime = (TextView) view.findViewById(R.id.curTime);
        mEndTime = (TextView) view.findViewById(R.id.endTime);
        mSlider.setBackgroundColor(Color.RED);
        mSlider.setBallColor(Color.DKGRAY);
        mSlider.setShowNumberIndicator(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        onBindService();
    }

    @Override
    public void onPause() {
        super.onPause();
        onUnBindService();
    }

    private void onBindService() {
        SystemBarHelper.setStatusbarColor(getActivity(), R.color.musicBg);
        ((ActionBarCastActivity) getContext()).getToolbar().setBackgroundResource(R.color.musicBg);
        getContext().bindService(new Intent(getContext(), MusicService.class)
                , mServiceSonnection, Context.BIND_AUTO_CREATE);
    }

    private void onUnBindService() {
        SystemBarHelper.setStatusbarColor(getActivity(), R.color.colorPrimary);
        ((ActionBarCastActivity) getContext()).getToolbar().setBackgroundResource(R.color.colorPrimary);
        if (mServiceSonnection != null) {
            getContext().unbindService(mServiceSonnection);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mServiceSonnection == null)
            mServiceSonnection = new MusicServiceConnection();
        onBindService();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinder.onControll(MusicService.PLAYED);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinder.onControll(MusicService.NEXT);
            }
        });

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinder.onControll(MusicService.PREVIOUS);
            }
        });

        mSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                mSlider.setIndicatorText(StringUtils.formatMusicTime(value));
            }

            @Override
            public void onStartTrackingTouch(Slider slider) {
                mBinder.onStartTrackingTouch();
            }

            @Override
            public void onStopTrackingTouch(Slider slider) {
                mBinder.onStopTrackingTouch(slider.getValue());
            }
        });
    }

    @Override
    public void onPlayStart(MusicInfo info) {
        LogUtils.e(TAG, "---->onPlayStart");
        mSlider.setMax(Integer.valueOf(info.getDuration()));
        Bitmap artwork = MusiceHelper.getArtwork(getContext(), Long.valueOf(info.get_id())
                , Long.valueOf(info.getAlbumId()), true);
        startRotateAnim(artwork);
        if (!mPlayBtn.isPlaying()) {
            mPlayBtn.setPlaying(true);
        }
        mArtist.setText(info.getArtist());
        mTitle.setText(info.getTitle());
        mEndTime.setText(StringUtils.formatMusicTime(Integer.valueOf(info.getDuration())));
    }

    private void startRotateAnim(final Bitmap artwork) {
        int centerX = mPlayBitmap.getWidth() / 2;
        int centerY = mPlayBitmap.getHeight() / 2;
        final Rotate3DAnimation firstRotate = new Rotate3DAnimation(0, 90, centerX, centerY, 300.0f, true);
        firstRotate.setDuration(500);
        firstRotate.setFillAfter(true);
        final Rotate3DAnimation secendRotate = new Rotate3DAnimation(270, 360, centerX, centerY, 300.0f, false);
        secendRotate.setDuration(500);
        secendRotate.setFillAfter(true);
        firstRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mPlayBitmap.setRota(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPlayBitmap.setAnimation(secendRotate);
                mPlayBitmap.setRota(true);
                mPlayBitmap.setBitmapCover(artwork);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mPlayBitmap.startAnimation(firstRotate);
    }

    @Override
    public void onPlayPause() {
        mPlayBtn.setPlaying(false);
        mPlayBitmap.setRota(false);
    }

    @Override
    public void onPlayComplate() {
//        mPlayBtn.setPlaying(false);
//        mPlayBitmap.setRota(false);
//        mBinder.onControll(MusicService.NEXT);
    }

    @Override
    public void onPlayError() {
        Snackbar.make(getRootView(), "播放出错了", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayDurationChanged(int duration, int curDuration) {
        final int position = curDuration;
        mSlider.setValue(position);
        mStartTime.setText(StringUtils.formatMusicTime(position));
    }

    public class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicBinder) service;
            mBinder.setOnPlayStartListener(MusicFragment.this);
            mBinder.setOnPlayPauseListener(MusicFragment.this);
            mBinder.setOnPlayComplateListener(MusicFragment.this);
            mBinder.setOnPlayErrorListener(MusicFragment.this);
            mBinder.setOnPlayDurationChangedListener(MusicFragment.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.e(MusicFragment.class.getCanonicalName(), "onServiceDisconnected");
            mServiceSonnection = null;
        }
    }
}
