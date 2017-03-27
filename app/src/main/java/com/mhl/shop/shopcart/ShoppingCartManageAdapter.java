package com.mhl.shop.shopcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-2 11:18
 * 描述：
 */
public class ShoppingCartManageAdapter extends BaseAdapter {

    private  Context context;
    private   List<NewCartList> alist;
    private  CartShopAdapter cadapter;

    public ShoppingCartManageAdapter(Context context, List<NewCartList> list) {
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
        final NewCartList cartShop = alist.get(position);

        View view = convertView;
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_shopping_cart_manage, null);
            holder.shopping_cart_shopIcon = (ImageView) view.findViewById(R.id.shopping_cart_shop_icon);
            holder.shopping_cart_shopName = (TextView) view.findViewById(R.id.shopping_cart_shop_name);
            holder.shopping_cart_shopItem = (MyRecyclerView) view.findViewById(R.id.shopping_cart_goods_item);
            holder.shopping_cart_shopChose = (CheckBox) view.findViewById(R.id.shopping_cart_item_all_chose);
            holder.abate=(LinearLayout) view.findViewById(R.id.abate);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
//        cadapter = new CartShopAdapter(context,cartShop.getGoodsList(),holder.shopping_cart_shopChose);
//        holder.shopping_cart_shopItem.setAdapter(cadapter);
//创建默认的线性LayoutManager
        MyLinearLayoutManger  mLayoutManager = new MyLinearLayoutManger(context);
        holder.shopping_cart_shopItem.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        holder.shopping_cart_shopItem.setHasFixedSize(true);
//创建并设置Adapter
//        MyAdapter mAdapter = new MyAdapter(context,cartShop.getGoodsList());
//        holder.shopping_cart_shopItem.setAdapter(mAdapter);
        holder.shopping_cart_shopIcon.setImageResource(R.drawable.obligation_shop_icon);
        holder.shopping_cart_shopName.setText(cartShop.getSupplierId());

        return view;
    }

    class ViewHolder
    {
        ImageView	shopping_cart_shopIcon;
        TextView	shopping_cart_shopName;
        MyRecyclerView shopping_cart_shopItem;
        CheckBox	shopping_cart_shopChose;
        LinearLayout abate;
    }

}
