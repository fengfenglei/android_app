package com.mhl.shop.home.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.homepage.bean.Message;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.LogisticsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-19 18:24
 * 描述：消息物流
 */
public class MessageLogisticsAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Message> alist;

    public MessageLogisticsAdapter(Context context, List<Message> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.message_logistics_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getMsg4()+"-200-200.jpg").into(holder.icon);

        holder.time.setText(ToolsUtils.dateToStampLit(alist.get(position).getSendTime()));
        holder.title.setText(alist.get(position).getNoticeTitle());
        holder.content.setText(alist.get(position).getNoticeMsg());
        holder.number.setText(alist.get(position).getMsg1()+"  "+alist.get(position).getMsg2());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(context,LogisticsActivity.class);
                intent10.putExtra("pkId", alist.get(position).getMsg3());
                intent10.putExtra("type","1");
                context.startActivity(intent10);
            }
        });
        return convertView;
    }

    class ViewHolder
    {
        TextView content;
        TextView title;
        TextView time;
        TextView number;
        ImageView icon;
        LinearLayout item;
    }

}

