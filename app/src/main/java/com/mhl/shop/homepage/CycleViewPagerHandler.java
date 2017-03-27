package com.mhl.shop.homepage;
import android.content.Context;
import android.os.Handler;
/**
 * 作者：lff
 * 时间；2016-11-11 10:06
 * 描述：为了防止内存泄漏，定义外部类，防止内部类对外部类的引用
 */
public class CycleViewPagerHandler  extends Handler {
    Context context;

    public CycleViewPagerHandler(Context context) {
        this.context = context;
    }
};