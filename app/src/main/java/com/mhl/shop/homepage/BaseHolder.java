package com.mhl.shop.homepage;

import android.view.View;

/**
 * 作者：lff
 * 时间；2016-11-11 18:27
 * 描述：基类;MVC中C，用来控制视图和数据，对视图和数据进行合理的显示
 */
public abstract class BaseHolder<T>
{
    protected View mRootView;
    protected T		mData;

    public BaseHolder() {
        mRootView = initView();//这里负责调用,子类负责实现

        // 打标记
        mRootView.setTag(this);
    }

    protected abstract View initView();

    protected abstract void refreshUI(T data);

    public void setData(T data)
    {
        this.mData = data;

        // UI刷新
        refreshUI(mData);//这里负责调用,子类负责实现
    }

    public View getRootView()
    {
        return mRootView;
    }
}
