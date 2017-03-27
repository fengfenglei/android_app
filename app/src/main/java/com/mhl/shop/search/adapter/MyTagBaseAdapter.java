package com.mhl.shop.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mhl.shop.R;
import com.mhl.shop.search.been.HotShop;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-2-24 17:06
 * 描述：
 */
public class MyTagBaseAdapter  extends BaseAdapter {

    private Context mContext;
    private List<HotShop> mList;

    public MyTagBaseAdapter(Context context, List<HotShop> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
//        return mList.get(position).getKeyword();
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagmyview, null);
            holder = new ViewHolder();
            holder.tagBtn = (Button) convertView.findViewById(com.fyales.tagcloud.library.R.id.tag_btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tagBtn.setText(mList.get(position).getKeyword());
        if(mList.get(position).getEnableFlag()==1){
            holder.tagBtn.setBackgroundResource(R.drawable.all_shopcomment_normal);
            holder.tagBtn.setTextColor(Color.parseColor("#ff5f5f"));
        }else {
            holder.tagBtn.setBackgroundResource(R.drawable.rect_black);
            holder.tagBtn.setTextColor(Color.parseColor("#666666"));

        }
        return convertView;
    }

    static class ViewHolder {
        Button tagBtn;
    }
}
