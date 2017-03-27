package com.mhl.shop.finance.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.finance.ActivatedNoDetailsActivity;
import com.mhl.shop.finance.been.ActivatedNo;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.utils.ToolsUtils;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-18 14:57
 * 描述：待激活
 */
public class ActivatedNoAdpter extends MyBaseAdpter {
    private final Activity context;
    private final List<ActivatedNo> alist;

    public ActivatedNoAdpter(Activity context, List<ActivatedNo> list) {
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_activated_no_item, null);
            holder = new ViewHolder();
            holder.order_id = (TextView) view.findViewById(R.id.order_id);
            holder.money = (TextView) view.findViewById(R.id.money);
            holder.money_will= (TextView) view.findViewById(R.id.money_will);
            holder.item = (LinearLayout) view.findViewById(R.id.item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivatedNoDetailsActivity.class);
                intent.putExtra("ActivatedNoId", alist.get(position).getPkId()+"");
                context.startActivity(intent);
            }
        });
        holder.order_id.setText(alist.get(position).getOrderNo());
        holder.money_will.setText("¥"+ ToolsUtils.Tow(alist.get(position).getExpectedActivationMoney()+""));
        holder.money.setText("¥"+ToolsUtils.Tow(alist.get(position).getTotalPrice()+""));
        return view;
    }
    class ViewHolder
    {
        TextView		order_id;
        TextView        money;
        TextView        money_will;
        LinearLayout    item;
    }

}
