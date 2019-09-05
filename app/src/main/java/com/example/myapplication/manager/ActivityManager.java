package com.example.myapplication.manager;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 管理Activity
 * Created by MBENBEN on 2015/12/24.
 */
public class ActivityManager {

    private static ActivityManager instance;
    private static List<Activity> activities = new LinkedList<>();

    private ActivityManager(){
    }

    public static ActivityManager getInstance(){
        if(instance == null){
            synchronized (ActivityManager.class){
                instance = new ActivityManager();
            }
        }
        return instance;
    }

    public synchronized Activity getForegroundActivity(){
        return activities.size() > 0 ? activities.get(activities.size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity){
        activities.add(activity);
    }

    public synchronized void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public synchronized void clearAll(){
        final List<Activity> copyList = new LinkedList<>(activities);
        for (Activity activity:copyList) {
            activity.finish();
            removeActivity(activity);
        }
        activities.clear();
        copyList.clear();
    }

    public synchronized void clearToTop(){
        for (int i = activities.size() - 2; i > -1; i--) {
            Activity activity = activities.get(i);
            removeActivity(activity);
            activity.finish();
            i = activities.size() - 1;
        }
    }

}
