package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.ViewPagerActivity;
import com.mhl.shop.utils.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-12 16:33
 * 描述：
 */
public class GridViewAdapter  extends BaseAdapter {
    private final Context context;
    private final List<String> list;
    public GridViewAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else
        {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 跳转到查看大图界面
                ArrayList<String> ny=new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    ny.add(list.get(i));
                }

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("mainImg", ny);

                context.startActivity(intent);
//                context.overridePendingTransition(R.anim.activity_zoomin, R.anim.activity_zoomout);

            }
        });

        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position)+"-200-200.jpg").placeholder(R.drawable.icon_bg).into(holder.icon);
        return convertView;

    }
        class ViewHolder
        {
            ImageView  icon;

        }
}