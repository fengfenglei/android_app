package com.mhl.shop.shopcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * lff
 *
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.MyViewHolder> {

    private Context context;
    private List<Cart> data;
    private View headerView;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnEditClickListener mOnEditClickListener;
    private static OnResfreshListener mOnResfreshListener;

    public ShopCartAdapter(Context context, List<Cart> data){
        this.context = context;
        this.data = data;
    }



    @Override
    public ShopCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_shopcart, parent, false);
        return new ShopCartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopCartAdapter.MyViewHolder holder, final int position) {
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+data.get(position).getGoodsPicId()+"-300-300.jpg"
        ).into(holder.ivShopCartClothPic);
        if (position > 0) {
//            if (data.get(position).getIsFirst()==1) {
            if (Long.valueOf(data.get(position).getSupplierId()).intValue() == (Long.valueOf(data.get(position - 1).getSupplierId())).intValue()) {
                holder.llShopCartHeader.setVisibility(View.GONE);
            } else {
                holder.llShopCartHeader.setVisibility(View.VISIBLE);
            }
        }else {
            holder.llShopCartHeader.setVisibility(View.VISIBLE);
        }
        holder.tvShopCartShopName.setText(data.get(position).getSupplierIdName());
        holder.tvShopCartClothName.setText(data.get(position).getGoodsIdName());
        holder.tvShopCartClothPrice.setText("¥"+ ToolsUtils.Tow((data.get(position).getPrice()*data.get(position).getGoodsCount())+""));
        holder.etShopCartClothNum.setText(data.get(position).getGoodsCount() + "");
        holder.tvShopCartClothColor.setText(data.get(position).getGoodsSpecInfo());
        holder.shopping_cart_goods_item_old_money.setText("¥"+ToolsUtils.Tow((data.get(position).getMarketPrice()*data.get(position).getGoodsCount())+""));
        holder.shopping_cart_goods_item_old_money.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        if(mOnResfreshListener != null){
            boolean isSelect = false;
            for(int i = 0;i < data.size(); i++){
                if(!data.get(i).getIsSelect()){
                    isSelect = false;
                    break;
                }else{
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
        }
        //减少商品
        holder.ivShopCartClothMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getGoodsCount() > 1) {
                OkGo.post(Urls.URL_CART_CHANGE_GOODS)//
                        .tag(this)
                        .params("userGoodsCartId",data.get(position).getPkId())
                        .params("goodsCount",data.get(position).getGoodsCount()-1)
                        .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                                     @Override
                                     public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                         if(lzyResponse.code==200){
                                             int count = data.get(position).getGoodsCount() - 1;
                                             if (mOnEditClickListener != null) {
                                                 mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                                             }
                                             data.get(position).setGoodsCount(count);
                                             notifyDataSetChanged();
                                         }else {
                                             T.showShort(context,lzyResponse.info);

                                         }
                                     }
                                     @Override
                                     public void onError(Call call, Response response, Exception e) {
                                         super.onError(call, response, e);
                                         if(!TextUtils.isEmpty(e.getMessage())){
                                             T.showShort(context,e.getMessage());

                                         }
                                     }
                                 }
                        );





                }
            }
        });
        //跳转至商品详情
        holder.shop_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("goodsid", data.get(position).getGoodsId());
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        //增加商品
        holder.ivShopCartClothAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post(Urls.URL_CART_CHANGE_GOODS)//
                        .tag(this)
                        .params("userGoodsCartId",data.get(position).getPkId())
                        .params("goodsCount",data.get(position).getGoodsCount()+1)
                        .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                                     @Override
                                     public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                         if(lzyResponse.code==200){
                                             int count = data.get(position).getGoodsCount() + 1;
                                             if(mOnEditClickListener != null){
                                                 mOnEditClickListener.onEditClick(position,data.get(position).getId(),count);
                                             }
                                             Log.d("ShopCartAdapter","data="+lzyResponse.data);

                                             data.get(position).setGoodsCount(count);
                                             notifyDataSetChanged();
                                         }else {
                                             Log.d("ShopCartAdapter","info="+lzyResponse.info);
                                             T.showShort(context,lzyResponse.info);
                                         }
                                     }
                                     @Override
                                     public void onError(Call call, Response response, Exception e) {
                                         super.onError(call, response, e);
                                         if(!TextUtils.isEmpty(e.getMessage())){
                                             T.showShort(context,e.getMessage());

                                         }
                                     }
                                 }
                        );

            }
        });

        if(data.get(position).getIsSelect()){
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
        }else {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.not_checked));
        }

        if(data.get(position).getIsShopSelect()){
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
        }else {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.not_checked));
        }


        //单个商品选额
        holder.ivShopCartClothSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).getIsSelect());
                //通过循环找出不同商铺的第一个商品的位置
                for(int i = 0;i < data.size(); i++){
                    if(data.get(i).getIsFirst() == 1) {
                        //遍历去找出同一家商铺的所有商品的勾选情况
                        for(int j = 0;j < data.size();j++){
                            //如果是同一家商铺的商品，并且其中一个商品是未选中，那么商铺的全选勾选取消
                            if(data.get(j).getSupplierId() .equals( data.get(i).getSupplierId() )&& !data.get(j).getIsSelect()){
                                data.get(i).setShopSelect(false);
                                break;
                            }else{
                                //如果是同一家商铺的商品，并且所有商品是选中，那么商铺的选中全选勾选
                                data.get(i).setShopSelect(true);
                            }
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
//
        holder.ivShopCartShopSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getIsFirst() == 1) {
                    data.get(position).setShopSelect(!data.get(position).getIsShopSelect());
                    for(int i = 0;i < data.size();i++){
                        if(Long.valueOf(data.get(i).getSupplierId()).intValue()  ==Long.valueOf(data.get(position).getSupplierId()).intValue() ){
                            data.get(i).setSelect(data.get(position).getIsShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

//    private void showDialog(final View view, final int position){
//            //调用删除某个规格商品的接口
//            if(mOnDeleteClickListener != null){
//                mOnDeleteClickListener.onDeleteClick(view,position,data.get(position).getId());
//            }
//            data.remove(position);
//            //重新排序，标记所有商品不同商铺第一个的商品位置
//            MainActivity.isSelectFirst(data);
//            notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if(headerView != null){
            count++;
        }
        return count;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ivShopCartShopSel;
        private TextView tvShopCartShopName;
        private TextView tvShopCartClothName;
        private TextView tvShopCartClothPrice;
        private TextView etShopCartClothNum;
        private TextView tvShopCartClothColor;
        private TextView shopping_cart_goods_item_old_money;
        private TextView tvShopCartClothSize;
        private ImageView ivShopCartClothSel;
        private LinearLayout ivShopCartClothMinus;
        private LinearLayout ivShopCartClothAdd;
        private ImageView ivShopCartClothPic;
        private LinearLayout llShopCartHeader;
        private LinearLayout shop_ly;
        public MyViewHolder(View view)
        {
            super(view);
            llShopCartHeader = (LinearLayout) view.findViewById(R.id.ll_shopcart_header);
            shop_ly = (LinearLayout) view.findViewById(R.id.shop_ly);
            ivShopCartShopSel = (ImageView) view.findViewById(R.id.iv_item_shopcart_shopselect);
            tvShopCartShopName = (TextView) view.findViewById(R.id.tv_item_shopcart_shopname);
            tvShopCartClothName = (TextView) view.findViewById(R.id.tv_item_shopcart_clothname);
            tvShopCartClothPrice = (TextView) view.findViewById(R.id.tv_item_shopcart_cloth_price);
            etShopCartClothNum = (TextView) view.findViewById(R.id.et_item_shopcart_cloth_num);
            tvShopCartClothColor = (TextView) view.findViewById(R.id.tv_item_shopcart_cloth_color);
            ivShopCartClothSel = (ImageView) view.findViewById(R.id.tv_item_shopcart_clothselect);
            ivShopCartClothMinus = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_subtract);
            ivShopCartClothAdd = (LinearLayout) view.findViewById(R.id.shopping_cart_goods_add);
            ivShopCartClothPic = (ImageView) view.findViewById(R.id.iv_item_shopcart_cloth_pic);
            shopping_cart_goods_item_old_money = (TextView) view.findViewById(R.id.shopping_cart_goods_item_old_money);
        }
    }


    public View getHeaderView(){
        return headerView;
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(View view, int position, int cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener){
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public interface OnEditClickListener{
        void onEditClick(int position, int cartid, int count);
    }

    public void setOnEditClickListener(OnEditClickListener mOnEditClickListener){
        this.mOnEditClickListener = mOnEditClickListener;
    }

    public interface OnResfreshListener{
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener){
        this.mOnResfreshListener = mOnResfreshListener;
        this.mOnResfreshListener = mOnResfreshListener;
    }

}
