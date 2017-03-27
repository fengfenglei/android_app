package com.mhl.shop.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.mhl.shop.main.BottomMenuDialog;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.main.Share;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.been.CollectShop;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：商品列表
 */
public class CollectAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<CollectShop.CollectionGoodsBean> alist;
    BottomMenuDialog d5;
    public CollectAdapter(Context context, List<CollectShop.CollectionGoodsBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.mycoll_item, null);
            holder = new ViewHolder();
            holder.mycollCartGoodsIcon = (ImageView) convertView.findViewById(R.id.mycoll_cart_goods_icon);
            holder.mycollCartGoodsItemTitle = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_item_title);
            holder.mycollCartGoodsSprice = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_sprice);
            holder.more = (LinearLayout) convertView.findViewById(R.id.more);
            holder.detail_ly= (LinearLayout) convertView.findViewById(R.id.detail_ly);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+alist.get(position).getGoodsPhotoId()+"-80-80.jpg").into(holder.mycollCartGoodsIcon);

        holder.mycollCartGoodsItemTitle.setText(alist.get(position).getGoodsName());
        holder.mycollCartGoodsSprice.setText("¥"+alist.get(position).getCurrentPrice()+"");
        holder.detail_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("goodsid", alist.get(position).getGoodsId());
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        //更多
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d5 = new BottomMenuDialog.Builder(context)
                        .addMenu("分享给好友", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d5.dismiss();
                                Share share = new Share(Urls.URL_BASE_HTTP+"/file/v3/download-" + alist.get(position).getGoodsPhotoId() + "-150-150.jpg",
                                        Urls.URL_SHARE+"/mall/good-detail-"+alist.get(position).getGoodsId()+".html",
                                        alist.get(position).getGoodsName(),"卖货郎商城",context);
                                share.goShare();

                            }
                        }).addMenu("取消收藏", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d5.dismiss();
                                delete(alist.get(position).getGoodsId(),position);
                            }
                        }).create();

                d5.show();
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        ImageView mycollCartGoodsIcon;
        TextView mycollCartGoodsItemTitle;
        TextView mycollCartGoodsSprice;
        LinearLayout more,detail_ly;
    }
    /**
     * 取消收藏
     *
     */
    private void delete(String id, final int position)
    {
        OkGo.post(Urls.URL_DELETE_COLLECTSHOP)//
                .tag(this)
                .params("goodsId",id)
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(context,  "已取消该商品的收藏");
                                     //刷新
                                     if (MeInterface.onMyAllCollectListener!=null) {
                                         MeInterface.onMyAllCollectListener.OnMyAllCollectRefresh("",0);
                                     }
                                 }else{
                                     T.showShort(context, lzyResponse.info);
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );
    }

}
