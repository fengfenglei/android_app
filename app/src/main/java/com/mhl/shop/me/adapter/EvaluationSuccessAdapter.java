package com.mhl.shop.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.OrderEvaluateActivity;
import com.mhl.shop.me.been.Order;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：地址列表
 */
public class EvaluationSuccessAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Order.RowsBean> alist;

    public EvaluationSuccessAdapter(Context context, List<Order.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.evaluation_success, null);
            holder = new ViewHolder();
            holder.shop_image = (ImageView) convertView.findViewById(R.id.shop_image);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.order_evluation = (Button) convertView.findViewById(R.id.order_evluation);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getGoodsIdPic()+"-200-200.jpg"
        ).into(holder.shop_image);
        holder.shop_name.setText(alist.get(position).getGoodsIdName());

        //立即评价
        holder.order_evluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context,OrderEvaluateActivity.class);
                intent1.putExtra("item_image", alist.get(position).getGoodsIdPic());
                intent1.putExtra("shop_name", alist.get(position).getGoodsIdName());
                intent1.putExtra("shop_count", alist.get(position).getGoodsCount()+"");
                intent1.putExtra("guige", alist.get(position).getGoodsSpec());
                intent1.putExtra("money", alist.get(position).getGoodsIdPrice()+"");
                intent1.putExtra("orderDetailId", alist.get(position).getPkId()+"");
                intent1.putExtra("goodsId", alist.get(position).getGoodsId());
                context.startActivity(intent1);
            }
        });

    return convertView;
    }

    class ViewHolder
    {
        TextView shop_name;
        Button order_evluation;
        ImageView shop_image;
    }

}
