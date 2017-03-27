package com.mhl.shop.home.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.home.MessageCouponActivity;
import com.mhl.shop.homepage.bean.Message;
import com.mhl.shop.main.MyBaseAdpter;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-19 16:20
 * 描述：
 */
public class MessageAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Message> alist;

    public MessageAdapter(Context context, List<Message> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final Intent intent = new Intent(context, MessageCouponActivity.class);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//通知类型(--0全部--1物流通知--2账户提醒--3货郎活动--4优惠券)
                if(alist.get(position).getMsgType()==1){
                    intent.putExtra("type", alist.get(position).getMsgType()+"");
                    context.startActivity(intent);
                }else if(alist.get(position).getMsgType()==2){
                intent.putExtra("type", alist.get(position).getMsgType()+"");
                context.startActivity(intent);
                }else if(alist.get(position).getMsgType()==3){
                }else if(alist.get(position).getMsgType()==4){
                    intent.putExtra("type", alist.get(position).getMsgType()+"");
                    context.startActivity(intent);
                }
            }
        });
        if(alist.get(position).getMsgType()==1){
            holder.title.setText("物流通知");
            holder.icon.setBackgroundResource(R.drawable.message_icon_logistics);
        }else if(alist.get(position).getMsgType()==2){
            holder.title.setText("账户提醒");
            holder.icon.setBackgroundResource(R.drawable.message_icon_remind);
        }else if(alist.get(position).getMsgType()==3){
            holder.title.setText("货郎活动");
        }else if(alist.get(position).getMsgType()==4){
            holder.title.setText("优惠券");
            holder.icon.setBackgroundResource(R.drawable.message_icon_coupons);
        }
if(alist.get(position).getNoticeMsg()!=null) {
    holder.content.setText(alist.get(position).getNoticeMsg());
}else {
    holder.content.setText("暂无消息");
}
        return convertView;
    }

    class ViewHolder
    {
        TextView content;
        TextView title;
        ImageView icon;
        LinearLayout item;
    }

}

