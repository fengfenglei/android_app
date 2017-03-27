package com.mhl.shop.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.me.been.OrderLogistics;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-10-13 14:46
 * 描述：物流日志
 */
public class CheckOrderLogisticsAdapter extends BaseAdapter
{
    private Context context;
    private List<OrderLogistics.ExpressProgressBean> list;

    public CheckOrderLogisticsAdapter(Context context, List<OrderLogistics.ExpressProgressBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        View view = convertView;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_check_logistics, null);
            holder.check_Logistics_position = (TextView) view.findViewById(R.id.check_logistics_position);
            holder.check_Logistics_time = (TextView) view.findViewById(R.id.check_logistics_time);
            holder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        if (position==0)
        {
            holder.check_Logistics_position.setText(list.get(position).getContext());
            holder.check_Logistics_position.setTextColor(context.getResources().getColor(R.color.text_color_red));
            holder.img.setBackgroundResource(R.drawable.log_red);
        }
        else
        {
            holder.check_Logistics_position.setText(list.get(position).getContext());
            holder.check_Logistics_position.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.img.setBackgroundResource(R.drawable.log_gray);
        }
        holder.check_Logistics_time.setText(list.get(position).getFtime());
        return view;
    }

    class ViewHolder
    {
        TextView check_Logistics_position;
        TextView check_Logistics_time;
        ImageView img;
    }
}

