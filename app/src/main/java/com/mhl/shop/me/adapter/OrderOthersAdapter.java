package com.mhl.shop.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.me.been.OrderOthers;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-13 17:13
 * 描述：订单详情
 */
public class OrderOthersAdapter extends RecyclerView.Adapter<OrderOthersAdapter.MyViewHolder> {
    private Context context;
    private List<OrderOthers.OrderDetailBean> list;

    public OrderOthersAdapter(Context context, List<OrderOthers.OrderDetailBean> data){
        this.context = context;
        this.list = data;
    }

    @Override
    public OrderOthersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.order_others_item, parent, false);
        return new OrderOthersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderOthersAdapter.MyViewHolder holder, final int position) {
        if (position > 0) {
            if (Long.valueOf(list.get(position).getSupplierId()).intValue() == (Long.valueOf(list.get(position - 1).getSupplierId())).intValue()) {
                holder.head_view.setVisibility(View.GONE);
            } else {
                holder.head_view.setVisibility(View.VISIBLE);
            }

        }else {
            holder.head_view.setVisibility(View.VISIBLE);
            holder.foot_view.setVisibility(View.VISIBLE);
        }
        if(list.size()>position+1){
            if (Long.valueOf(list.get(position).getSupplierId()).intValue() != (Long.valueOf(list.get(position + 1).getSupplierId())).intValue()) {
                holder.foot_view.setVisibility(View.VISIBLE);
            } else {
                holder.foot_view.setVisibility(View.GONE);
            }
        }
         Double num = Double.valueOf(0);//同一个供应商所有的价格
        for (int j = 0; j < list.size(); j++) {
            if ((list.get(position).getSupplierId().equals(list.get(j).getSupplierId()))){
                num +=  list.get(j).getTotalPrice();
            }

        }
        Double freight = Double.valueOf(0);//同一个供应商所有的运费

        for (int j = 0; j < list.size(); j++) {
            if (list.get(position).getSupplierId().equals(list.get(j).getSupplierId())){
                freight +=  list.get(j).getFreightMoney();
            }

        }
        holder.price.setText("¥"+ToolsUtils.Tow(list.get(position).getGoodsIdPrice()+""));
        holder.shop_count.setText("x"+list.get(position).getGoodsCount()+"");

        holder.write_order_supplier_name.setText(list.get(position).getSupplierIdName());

        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position).getGoodsIdPic()+"-200-200.jpg"
        ).into(holder.write_order_shop_icon);
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position).getIdentificationId()+"-200-200.jpg"
        ).into(holder.write_order_supplier_icon);
        holder.shop_name.setText(list.get(position).getGoodsIdName());
        holder.guige.setText(list.get(position).getGoodsSpec());
        if(!TextUtils.isEmpty(list.get(position).getFreightMoney()+"")){
            holder.freightMoney.setText("¥"+ToolsUtils.Tow(freight+""));
        }else {
            holder.freightMoney.setText("包邮");
        }

        holder.all_money.setText("¥"+ToolsUtils.Tow(num+""));
        if(list.get(position).getLeaveMessage()==null || list.get(position).getLeaveMessage().equals("")){
            holder.liuyan.setText("无");
        }else {
            holder.liuyan.setText(list.get(position).getLeaveMessage());

        }

        holder.head_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(context,SupplierActivity.class);
                intent2.putExtra("supplierId", list.get(position).getSupplierId());
                context.startActivity(intent2);
            }
        });
        holder.lit_item_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context,GoodsDetailsActivity.class);
                intent1.putExtra("goodsid", list.get(position).getGoodsId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView shop_name,price,guige,freightMoney,shop_count,write_order_supplier_name,all_money,liuyan;
        private RelativeLayout head_view;
        private LinearLayout foot_view,lit_item_ly;
        private ImageView write_order_shop_icon,write_order_supplier_icon;
        public MyViewHolder(View view)
        {
            super(view);
            head_view= (RelativeLayout) view.findViewById(R.id.head_view);//头布局
            foot_view= (LinearLayout) view.findViewById(R.id.foot_view);//尾部布局
            lit_item_ly= (LinearLayout) view.findViewById(R.id.lit_item_ly);
            write_order_shop_icon = (ImageView) view.findViewById(R.id.write_order_shop_icon);
            write_order_supplier_icon = (ImageView) view.findViewById(R.id.write_order_supplier_icon);
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            price = (TextView) view.findViewById(R.id.price);
            freightMoney = (TextView) view.findViewById(R.id.freightMoney);
            shop_count = (TextView) view.findViewById(R.id.shop_count);
            all_money = (TextView) view.findViewById(R.id.all_money);
            guige = (TextView) view.findViewById(R.id.guige);
            liuyan = (TextView) view.findViewById(R.id.liuyan);
            write_order_supplier_name = (TextView) view.findViewById(R.id.write_order_supplier_name);
        }
    }
}
