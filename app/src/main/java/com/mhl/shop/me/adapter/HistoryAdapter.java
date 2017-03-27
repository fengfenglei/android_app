package com.mhl.shop.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.been.History;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：浏览记录列表
 */
public class HistoryAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<History.RowsBean> alist;

    public HistoryAdapter(Context context, List<History.RowsBean> list) {
        super();
        this.context = context;
        this.alist = list;
    }



    public int getCount() {
        return alist.size();
    }

    public Object getItem(int position) {
        return alist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.myhistory_item, null);
            holder = new ViewHolder();
            holder.mycollCartGoodsIcon = (ImageView) convertView.findViewById(R.id.mycoll_cart_goods_icon);
            holder.mycollCartGoodsItemTitle = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_item_title);
            holder.mycollCartGoodsSprice = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_sprice);
            holder.detail_ly= (LinearLayout) convertView.findViewById(R.id.detail_ly);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.detail_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("goodsid", alist.get(position).getGoodsId());
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getGoodsPicId()+"-200-200.jpg").placeholder(R.drawable.icon_bg).into(holder.mycollCartGoodsIcon);
        holder.mycollCartGoodsItemTitle.setText(alist.get(position).getGoodsName()+"");
        holder.mycollCartGoodsSprice.setText("¥"+ ToolsUtils.Tow(alist.get(position).getShoppingPrice()+""));


        return convertView;
    }

    class ViewHolder
    {
        ImageView mycollCartGoodsIcon;
        TextView mycollCartGoodsItemTitle;
        TextView mycollCartGoodsSprice;
        LinearLayout detail_ly;

    }

}
