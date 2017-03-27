package com.mhl.shop.homepage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：Administrator
 * 时间；2016-11-11 18:26
 * 描述：
 */
public class TouchedViewPager extends ViewPager
{

    private float	mDownX;
    private float	mDownY;

    public TouchedViewPager(Context context) {
        super(context);
    }

    public TouchedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface Toucher
    {
        public void ToucherListener();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();

        switch (action)
        {

            case MotionEvent.ACTION_DOWN:

                mDownX = ev.getX();
                mDownY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                float moveX = ev.getX();
                float moveY = ev.getY();

                float diffX = moveX - mDownX;
                float diffY = moveY - mDownY;

                if (Math.abs(diffX) > Math.abs(diffY))
                {
                    // 水平滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                else
                {
                    // 垂直滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

}
