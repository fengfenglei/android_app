package com.mhl.shop.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.been.MyEctensionCenter;
import com.mhl.shop.utils.ToolsUtils;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：推广列表
 */
public class MyEctensionCenterAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<MyEctensionCenter> alist;

    public MyEctensionCenterAdapter(Context context, List<MyEctensionCenter> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.my_center_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(alist.get(position).getLoginName());
        holder.time.setText(ToolsUtils.dateToStampLit(alist.get(position).getRegisterTime()));
        return convertView;
    }

    class ViewHolder
    {
        TextView name;
        TextView time;
    }

}
