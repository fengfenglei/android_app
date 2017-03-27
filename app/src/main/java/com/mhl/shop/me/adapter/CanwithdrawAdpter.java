package com.mhl.shop.me.adapter;

import android.app.Activity;
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

import butterknife.Bind;

/**
 * 作者：lff
 * 时间；2016-11-18 14:57
 * 描述：可体现金额
 */
public class CanwithdrawAdpter extends MyBaseAdpter {
    private final Activity context;
    private final List<TransactionRecord.RowsBean> alist;
    @Bind(R.id.order_id)
    TextView orderId;
    @Bind(R.id.money_type)
    TextView moneyType;
    @Bind(R.id.money)
    TextView money;

    public CanwithdrawAdpter(Activity context, List<TransactionRecord.RowsBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_canwithdraw_item, null);
            holder = new ViewHolder();
            holder.order_id = (TextView) view.findViewById(R.id.order_id);
            holder.money = (TextView) view.findViewById(R.id.money);
            holder.item = (LinearLayout) view.findViewById(R.id.item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
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
        holder.order_id.setText(alist.get(position).getRemark());
        if(alist.get(position).getChangeAmountStr().indexOf("+") != -1)
        {
            holder.money.setTextColor(context.getResources().getColor(R.color.text_color_red));
        }else{
            holder.money.setTextColor(context.getResources().getColor(R.color.grenn));
        }
        holder.money.setText(alist.get(position).getChangeAmountStr()+"");
        return view;
    }
    class ViewHolder
    {
        TextView		order_id;
        TextView        money;
        LinearLayout item;
    }

}
