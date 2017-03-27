package com.mhl.shop.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.mhl.shop.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 作者：lff
 * 时间；2016-11-11 10:12
 * 描述： ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param text
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }
}

