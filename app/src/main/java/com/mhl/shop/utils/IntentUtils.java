package com.mhl.shop.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 作者：LFF
 * 时间；2016-11-16 18:03
 * 描述：IntentUtils
 */
public class IntentUtils {

    /**
     * 开启activity
     * @param context
     * @param cls
     */
    public static void startActivity(Activity context, Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }

    /**
     * 带参数传递
     * @param context
     * @param cls
     */
    public static void startActivityWithArgs(Activity context, Class<?> cls,String key,String value){
        Intent intent = new Intent(context,cls);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void startActivityIntent(Activity context,Intent intent, Class<?> cls){
        intent = new Intent(context,cls);
        context.startActivity(intent);
    }

    /**
     * 开启和结束activity
     * @param context
     * @param cls
     */
    public static void startActivityAndFinish(Activity context, Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
        context.finish();
    }
    public static void startActivityAndFinishAndFragment(Activity context, Class<?> cls,Fragment fragment){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
        context.finish();
    }


}
