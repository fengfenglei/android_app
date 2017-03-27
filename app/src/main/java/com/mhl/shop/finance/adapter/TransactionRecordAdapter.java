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

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：交易記錄
 */
public class TransactionRecordAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<TransactionRecord.RowsBean> alist;

    public TransactionRecordAdapter(Context context, List<TransactionRecord.RowsBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.record_item, null);
            holder = new ViewHolder();
            holder.remark = (TextView) convertView.findViewById(R.id.remark);
            holder.money_type = (TextView) convertView.findViewById(R.id.money_type);
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
                // 传递标记登陆成功后返回到我的
                intent.putExtra("TransactionRecordId", alist.get(position).getPkId()+"");
                intent.putExtra("typedetails", "TransactionRecord");
                context.startActivity(intent);

            }
        });
        holder.remark.setText(alist.get(position).getRemark()+"");
        holder.money_type.setText(alist.get(position).getFundTypeStr());
        if(alist.get(position).getChangeAmountStr().indexOf("+") != -1)
        {
            holder.money.setTextColor(context.getResources().getColor(R.color.text_color_red));
        }else{
            holder.money.setTextColor(context.getResources().getColor(R.color.grenn));
        }
        holder.money.setText(alist.get(position).getChangeAmountStr()+"");
        return convertView;
    }

    class ViewHolder
    {
        TextView remark;
        TextView money_type;
        TextView money;
        LinearLayout item;
    }

}
