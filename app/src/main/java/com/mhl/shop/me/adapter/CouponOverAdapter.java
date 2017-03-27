package com.mhl.shop.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.been.Coupon;
import com.mhl.shop.utils.ToolsUtils;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：地址列表
 */
public class CouponOverAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Coupon.RowsBean> alist;

    public CouponOverAdapter(Context context, List<Coupon.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_over_item, null);
            holder = new ViewHolder();
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.man_money = (TextView) convertView.findViewById(R.id.man_money);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.money.setText((int)alist.get(position).getCouponAmount()+"");
        holder.man_money.setText("卖货郎商城通用满"+((int)alist.get(position).getCouponOrderAmount())+""+"元可用");
        holder.time.setText("有效期："+ (ToolsUtils.dateToStamp(alist.get(position).getStartTime())+"")+"~"+ (ToolsUtils.dateToStamp(alist.get(position).getEndTime())+""));
        return convertView;
    }

    class ViewHolder
    {
        TextView money;
        TextView man_money;
        TextView time;
    }

}
