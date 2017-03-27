package com.mhl.shop.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.me.been.CollectShop;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-5 17:03
 * 描述：
 */
public class CollectNoShopAdapter extends  RecyclerView.Adapter<CollectNoShopAdapter.ItemViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CollectShop.ChoicenessGoods> alist;

    public CollectNoShopAdapter(Context context,List<CollectShop.ChoicenessGoods> list) {
        mContext = context;
        alist = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_no_shop,parent,false);
        ItemViewHolder vh = new ItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.home_item_tv_title_1.setText(alist.get(position).getGoodsName());
        holder.home_item_tv_money_1.setText("¥"+(alist.get(position).getShoppingPrice())+"");
        Glide.with(mContext).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getPicId()+"-200-200.jpg").into(holder.home_item_iv_image_1);
    }


    @Override
    public int getItemCount() {
        return alist.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView		home_item_tv_money_1;
        TextView		home_item_tv_title_1;
        ImageView		home_item_iv_image_1;

        public ItemViewHolder(View view) {
            super(view);
            home_item_iv_image_1 = (ImageView) view.findViewById(R.id.home_item_iv_image_1);
            home_item_tv_title_1 = (TextView) view.findViewById(R.id.home_item_tv_title_1);
            home_item_tv_money_1 = (TextView) view.findViewById(R.id.home_item_tv_money_1);
              }
    }
}

