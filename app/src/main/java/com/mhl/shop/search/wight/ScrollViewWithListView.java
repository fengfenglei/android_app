package com.mhl.shop.search.wight;

import android.widget.ListView;

/**
 * 作者：Administrator
 * 时间；2017-1-11 13:38
 * 描述：
 */
public class ScrollViewWithListView  extends ListView {
    public ScrollViewWithListView(android.content.Context context,android.util.AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
