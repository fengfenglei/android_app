package com.mhl.shop.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.mhl.shop.main.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Administrator
 * 时间；2016-11-11 18:28
 * 描述：
 */
public class UIUtils {


    /**
     * 上下文的获取
     *
     * @return
     */
    public static Context getContext()
    {
        return MyApplication.getContext();
    }

    /**
     * 获取资源
     *
     * @return
     */
    public static Resources getResources()
    {
        return getContext().getResources();
    }

    public static long getMainThreadId()
    {
        return MyApplication.getMainThreadId();
    }

    public static Handler getMainThreadHandler()
    {
        return MyApplication.getMainHandler();
    }

    /**
     * 主线程中执行 任务
     *
     * @param task
     */
    public static void runOnUiThread(Runnable task)
    {
        long currentThreadId = android.os.Process.myTid();
        long mainThreadId = getMainThreadId();

        if (currentThreadId == mainThreadId)
        {
            // 如果在主线程中执行
            task.run();
        }
        else
        {
            // 需要转的主线程执行
            getMainThreadHandler().post(task);
        }
    }
    /**
     * 让task在主线程中执行
     */
    public static void post(Runnable task)
    {
        int myTid = android.os.Process.myTid();

        if (myTid == getMainThreadId())
        {
            // 在主线程中执行的
            task.run();
        }
        else
        {
            // 在子线程中执行的
            getMainThreadHandler().post(task);
        }
    }
    /**
     * 时间戳
     *
     * @param  str
     * @return 验证通过返回
     */
    public static String isTime( ) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return 如果字符串为空，返回true；否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || s.matches("\\s*") || s.equals("null");
    }

    /**
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip)
    {
        // 公式 1: px = dp * (dpi / 160)
        // 公式 2: dp = px / denistity;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        // metrics.densityDpi
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px)
    {
        // 公式 1: px = dp * (dpi / 160)
        // 公式 2: dp = px / denistity;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        // metrics.densityDpi
        return (int) (px / density + 0.5f);
    }

    public static String getString(int resId)
    {
        return getResources().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs)
    {
        return getResources().getString(resId, formatArgs);
    }

    public static String getPackageName()
    {
        return getContext().getPackageName();
    }

    public static String[] getStringArray(int resId)
    {
        return getResources().getStringArray(resId);
    }

    public static int getColor(int resId)
    {
        return getResources().getColor(resId);
    }

    /**
     * 执行延时操作
     *
     * @param task
     * @param delay
     */
    public static void postDelayed(Runnable task, long delay)
    {
        getMainThreadHandler().postDelayed(task, delay);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public static void removeCallbacks(Runnable task)
    {
        getMainThreadHandler().removeCallbacks(task);
    }


    private static long lastClickTime;
    //防止按钮多次被点击
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}
