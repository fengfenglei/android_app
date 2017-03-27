package com.mhl.shop.finance.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.finance.TransactionDetailsActivity;
import com.mhl.shop.finance.been.TransactionRecord;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.utils.ToolsUtils;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：赠送金额
 */
public class GiftBalanceAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<TransactionRecord.RowsBean> alist;

    public GiftBalanceAdapter(Context context, List<TransactionRecord.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.giftbalance_item, null);
            holder = new ViewHolder();
            holder.counet = (TextView) convertView.findViewById(R.id.counet);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionDetailsActivity.class);
                intent.putExtra("TransactionRecordId", alist.get(position).getPkId()+"");
                intent.putExtra("typedetails", "TransactionRecord");
                context.startActivity(intent);

            }
        });

        holder.counet.setText(alist.get(position).getRemark()+"");
        holder.time.setText(ToolsUtils.dateToStampMonth(alist.get(position).getRecordTime()));
        holder.money.setText(alist.get(position).getChangeAmountStr()+"");
        if(alist.get(position).getChangeAmountStr().indexOf("+") != -1)
        {

            holder.money.setTextColor(context.getResources().getColor(R.color.text_color_red));
        }else {
            holder.money.setTextColor(context.getResources().getColor(R.color.grenn));
        }
        return convertView;
    }

    class ViewHolder
    {
        TextView counet;
        TextView money;
        TextView time;
        LinearLayout item;
    }

}
