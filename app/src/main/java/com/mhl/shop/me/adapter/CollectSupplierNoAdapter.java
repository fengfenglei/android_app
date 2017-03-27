package com.mhl.shop.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.BottomMenuDialog;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.been.CollectSupplier;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.Urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：地址列表
 */
public class CollectSupplierNoAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<CollectSupplier.RecommendSuppliers> alist;
    BottomMenuDialog d5;

    public CollectSupplierNoAdapter(Context context, List<CollectSupplier.RecommendSuppliers> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.myupplier_no_item, null);
            holder = new ViewHolder();
            holder.mycollCartGoodsIcon = (CircleImageView) convertView.findViewById(R.id.mycoll_cart_goods_icon);
            holder.mycollCartGoodsItemTitle = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_item_title);
            holder.mycollCartGoodsSprice = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_sprice);
            holder.manyi = (TextView) convertView.findViewById(R.id.manyi);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+alist.get(position).getIdentificationId()+"-80-80.jpg").into(holder.mycollCartGoodsIcon);

        holder.mycollCartGoodsItemTitle.setText(alist.get(position).getSupplierName());
        holder.manyi.setText("商家满意度"+"95%");
        holder.mycollCartGoodsSprice.setText("所在地区"+alist.get(position).getProvince()+alist.get(position).getCity()+alist.get(position).getDistrict());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent(context,SupplierActivity.class);
                intent1.putExtra("supplierId", alist.get(position).getPkId());
                context.startActivity(intent1);
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        CircleImageView mycollCartGoodsIcon;
        TextView mycollCartGoodsItemTitle;
        TextView manyi;
        LinearLayout item;
        TextView mycollCartGoodsSprice;

    }

}
