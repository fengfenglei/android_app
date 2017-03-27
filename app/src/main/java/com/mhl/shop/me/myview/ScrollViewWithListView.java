package com.mhl.shop.me.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：Administrator
 * 时间；2016-12-6 14:45
 * 描述：
 */
public class ScrollViewWithListView extends ListView {
    public ScrollViewWithListView(Context context) {
        super(context);
    }

    public ScrollViewWithListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewWithListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
