package com.mhl.shop.search.adapter;

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
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.search.been.ShopResult;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：商品搜索
 */
public class ShopSearchAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<ShopResult.GoodsInfosBean> alist;

    public ShopSearchAdapter(Context context, List<ShopResult.GoodsInfosBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_search_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                Intent intent1 = new Intent(context, SupplierActivity.class);
//                intent1.putExtra("supplierId", alist.get(position).getPkId());
//                context.startActivity(intent1);
                Intent intent = new Intent();
                intent.putExtra("goodsid", alist.get(position).getPkId());
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        Glide.with(context).load(Urls.URL_PHOTO + "/file/v3/download-" + alist.get(position).getPicId() + "-180-180.jpg").placeholder(R.drawable.icon_bg).into(holder.icon);

        holder.shopName.setText(alist.get(position).getGoodsName());

        holder.priceBig.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.price.setText("¥" + ToolsUtils.Tow(alist.get(position).getShoppingPrice()+""));
        holder.priceBig.setText("¥" + ToolsUtils.Tow(alist.get(position).getMarketPrice()+""));
        holder.salesVolume.setText("累积销量"+alist.get(position).getPurchaseTimes()+"件");
        holder.viewCount.setText(alist.get(position).getVisitTimes()+"人浏览");
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.shop_name)
        TextView shopName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.price_big)
        TextView priceBig;
        @Bind(R.id.sales_volume)
        TextView salesVolume;
        @Bind(R.id.view_count)
        TextView viewCount;
        @Bind(R.id.item)
        LinearLayout item;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
