package com.mhl.shop.shopcart;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：购物车商品列表
 */
public class CartShopAdapter extends MyBaseAdpter {

    private  Context context;
    private  List<Cart>   alist;

    public CartShopAdapter(Context context, List<Cart> list, CheckBox shopping_cart_shopChose) {
        super();

        this.context = context;
        this.alist = list;
    }
    public CartShopAdapter() {
        super();
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
        View view = convertView;
        final ViewHolder holder;
        if (view == null)
        {            holder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.cartshop_item, null);
            holder.shopping_cart_goodsChose = (CheckBox) view.findViewById(R.id.shopping_cart_item_goods_chose);
            holder.shopping_cart_goodsIcon = (ImageView) view.findViewById(R.id.shopping_cart_goods_icon);
            holder.shopping_cart_goodsAdd = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_add);
            holder.shopping_cart_goodsSubtract = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_subtract);
            holder.abate_state = (LinearLayout) view.findViewById(R.id.abate_state);
            holder.shopping_cart_goodsTitle = (TextView) view.findViewById(R.id.shopping_cart_goods_item_title);
            holder.shopping_cart_SpecInfo = (TextView) view.findViewById(R.id.shopping_cart_goods_SpecInfo);
            holder.shopping_cart_goodsMoney = (TextView) view.findViewById(R.id.shopping_cart_goods_item_money);
            holder.shopping_cart_goodsOldMoney = (TextView) view.findViewById(R.id.shopping_cart_goods_item_old_money);
            holder.shopping_cart_goodsOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.shopping_cart_goodsNumber = (TextView) view.findViewById(R.id.shopping_cart_goods_num);
            holder.shopping_cart_goodsAdd_img = (ImageView) view.findViewById(R.id.shopping_cart_goods_add_img);
            holder.shopping_cart_goodsSubtract_img = (ImageView) view.findViewById(R.id.shopping_cart_goods_subtract_img);
                view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.shopping_cart_goodsTitle.setText(alist.get(position).getPkId()+"");
        holder.shopping_cart_goodsOldMoney.setText("¥"+(alist.get(position).getMarketPrice()*alist.get(position).getGoodsCount())+"");
        holder.shopping_cart_SpecInfo.setText(alist.get(position).getGoodsSpecInfo()+"");
        holder.shopping_cart_goodsMoney.setText("¥"+(alist.get(position).getCurrentPrice()*alist.get(position).getGoodsCount())+"");
        holder.shopping_cart_goodsNumber.setText(alist.get(position).getGoodsCount()+"");
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getGoodsPicId()+"-300-300.jpg").placeholder(R.drawable.icon_bg).into(holder.shopping_cart_goodsIcon);


        return view;
    }

    class ViewHolder
    {
        CheckBox		shopping_cart_goodsChose;
        ImageView		shopping_cart_goodsIcon;
        LinearLayout shopping_cart_goodsAdd;
        LinearLayout	shopping_cart_goodsSubtract;
        LinearLayout   abate_state;//是不是已失效产品
        TextView		shopping_cart_goodsTitle;
        TextView		shopping_cart_SpecInfo;
        TextView		shopping_cart_goodsMoney;
        TextView		shopping_cart_goodsOldMoney;
        TextView		shopping_cart_goodsNumber;
        ImageView		shopping_cart_goodsAdd_img;
        ImageView		shopping_cart_goodsSubtract_img;
    }

}
