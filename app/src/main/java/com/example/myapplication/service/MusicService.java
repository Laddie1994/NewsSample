package com.example.myapplication.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.myapplication.bean.MusicInfo;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.MusiceHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/3/20.
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener, MusicBinder.OnServiceBinderListener {

    private static final String TAG = MusicService.class.getCanonicalName();
    public static final int PLAYED = 0x00;
    public static final int PAUSE = 0x01;
    public static final int STOP = 0x02;
    public static final int CONTINUE = 0x03;
    public static final int NEXT = 0x04;
    public static final int PREVIOUS = 0x05;
    private static final int UPDATA_DELAY = 500;

    private static final int MEDIA_PLAY_UPDATA = 0x01;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private int mCurPosition;
    private ArrayList<MusicInfo> mSongs;
    private MusicBinder mBinder;
    private MusicInfo mCurMusic;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MEDIA_PLAY_UPDATA:
                    onUpdataDuration();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mSongs == null) {
            mSongs = MusiceHelper.getAllSong(this);
        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG, "onCreate");

        mBinder = new MusicBinder();
        mBinder.setOnServiceBinderListener(this);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(this);//资源错误
        mMediaPlayer.setOnPreparedListener(this);//资源准备Ok了
        mMediaPlayer.setOnCompletionListener(this);//播放完毕
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        removeUpdateMsg();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e(TAG, "---->onUnbind");
        removeUpdateMsg();
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.e(TAG, "---->onRebind");
        if (mMediaPlayer.isPlaying()) {
            sendUpdataMsg();
            mBinder.onPlayStart(mCurMusic);
        }
    }

    public void onSeekPlay(int progress) {
        LogUtils.e(TAG, "onSeekPlay");
        if (mCurMusic != null) {
            mMediaPlayer.seekTo(progress);
            mMediaPlayer.start();
            mBinder.onPlayStart(mCurMusic);
            sendUpdataMsg();
        }
    }

    public void onContinue() {
        LogUtils.e(TAG, "onContinue");
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void onUpdataDuration() {
        mBinder.onPlayDurationChanged(mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition());
        sendUpdataMsg();
    }

    public void onStop() {
        LogUtils.e(TAG, "onStop");
        removeUpdateMsg();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void onPause() {
        LogUtils.e(TAG, "onPause");
        removeUpdateMsg();
        mBinder.onPlayPause();
        mMediaPlayer.pause();
    }

    public void onNext() {
        if (mCurPosition < mSongs.size() - 1) {
            mCurPosition++;
        } else {
            mCurPosition = 0;
        }
        removeUpdateMsg();
        MusicInfo musicInfo = mSongs.get(mCurPosition);
        if (musicInfo != null)
            initMediaPlayer(musicInfo);
    }

    public void onPrevious() {
        if (mCurPosition <= 0) {
            mCurPosition = mSongs.size() - 1;
        } else {
            mCurPosition--;
        }
        removeUpdateMsg();
        MusicInfo musicInfo = mSongs.get(mCurPosition);
        if (musicInfo != null)
            initMediaPlayer(musicInfo);
    }

    public void initMediaPlayer(MusicInfo musicInfo) {
        try {
            //设置音频焦点
            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            mCurMusic = musicInfo;
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mCurMusic.getData());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlay() {
        LogUtils.e(TAG, "onPlay");
        MusicInfo musicInfo = mSongs.get(mCurPosition);
        removeUpdateMsg();
        if (musicInfo != null)
            initMediaPlayer(musicInfo);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtils.e(TAG, "onError");
        mBinder.onPlayError();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.e(TAG, "onPrepared");
        mp.start();
        mBinder.onPlayStart(mCurMusic);
        sendUpdataMsg();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mBinder.onPlayComplate();
        //默认自动下一首
        onNext();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        LogUtils.e(TAG, "onAudioFocusChange------->");
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                LogUtils.e(TAG, "AUDIOFOCUS_GAIN----->" + AudioManager.AUDIOFOCUS_GAIN);
                if (!mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.start();
                    onPlay();
                    mMediaPlayer.setVolume(1.0f, 1.0f);
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (mMediaPlayer == null) return;

                LogUtils.e(TAG, "AUDIOFOCUS_LOSS----->" + AudioManager.AUDIOFOCUS_LOSS);
                if (mMediaPlayer.isPlaying())
                    onPause();
//                mMediaPlayer.release();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                LogUtils.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT----->" + AudioManager.AUDIOFOCUS_LOSS_TRANSIENT);

                if (mMediaPlayer.isPlaying())
                    onPause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                LogUtils.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK----->" + AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK);

                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    /**
     * 移除更新UI的消息
     */
    private void removeUpdateMsg() {
        if (mHandler != null && mHandler.hasMessages(MEDIA_PLAY_UPDATA)) {
            mHandler.removeMessages(MEDIA_PLAY_UPDATA);
        }
    }

    private void sendUpdataMsg() {
        if (mHandler != null && !mHandler.hasMessages(MEDIA_PLAY_UPDATA)) {
            mHandler.sendEmptyMessageDelayed(MEDIA_PLAY_UPDATA, UPDATA_DELAY);
        }
    }

    @Override
    public void onStartTrackingTouch() {
        if (mMediaPlayer.isPlaying()) {
            removeUpdateMsg();
        }
    }

    @Override
    public void onStopTrackingTouch(int value) {
        onSeekPlay(value);
    }

    @Override
    public void onControll(int command) {
        switch (command) {
            case NEXT://下一首
                onNext();
                break;
            case PAUSE://暂停
                onPause();
                break;
            case CONTINUE://继续
                onContinue();
                break;
            case PREVIOUS://上一首
                onPrevious();
                break;
            case PLAYED://播放
                if (mMediaPlayer.isPlaying()) {
                    onPause();
                } else if (mCurMusic != null) {
                    mMediaPlayer.start();
                    mBinder.onPlayStart(mCurMusic);
                    sendUpdataMsg();
                } else {
                    onPlay();
                }
                break;
        }
    }
}
