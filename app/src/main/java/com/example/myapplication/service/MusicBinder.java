package com.example.myapplication.service;

import android.os.Binder;

import com.example.myapplication.bean.MusicInfo;

/**
 * Created by MBENBEN on 2016/3/21.
 */
public class MusicBinder extends Binder {
    private OnPlayStartListener onPlayStartListener;
    private OnPlayPauseListener onPlayPauseListener;
    private OnPlayComplateListener onPlayComplateListener;
    private OnPlayErrorListener onPlayErrorListener;
    private OnServiceBinderListener onServiceBinderListener;

    public void setOnPlayDurationChangedListener(OnPlayDurationChangedListener onPlayDurationChangedListener) {
        this.onPlayDurationChangedListener = onPlayDurationChangedListener;
    }

    private OnPlayDurationChangedListener onPlayDurationChangedListener;

    public void onPlayStart(MusicInfo info){
        if(onPlayStartListener != null)
            onPlayStartListener.onPlayStart(info);
    }

    public void onPlayPause(){
        if(onPlayPauseListener != null)
            onPlayPauseListener.onPlayPause();
    }

    public void onPlayDurationChanged(int duration, int curDuration){
        if(onPlayDurationChangedListener != null)
            onPlayDurationChangedListener.onPlayDurationChanged(duration, curDuration);
    }

    public void onPlayComplate(){
        if(onPlayComplateListener != null)
            onPlayComplateListener.onPlayComplate();
    }

    public void onPlayError(){
        if(onPlayErrorListener != null)
            onPlayErrorListener.onPlayError();
    }
    public void onStopTrackingTouch(int value){
        if(onServiceBinderListener != null)
            onServiceBinderListener.onStopTrackingTouch(value);
    }

    public void onStartTrackingTouch(){
        if(onServiceBinderListener != null)
            onServiceBinderListener.onStartTrackingTouch();
    }

    public void onControll(int command){
        if(onServiceBinderListener != null)
            onServiceBinderListener.onControll(command);
    }

    public void setOnServiceBinderListener(OnServiceBinderListener onServiceBinderListener) {
        this.onServiceBinderListener = onServiceBinderListener;
    }

    public void setOnPlayStartListener(OnPlayStartListener onPlayStartListener) {
        this.onPlayStartListener = onPlayStartListener;
    }

    public void setOnPlayPauseListener(OnPlayPauseListener onPlayPauseListener) {
        this.onPlayPauseListener = onPlayPauseListener;
    }

    public void setOnPlayComplateListener(OnPlayComplateListener onPlayComplateListener) {
        this.onPlayComplateListener = onPlayComplateListener;
    }

    public void setOnPlayErrorListener(OnPlayErrorListener onPlayErrorListener) {
        this.onPlayErrorListener = onPlayErrorListener;
    }

    public interface OnPlayStartListener {
        void onPlayStart(MusicInfo info);
    }

    public interface OnPlayDurationChangedListener{
        void onPlayDurationChanged(int duration, int curDuration);
    }

    public interface OnPlayPauseListener {
        void onPlayPause();
    }

    public interface OnPlayComplateListener {
        void onPlayComplate();
    }

    public interface OnPlayErrorListener {
        void onPlayError();
    }

    protected interface OnServiceBinderListener {
        void onStartTrackingTouch();

        void onStopTrackingTouch(int value);

        void onControll(int command);
    }
}
