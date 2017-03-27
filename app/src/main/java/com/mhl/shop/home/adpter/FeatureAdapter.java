package com.mhl.shop.home.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.home.FeatureActivity;
import com.mhl.shop.home.been.Feature;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-22 10:45
 * 描述：
 */
public class FeatureAdapter  extends BaseAdapter {

    private Context context;
    private  List<Feature.RowsBean> strings;
    public static int mPosition;

    public FeatureAdapter(Context context, List<Feature.RowsBean> strings){
        this.context =context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(context).inflate(R.layout.feature_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        mPosition = position;
        tv.setText(strings.get(position).getTitle1());
        if (position == FeatureActivity.mPosition) {
            convertView.setBackgroundResource(R.drawable.yxyp_bj);
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }
        return convertView;
    }
}

