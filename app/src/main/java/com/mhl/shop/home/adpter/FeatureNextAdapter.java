package com.mhl.shop.home.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.home.been.FeatureNext;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：特色馆
 */
public class FeatureNextAdapter extends MyBaseAdpter {

    private final Context context;
    private final  List<FeatureNext.RowsBean> alist;

    public FeatureNextAdapter(Context context, List<FeatureNext.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.feature_next_item, null);
            holder = new ViewHolder();
            holder.mycollCartGoodsIcon = (ImageView) convertView.findViewById(R.id.mycoll_cart_goods_icon);
            holder.mycollCartGoodsItemTitle = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_item_title);
            holder.marketPrice = (TextView) convertView.findViewById(R.id.marketPrice);
            holder.mycollCartGoodsSprice = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_sprice);

            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mycollCartGoodsItemTitle.setText(alist.get(position).getGsName());
        holder.mycollCartGoodsSprice.setText("¥"+ToolsUtils.Tow(alist.get(position).getShoppingPrice()+""));
        holder.marketPrice.setText("¥"+ToolsUtils.Tow(alist.get(position).getMarketPrice()+""));
        holder.marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        Glide.with(context).load(Urls.URL_BASE+"/file/v3/download-"+alist.get(position).getGsImg()+"-180-180.jpg").into(holder.mycollCartGoodsIcon);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("goodsid", alist.get(position).getGsId()+"");
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        ImageView mycollCartGoodsIcon;
        TextView mycollCartGoodsItemTitle;
        TextView mycollCartGoodsSprice,marketPrice;
        LinearLayout item;

    }

}
