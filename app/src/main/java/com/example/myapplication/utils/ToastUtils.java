package com.example.myapplication.utils;

import android.widget.Toast;

import com.example.myapplication.AppContext;

/**
 * Created by MBENBEN on 2015/11/23.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(String str){
        if(mToast == null){
            mToast = Toast.makeText(AppContext.getContext(),str,Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.show();
    }

    public static void showToast(int strId){
        String str = UIUtils.getString(strId);
        if(mToast == null){
            mToast = Toast.makeText(AppContext.getContext(),str,Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.show();
    }
}
