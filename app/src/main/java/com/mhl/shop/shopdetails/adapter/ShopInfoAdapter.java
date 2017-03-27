package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.mhl.shop.R;
import com.mhl.shop.shopdetails.been.WantInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * lff
 *
 */

public class ShopInfoAdapter extends RecyclerView.Adapter<ShopInfoAdapter.MyViewHolder> {

    private Context context;
    private List<WantInfo> data;
    private OnItemShopClickListener listener;


    public ShopInfoAdapter(Context context, List<WantInfo> data, OnItemShopClickListener listener){
        this.context = context;
        this.data = data;
        this.listener = listener;
    }




    @Override
    public ShopInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.goosd_info, parent, false);
        return new ShopInfoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopInfoAdapter.MyViewHolder holder, final int position_da) {

        holder.name.setText(data.get(position_da).getAttrName());
        List<String> list1 = new ArrayList<>(data.get(position_da).getAttrList());
        holder.flowLayout.setList(list1);
        holder.flowLayout.setIndexItemSelected(0);
        listener.onItemShopClick(true,position_da,list1.get(0),data.get(position_da).getAttrName());
//        holder.shop_ly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("goodsid", data.get(position).getGoodsId());
//                intent.setClass(context, GoodsDetailsActivity.class);
//                context.startActivity(intent);
//            }
//        });
//单选
        holder.flowLayout.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                listener.onItemShopClick(false, position_da,text,data.get(position_da).getAttrName());
            }
        });



    }
    public interface OnItemShopClickListener {
        void onItemShopClick(boolean b, int position, String s, String text);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private MultiLineChooseLayout flowLayout;


        private LinearLayout shop_ly;
        public MyViewHolder(View view)
        {
            super(view);
            flowLayout = (MultiLineChooseLayout) view.findViewById(R.id.flowLayout);
            name = (TextView) view.findViewById(R.id.name);
;
        }
    }




}
