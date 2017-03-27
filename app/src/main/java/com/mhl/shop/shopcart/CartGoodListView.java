package com.mhl.shop.shopcart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CartGoodListView extends ListView {

	public CartGoodListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CartGoodListView(Context context){
		super(context);
	}
    
	public CartGoodListView(Context context, AttributeSet attrs, int defStyle) { 
	    super(context, attrs, defStyle); 
	} 
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec); 
	}
}
