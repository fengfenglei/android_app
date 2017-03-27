package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.been.Supplier;
import com.mhl.shop.utils.Urls;

import java.util.ArrayList;
import java.util.List;

public class SupplierListAdapter extends BaseAdapter {
	private List<Supplier> datas = new ArrayList<>();
	private Context context;

	public SupplierListAdapter(Context context, List<Supplier> datas){
		this.context = context;
		this.datas = datas;
	}
	@Override
	public int getCount() {
		return datas.size() % 2 == 0 ? datas.size() / 2 : datas.size() / 2 + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if(convertView == null){
			hodler = new ViewHodler();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.supplirt_list_item, null);
			hodler.layout1 = (LinearLayout) convertView.findViewById(R.id.item_layout1);
			hodler.layout2 = (LinearLayout) convertView.findViewById(R.id.item_layout2);
			hodler.goodsTitle1 = (TextView) convertView.findViewById(R.id.goodsTitle1);
			hodler.goodsTitle2 = (TextView) convertView.findViewById(R.id.goodsTitle2);
			hodler.shoppingPrice1 = (TextView) convertView.findViewById(R.id.shoppingPrice1);
			hodler.shoppingPrice2 = (TextView) convertView.findViewById(R.id.shoppingPrice2);
			hodler.shoppingmark1 = (TextView) convertView.findViewById(R.id.shoppingmark1);
			hodler.shoppingmark2 = (TextView) convertView.findViewById(R.id.shoppingmark2);
			hodler.icon1 = (ImageView) convertView.findViewById(R.id.icon1);
			hodler.icon2 = (ImageView) convertView.findViewById(R.id.icon2);
			convertView.setTag(hodler);
		}else{
			hodler = (ViewHodler) convertView.getTag();
		}
		
		 String item1 = "";
		 String item2 = "";
		String goodsTitle1 = "";
		String goodsTitle2 = "";
		String icon1 = "";
		String icon2 = "";
		String shoppingPrice1 = "";
		String shoppingPrice2 = "";
		String shoppingmark1 = "";
		String shoppingmark2 = "";
		String pkId1 = "";
		String pkId2 = "";
		if(position * 2 + 1 < datas.size()){
			item1 = String.valueOf(datas.get(position * 2).getGoodsName());
			item2 = String.valueOf(datas.get(position * 2 + 1).getGoodsName());
			goodsTitle1 = String.valueOf(datas.get(position * 2).getGoodsTitle());
			goodsTitle2 = String.valueOf(datas.get(position * 2 + 1).getGoodsTitle());
			icon1 = String.valueOf(datas.get(position * 2).getPicId());
			icon2 = String.valueOf(datas.get(position * 2 + 1).getPicId());
			shoppingPrice1 = String.valueOf(datas.get(position * 2).getShoppingPrice());
			shoppingPrice2 = String.valueOf(datas.get(position * 2 + 1).getShoppingPrice());
			shoppingmark1 = String.valueOf(datas.get(position * 2).getMarketPrice());
			shoppingmark2 = String.valueOf(datas.get(position * 2 + 1).getMarketPrice());
			pkId1 = String.valueOf(datas.get(position * 2).getPkId());
			pkId2 = String.valueOf(datas.get(position * 2 + 1).getPkId());
		}else if(position * 2 + 1 == datas.size()){
			item1 = String.valueOf(datas.get(position * 2).getGoodsName());
			item2 ="";
			goodsTitle1 = String.valueOf(datas.get(position * 2).getGoodsTitle());
			goodsTitle2 = "";
			shoppingmark1 = String.valueOf(datas.get(position * 2).getMarketPrice());
			shoppingmark2 = "";
			icon1 = String.valueOf(datas.get(position * 2).getPicId());
			icon2 = "";
			shoppingPrice1 = String.valueOf(datas.get(position * 2).getShoppingPrice());
			shoppingPrice2 = "";
			pkId1 = String.valueOf(datas.get(position * 2).getPkId());
			pkId2 = "";
			hodler.layout2.setVisibility(View.INVISIBLE);
		}

		if(item1 != null){
//			hodler.textView1.setText(item1);
			hodler.goodsTitle1.setText(item1);
			hodler.shoppingPrice1.setText("¥"+shoppingPrice1);
			hodler.shoppingmark1.setText("¥"+shoppingmark1);
			hodler.shoppingmark1.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
			Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+icon1+"-400-400.jpg").into(hodler.icon1);
			hodler.layout1.setTag(item1);
			final String finalPkId1 = pkId1;
			hodler.layout1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent();
					intent.putExtra("goodsid", finalPkId1);
					intent.setClass(context, GoodsDetailsActivity.class);
					context.startActivity(intent);
				}
			});
		}
		if(item2 != null){
//			hodler.textView2.setText(item2);
			hodler.goodsTitle2.setText(item2);
			hodler.shoppingPrice2.setText("¥"+shoppingPrice2);
			hodler.shoppingmark2.setText("¥"+shoppingmark2);
			hodler.shoppingmark2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
			Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+icon2+"-400-400.jpg").into(hodler.icon2);
			hodler.layout2.setTag(item2);
			final String finalPkId = pkId2;
			hodler.layout2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent();
					intent.putExtra("goodsid", finalPkId);
					intent.setClass(context, GoodsDetailsActivity.class);
					context.startActivity(intent);
				}
			});
		}
		
		return convertView;
	}
	
	class ViewHodler {
//		TextView textView1;
//		TextView textView2;
		TextView goodsTitle1;
		TextView goodsTitle2;
		TextView shoppingPrice1;
		TextView shoppingPrice2;
		TextView shoppingmark1;
		TextView shoppingmark2;
		ImageView icon1;
		ImageView icon2;
		LinearLayout layout1;
		LinearLayout layout2;
	}



}
