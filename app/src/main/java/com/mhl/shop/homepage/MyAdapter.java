package com.mhl.shop.homepage;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.shopcart.Cart;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-10 17:45
 * 描述：
 */
public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

private Context mContext;
private LayoutInflater mInflater;
private List<Cart>  alist;

public MyAdapter(Context context,List<Cart> list) {
        mContext = context;
    alist = list;
        mInflater = LayoutInflater.from(mContext);
        }

@Override
public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartshop_item,parent,false);
    ItemViewHolder vh = new ItemViewHolder(view);
    return vh;
        }

@Override
public void onBindViewHolder(ItemViewHolder holder, int position) {
    holder.shopping_cart_goodsTitle.setText(alist.get(position).getPkId()+"");
    holder.shopping_cart_goodsOldMoney.setText("¥"+(alist.get(position).getMarketPrice()*alist.get(position).getGoodsCount())+"");
    holder.shopping_cart_SpecInfo.setText(alist.get(position).getGoodsSpecInfo()+"");
    holder.shopping_cart_goodsMoney.setText("¥"+(alist.get(position).getCurrentPrice()*alist.get(position).getGoodsCount())+"");
    holder.shopping_cart_goodsNumber.setText(alist.get(position).getGoodsCount()+"");
    Glide.with(mContext).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getGoodsPicId()+"-200-200.jpg").placeholder(R.drawable.icon_bg).into(holder.shopping_cart_goodsIcon);
}


@Override
public int getItemCount() {
        return alist.size();
        }

class ItemViewHolder extends RecyclerView.ViewHolder {

    CheckBox shopping_cart_goodsChose;
    ImageView shopping_cart_goodsIcon;
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

    public ItemViewHolder(View view) {
        super(view);
        shopping_cart_goodsChose = (CheckBox) view.findViewById(R.id.shopping_cart_item_goods_chose);
        shopping_cart_goodsIcon = (ImageView) view.findViewById(R.id.shopping_cart_goods_icon);
        shopping_cart_goodsAdd = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_add);
        shopping_cart_goodsSubtract = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_subtract);
        abate_state = (LinearLayout) view.findViewById(R.id.abate_state);
        shopping_cart_goodsTitle = (TextView) view.findViewById(R.id.shopping_cart_goods_item_title);
        shopping_cart_SpecInfo = (TextView) view.findViewById(R.id.shopping_cart_goods_SpecInfo);
        shopping_cart_goodsMoney = (TextView) view.findViewById(R.id.shopping_cart_goods_item_money);
        shopping_cart_goodsOldMoney = (TextView) view.findViewById(R.id.shopping_cart_goods_item_old_money);
        shopping_cart_goodsOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        shopping_cart_goodsNumber = (TextView) view.findViewById(R.id.shopping_cart_goods_num);
        shopping_cart_goodsAdd_img = (ImageView) view.findViewById(R.id.shopping_cart_goods_add_img);
        shopping_cart_goodsSubtract_img = (ImageView) view.findViewById(R.id.shopping_cart_goods_subtract_img);    }
}
}
