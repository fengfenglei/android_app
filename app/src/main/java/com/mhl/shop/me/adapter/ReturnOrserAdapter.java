package com.mhl.shop.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.OrderDetailActivity;
import com.mhl.shop.me.ReturnDetailActivity;
import com.mhl.shop.me.been.Order;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：地址列表
 */
public class ReturnOrserAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Order.RowsBean> alist;

    public ReturnOrserAdapter(Context context, List<Order.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.return_item, null);
            holder = new ViewHolder();
            holder.write_order_supplier_name = (TextView) convertView.findViewById(R.id.write_order_supplier_name);
            holder.write_order_supplier_icon = (CircleImageView) convertView.findViewById(R.id.write_order_supplier_icon);
            holder.order_state = (TextView) convertView.findViewById(R.id.order_state);
            holder.shop_image = (ImageView) convertView.findViewById(R.id.shop_image);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.guige = (TextView) convertView.findViewById(R.id.guige);
            holder.big_money = (TextView) convertView.findViewById(R.id.big_money);
            holder.shop_count = (TextView) convertView.findViewById(R.id.shop_count);
            holder.order_detail = (Button) convertView.findViewById(R.id.order_detail);
            holder.return_detail = (Button) convertView.findViewById(R.id.return_detail);
            holder.head_supplier_view = (LinearLayout) convertView.findViewById(R.id.head_supplier_view);
            holder.shop_ly= (LinearLayout) convertView.findViewById(R.id.shop_ly);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.write_order_supplier_name.setText(alist.get(position).getSupplierIdName());

        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getIdentificationId()+"-100-100.jpg"
        ).into(holder.write_order_supplier_icon);
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getGoodsIdPic()+"-200-200.jpg"
        ).into(holder.shop_image);
        holder.shop_name.setText(alist.get(position).getGoodsIdName());
        holder.big_money.setText("¥"+ToolsUtils.Tow(alist.get(position).getGoodsIdPrice()+""));
        holder.guige.setText(alist.get(position).getGoodsSpec());
        holder.shop_count.setText("x"+alist.get(position).getGoodsCount()+"");
if(alist.get(position).getOrderStatus()==31){
    holder.order_state.setText("待供应商审核");
}else if(alist.get(position).getOrderStatus()==32){
    holder.order_state.setText("待平台审核");
}else if(alist.get(position).getOrderStatus()==38){
    holder.order_state.setText("供应商拒绝退款");
}else if(alist.get(position).getOrderStatus()==39){
    holder.order_state.setText("取消退款");
}else if(alist.get(position).getOrderStatus()==41){
    holder.order_state.setText("待供应商审核");
}else if(alist.get(position).getOrderStatus()==42){
    holder.order_state.setText("待会员退货");
}else if(alist.get(position).getOrderStatus()==43){
    holder.order_state.setText("待供应商收货");
}else if(alist.get(position).getOrderStatus()==44){
    holder.order_state.setText("待平台审核");
}else if(alist.get(position).getOrderStatus()==48){
    holder.order_state.setText("供应商拒绝退货退款");
}else if(alist.get(position).getOrderStatus()==49){
    holder.order_state.setText("取消退货退款");
}else if(alist.get(position).getOrderStatus()==61){
    holder.order_state.setText("退款成功");
}else if(alist.get(position).getOrderStatus()==62){
    holder.order_state.setText("退货退款完成");
}
        //跳转到商品详情
        holder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("PkId", alist.get(position).getPkId());//PkId
                    intent.setClass(context, OrderDetailActivity.class);
                    context.startActivity(intent);
            }
        });
        //跳转到供应商
        holder.head_supplier_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context,SupplierActivity.class);
                intent1.putExtra("supplierId", alist.get(position).getSupplierId());
                context.startActivity(intent1);

            }
        });
        //跳转到退款详情
        holder.return_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(context,ReturnDetailActivity.class);
                intent7.putExtra("pkId",  alist.get(position).getPkId());
                intent7.putExtra("supplierId",  alist.get(position).getSupplierId());
                intent7.putExtra("goodsId",  alist.get(position).getGoodsId());
                context.startActivity(intent7);

            }
        });
        //跳转到退款详情
        holder.shop_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(context,ReturnDetailActivity.class);
                intent7.putExtra("pkId",  alist.get(position).getPkId());
                intent7.putExtra("supplierId",  alist.get(position).getSupplierId());
                intent7.putExtra("goodsId",  alist.get(position).getGoodsId());
                context.startActivity(intent7);

            }
        });
    return convertView;
    }

    class ViewHolder
    {
        TextView money,write_order_supplier_name,order_state,shop_name,guige,big_money,shop_count,order_money;
        Button order_detail,return_detail;
        CircleImageView write_order_supplier_icon;
        ImageView shop_image;
        LinearLayout head_supplier_view,shop_ly;
    }

}
