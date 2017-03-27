package com.mhl.shop.homepage.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.shopcart.MyLinearLayoutManger;
import com.mhl.shop.shopcart.MyRecyclerView;
import com.mhl.shop.shopcart.NewCartList;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-10 17:45
 * 描述：
 */
public class MyomAdapter extends  RecyclerView.Adapter<MyomAdapter.ItemViewHolder> {

private Context mContext;
private LayoutInflater mInflater;
private List<NewCartList>  alist;

public MyomAdapter(Context context, List<NewCartList> list) {
        mContext = context;
        alist = list;
        mInflater = LayoutInflater.from(mContext);
        }

@Override
public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_cart_manage,parent,false);
    ItemViewHolder vh = new ItemViewHolder(view);
    return vh;
        }

@Override
public void onBindViewHolder(ItemViewHolder holder, int position) {
    holder.shopping_cart_shopIcon.setImageResource(R.drawable.obligation_shop_icon);
    holder.shopping_cart_shopName.setText(alist.get(position).getSupplierId()+"");
    MyLinearLayoutManger mLayoutManager = new MyLinearLayoutManger(mContext);
    holder.shopping_cart_shopItem.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    holder.shopping_cart_shopItem.setHasFixedSize(true);
//创建并设置Adapter
//    MyAdapter mAdapter = new MyAdapter(mContext,alist.get(position).getGoodsList());
//    holder.shopping_cart_shopItem.setAdapter(mAdapter);

}


@Override
public int getItemCount() {
        return alist.size();
        }

class ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView	shopping_cart_shopIcon;
    TextView	shopping_cart_shopName;
    MyRecyclerView shopping_cart_shopItem;
    CheckBox	shopping_cart_shopChose;
    LinearLayout abate;

    public ItemViewHolder(View view) {
        super(view);
       shopping_cart_shopIcon = (ImageView) view.findViewById(R.id.shopping_cart_shop_icon);
        shopping_cart_shopName = (TextView) view.findViewById(R.id.shopping_cart_shop_name);
        shopping_cart_shopItem = (MyRecyclerView) view.findViewById(R.id.shopping_cart_goods_item);
        shopping_cart_shopChose = (CheckBox) view.findViewById(R.id.shopping_cart_item_all_chose);
       abate=(LinearLayout) view.findViewById(R.id.abate);    }
}
}
