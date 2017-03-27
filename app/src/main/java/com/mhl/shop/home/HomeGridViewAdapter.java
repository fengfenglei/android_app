package com.mhl.shop.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-12 16:33
 * 描述：小郎精选
 */
public class HomeGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<Home.LangBean> list;
    public HomeGridViewAdapter(Context context, List<Home.LangBean> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.home_grid_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.marketPrice = (TextView) convertView.findViewById(R.id.marketPrice);
            holder.shoppingPrice = (TextView) convertView.findViewById(R.id.shoppingPrice);
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
                    ny.add(list.get(i).getShoppingPrice());
                }

//                Intent intent = new Intent(context, ViewPagerActivity.class);
//                intent.putExtra("position", position);
//                intent.putStringArrayListExtra("mainImg", ny);

//                context.startActivity(intent);

            }
        });
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+list.get(position).getShoppingPrice()+"-80-80.jpg").placeholder(R.drawable.icon_bg).into(holder.icon);
        holder.marketPrice.setText("¥"+ ToolsUtils.Tow(list.get(position).getMarketPrice()));
        holder.shoppingPrice.setText("¥"+ ToolsUtils.Tow(list.get(position).getShoppingPrice()));
        return convertView;

    }
        class ViewHolder
        {
            ImageView  icon;
            TextView shoppingPrice;
            TextView marketPrice;
        }
}