package com.mhl.shop.main.wight;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * @描述: 自定义线性布局(物流信息列表用到)
 * @svn版本: $Rev: 16295 $

 */
public class ClistView_NoScroll extends LinearLayout implements OnClickListener {

	public interface OnItemClickListener{
		public void onItemClick(AdapterView<?> v_adapter, View v_view, int v_position, long v_long);
	}
	
	private OnItemClickListener m_listener = null;
	private BaseAdapter m_adapter = null;
	private int         m_lineColor = Color.TRANSPARENT;
	private int         m_lineHeight = 1;
	private Context m_context   = null;
	
	public ClistView_NoScroll(Context context) {
		super(context);
		m_context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.TRANSPARENT);
	}
	
	public ClistView_NoScroll(Context context,AttributeSet v_attrs){
		super(context,v_attrs);
		m_context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.TRANSPARENT);	
	}
	
	public void setAdapter(BaseAdapter v_adapter){
		if (v_adapter == null) {
			return;
		}
		m_adapter = v_adapter;
		refreshListView();
	}
	
	public void setListLine(int v_lineColor,int v_lineHeight){
		m_lineColor = v_lineColor;
		m_lineHeight = v_lineHeight;
	}
	
	public void refreshListView(){
		this.removeAllViews();
		int count = m_adapter.getCount();
		View v_view = null;
		LinearLayout layout_line = null;
		for (int i = 0; i < count; i++) {
			v_view = m_adapter.getView(i, null, null);
			v_view.setTag(i);
			v_view.setOnClickListener(this);
			layout_line = new LinearLayout(m_context);
			layout_line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, m_lineHeight));
			layout_line.setBackgroundColor(m_lineColor);
			
			this.addView(v_view);
			this.addView(layout_line);

		}
		this.requestLayout();
		this.invalidate();
	}
	
	@Override
	public void onClick(View v_view){
		int positon = (Integer)	v_view.getTag();
		if (m_listener!=null) {
			m_listener.onItemClick(null, v_view, positon, 0);
		}
	}
	


}
