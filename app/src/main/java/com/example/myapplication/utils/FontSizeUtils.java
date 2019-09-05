package com.example.myapplication.utils;

import android.webkit.WebSettings.TextSize;

import com.example.myapplication.AppContext;

/**
 * Created by MBENBEN on 2016/1/22.
 */
public class FontSizeUtils {
    public static final String WEBVIEW_FONT_SIZE_KEY = "webview_font_size_key";

    public static int getSaveFontIndex(){
        return (int) SPUtils.get(AppContext.getContext(), WEBVIEW_FONT_SIZE_KEY, 2);
    }

    public static TextSize getSaveFont(){
        return getFontSize(getSaveFontIndex());
    }

    public static TextSize getFontSize(Integer fontIndex){
        TextSize fontSize = null;
        switch (fontIndex){
            case 0:
                fontSize = TextSize.LARGEST;
                break;
            case 1:
                fontSize = TextSize.LARGER;
                break;
            case 2:
                fontSize = TextSize.NORMAL;
                break;
            case 3:
                fontSize = TextSize.SMALLER;
                break;
        }
        return fontSize;
    }

    public static void saveFontSize(Integer fontIndex){
        SPUtils.put(AppContext.getContext(), WEBVIEW_FONT_SIZE_KEY, fontIndex);
    }
}
