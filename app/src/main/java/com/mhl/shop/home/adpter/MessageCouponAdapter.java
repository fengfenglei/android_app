package com.mhl.shop.home.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.homepage.bean.Message;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.utils.ToolsUtils;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-19 16:20
 * 描述：优惠券(和账户通知共用)
 */
public class MessageCouponAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Message> alist;

    public MessageCouponAdapter(Context context, List<Message> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.message_coupon_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(ToolsUtils.dateToStampLit(alist.get(position).getSendTime()));
        holder.title.setText(alist.get(position).getNoticeTitle());
        holder.content.setText(alist.get(position).getNoticeMsg());

        return convertView;
    }

    class ViewHolder
    {
        TextView content;
        TextView title;
        TextView time;
    }

}

