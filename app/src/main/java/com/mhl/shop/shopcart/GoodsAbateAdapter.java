package com.mhl.shop.shopcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 失效的商品
 */
public class GoodsAbateAdapter extends BaseAdapter{

	private Context context;
	private  List<Cart>  list;
	
	public GoodsAbateAdapter(Context context, List<Cart> goodsListAbate) {
		super();
		this.context = context;
		this.list = goodsListAbate;
	}



	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;
		View view =convertView;
		if(view==null)
		{
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.item_goodabate_lv, null);
			holder.home_gv_iv_recommend_icon=(ImageView) view.findViewById(R.id.home_gv_iv_recommend_icon);
			holder.home_recommend_tv_title=(TextView) view.findViewById(R.id.home_recommend_tv_title);

			view.setTag(holder);
		}else
		{
			holder=(ViewHolder) view.getTag();
		}
		

		holder.home_recommend_tv_title.setText(list.get(position).getGoodsIdName());
		Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position).getGoodsPicId()+"-300-300.jpg").placeholder(R.drawable.icon_bg).into(holder.home_gv_iv_recommend_icon);


		return view;
	}
	
	class ViewHolder
	{
		ImageView home_gv_iv_recommend_icon;
		TextView home_recommend_tv_title;
	}

}
