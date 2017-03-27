package com.mhl.shop.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mhl.shop.R;

/**
 * 作者：lff
 * 时间；2016-11-11 11:46
 * 描述：顶部布局
 */
public class HomeTopBar extends LinearLayout implements View.OnClickListener
{
    private TextView mHome_search_bar;
    private LinearLayout	mHome_ll_scan;
    private  RelativeLayout home_news;
    public HomeTopBar(Context context) {
        this(context, null);
    }

    public HomeTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.bar_home, this);
        mHome_search_bar = (TextView) findViewById(R.id.home_search_tv);
        mHome_ll_scan = (LinearLayout) findViewById(R.id.home_ll_scan);
        home_news= (RelativeLayout) findViewById(R.id.home_news);
        mHome_search_bar.setOnClickListener(this);
        mHome_ll_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (mListener != null)
        {
            mListener.onClickListener(v.getId());
        }
    }

    private HomeClickListener	mListener;

    public void setHomeClickListener(HomeClickListener listener)
    {
        this.mListener = listener;
    }

    public interface HomeClickListener
    {
        /**
         * 实现布局中item的点击事件
         *
         * @param id
         */
        public void onClickListener(int id);
    }
}
