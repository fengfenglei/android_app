package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.shopdetails.been.Submit;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-13 17:13
 * 描述：填写订单
 */
public class SubmitOrderAdapter extends RecyclerView.Adapter<SubmitOrderAdapter.MyViewHolder> {
    private Context context;
    private List<Submit.UserGoodsCartBean> list;
    private String way_str;//快递方式
    private OnItemClickListener listener;
    public SubmitOrderAdapter(Context context, List<Submit.UserGoodsCartBean> data,  OnItemClickListener listener){
        this.context = context;
        this.list = data;
        this.listener = listener;
    }

    @Override
    public SubmitOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.
//                from(parent.getContext()).
//                inflate(R.layout.submit_item,parent,false);
//        return new MyViewHolder(itemView);
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.submit_item, parent, false);
        return new SubmitOrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubmitOrderAdapter.MyViewHolder holder, final int position) {
        if (position > 0) {
            if (Long.valueOf(list.get(position).getSupplierId()).intValue() == (Long.valueOf(list.get(position - 1).getSupplierId())).intValue()) {
                holder.head_view.setVisibility(View.GONE);
            } else {
                holder.head_view.setVisibility(View.VISIBLE);
            }
        }else {
            holder.head_view.setVisibility(View.VISIBLE);
            holder.foot_view.setVisibility(View.VISIBLE);
            listener.onItemClick(position, list.get(position).getSupplierId()+"_"+"");
        }
        if(list.size()>position+1){
            if (Long.valueOf(list.get(position).getSupplierId()).intValue() != (Long.valueOf(list.get(position + 1).getSupplierId())).intValue()) {
                holder.foot_view.setVisibility(View.VISIBLE);
                listener.onItemClick(position+1, list.get(position+1).getSupplierId()+"_"+"");
            } else {
                holder.foot_view.setVisibility(View.GONE);
            }
        }
         Double num = Double.valueOf(0);//同一个供应商所有的价格
         int all_count =0;//同一个供应商所有的商品数量
         Double logistics_money = Double.valueOf(0);//同一个供应商所有的运费价格
        for (int j = 0; j < list.size(); j++) {
            if (list.get(position).getSupplierId().equals(list.get(j).getSupplierId())){
                num +=  list.get(j).getCurrentPrice()*list.get(j).getGoodsCount();
            }

        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(position).getSupplierId().equals(list.get(j).getSupplierId())){
                all_count +=  list.get(j).getGoodsCount();
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(position).getSupplierId().equals(list.get(j).getSupplierId())){
                if(!Double.isNaN(list.get(j).getFreightMoney())){
                    logistics_money +=  list.get(j).getFreightMoney();
                }

            }
        }
        holder.all_count.setText("共"+all_count+""+"件，小计：");
        holder.price.setText("¥"+ToolsUtils.Tow(list.get(position).getCurrentPrice()+""));
        if(logistics_money>0) {
            holder.write_order_shop_shop_money.setText("¥" + ToolsUtils.Tow(num + logistics_money+ ""));
        }else {
            holder.write_order_shop_shop_money.setText("¥" + ToolsUtils.Tow(num+""));
        }
        holder.write_order_supplier_name.setText(list.get(position).getSupplierIdName());
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position).getGoodsPicId()+"-200-200.jpg"
        ).placeholder(R.drawable.icon_bg).into(holder.write_order_shop_icon);

        holder.shop_name.setText(list.get(position).getGoodsIdName());
        holder.guige.setText(list.get(position).getGoodsSpecInfo());
        holder.shop_count.setText("x"+list.get(position).getGoodsCount()+"");

        if (list.get(position).getExpressWay()==1){
            way_str="平邮：";
        }else  if (list.get(position).getExpressWay()==2){
            way_str="快递：";
        }else  if (list.get(position).getExpressWay()==3){
            way_str="EMS：";
        }
        if(logistics_money>0){
        holder.write_order_shop_logistics_money.setText(way_str+ToolsUtils.Tow(logistics_money+""));
        }else {
            holder.write_order_shop_logistics_money.setText("包邮");
        }
//        holder.write_order_shop_message.requestFocus();
//        holder.write_order_shop_message.setFocusableInTouchMode(true);
        holder.write_order_shop_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onItemClick(position, list.get(position).getSupplierId()+"_"+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClick(int index, String newPrice);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView shop_name,price,guige,price_lit,shop_count,write_order_supplier_name,write_order_shop_shop_money,all_count,write_order_shop_logistics_money;
        private RelativeLayout head_view;
        private LinearLayout foot_view;
        private ImageView write_order_shop_icon,write_order_supplier_icon;
        private EditText write_order_shop_message;
        public MyViewHolder(View view)
        {
            super(view);
            head_view= (RelativeLayout) view.findViewById(R.id.head_view);//头布局
            foot_view= (LinearLayout) view.findViewById(R.id.foot_view);//尾部布局
            write_order_shop_icon = (ImageView) view.findViewById(R.id.write_order_shop_icon);
            write_order_supplier_icon = (ImageView) view.findViewById(R.id.write_order_supplier_icon);
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            price = (TextView) view.findViewById(R.id.price);
            write_order_shop_logistics_money = (TextView) view.findViewById(R.id.write_order_shop_logistics_money);
            all_count= (TextView) view.findViewById(R.id.all_count);
            write_order_shop_shop_money = (TextView) view.findViewById(R.id.write_order_shop_shop_money);
            guige = (TextView) view.findViewById(R.id.guige);
            write_order_shop_message = (EditText) view.findViewById(R.id.write_order_shop_message);
            price_lit = (TextView) view.findViewById(R.id.price_lit);
            shop_count = (TextView) view.findViewById(R.id.shop_count);
            write_order_supplier_name = (TextView) view.findViewById(R.id.write_order_supplier_name);
        }
    }
}
