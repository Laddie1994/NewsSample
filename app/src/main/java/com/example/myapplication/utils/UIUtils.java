package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.AppContext;
import com.example.myapplication.AppDetailActivity;
import com.example.myapplication.Constans;
import com.example.myapplication.DownloadDetailActivity;
import com.example.myapplication.GameDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.ImageDetailActivity;


/**
 * Created by MBENBEN on 2015/12/24.
 */
public class UIUtils {

    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page.js\">setTimeout(function(){autoResizeVideo($('.mod-yivideo-warp'));}, 10);</script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/jquery_1.11.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/get.php\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/ground(8).js_102623.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/jquery.cookie.js_103559.js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/sharedoc.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/vplayer.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/event.css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    public static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    /** 获取上下文 */
    public static Context getContext() {
        return AppContext.getContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /** 获取字符数组 */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /** 根据Id颜色获取颜色 */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /** 根据id获取尺寸 */
    public static int getDimen(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(int id){
        return getResources().getDrawable(id);
    }

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static String getString(int id){
        return getResources().getString(id);
    }

    public static String setHtmlCotentSupportImagePreview(String body){
        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        if ((Boolean)SPUtils.get(AppContext.getContext(), Constans.SP.KEY_LOAD_IMAGE, true) || TDevice.isWifiOpen()) {
            // 过滤掉 img标签的width,height属性
            body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
            // 添加点击图片放大支持
            // 添加点击图片放大支持
            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" onClick=\"showImagePreview('$2')\"");
        } else {
            // 过滤掉 img标签
            body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
        }
        return body;
    }

    /**
     * 在主线程中执行代码
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isRunOnMainThread()) {
            // 执行代码
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public static void post(Runnable runnable) {
        Handler handler = getHandler();
        handler.post(runnable);
    }

    public static Handler getHandler() {
        return AppContext.getHandler();
    }

    public static boolean isRunOnMainThread() {
        return android.os.Process.myTid() == getMainThreadTid();
    }

    private static int getMainThreadTid() {
        return AppContext.getMainThreadId();
    }

    public static Drawable getGameColor(String tag){
        Drawable drawable = null;
        if("角色扮演".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_role);
        }else if("休闲益智".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_flight);
        }else if ("动作射击".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_motion);
        }else if("策略塔防".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_exploration);
        }else if("酷跑闯关".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_parkour);
        }else if("飞行空战".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_strategy);
        }else if("清除游戏".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_fallow);
        }else if("赛车竞技".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_eliminate);
        }else if("格斗快打".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_racing);
        }else if("模拟经营".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_simulation);
        }else if("恋爱养成".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_love);
        }else if("体育竞技".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_sport);
        }else if("探险解迷".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_chess);
        }else if("棋盘游戏".equals(tag)){
            drawable = getDrawable(R.drawable.rounded_image_wrestle);
        }
        return drawable;
    }

    public static void skipDownPage(Context context, Bundle extras){
        Intent intent = new Intent(AppContext.getContext(), DownloadDetailActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static void skipImageDetail(Context context, Bundle extras){
        Intent intent = new Intent(AppContext.getContext(), ImageDetailActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static void skipAppDetail(Context context, Bundle extras){
        Intent intent = new Intent(AppContext.getContext(), AppDetailActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static void skipGameDetail(Context context, Bundle extras){
        Intent intent = new Intent(AppContext.getContext(), GameDetailActivity.class);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static void skipDownDetail(Context context, Bundle bundle){
        Intent intent = new Intent(AppContext.getContext(), DownloadDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
