package com.mhl.shop.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mhl.shop.R;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-24 14:37
 * 描述：
 */
public class ChooseAddressAdapter extends BaseAdapter
{
    private Context context;
    private List<String> list;



    public ChooseAddressAdapter(Context context, List<String> list) {
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

        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        View view=convertView;
        if(view==null)
        {
            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.adapter_choose_address, null);
            holder.address=(TextView) view.findViewById(R.id.address_item);
            view.setTag(holder);
        }else
        {
            holder=(ViewHolder) view.getTag();
        }
        holder.address.setText(list.get(position));
        return view;
    }

    class ViewHolder
    {
        TextView address;
    }


}

