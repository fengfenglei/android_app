package com.mhl.shop.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mhl.shop.me.been.CollectSupplier;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：供应商列表
 */
public class CollectSupplierAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<CollectSupplier.CollectionSuppliersBean> alist;
    BottomMenuDialog d5;

    public CollectSupplierAdapter(Context context, List<CollectSupplier.CollectionSuppliersBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.myupplier_item, null);
            holder = new ViewHolder();
            holder.mycollCartGoodsIcon = (CircleImageView) convertView.findViewById(R.id.mycoll_cart_goods_icon);
            holder.mycollCartGoodsItemTitle = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_item_title);
            holder.mycollCartGoodsSprice = (TextView) convertView.findViewById(R.id.mycoll_cart_goods_sprice);
            holder.more = (LinearLayout) convertView.findViewById(R.id.more);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mycollCartGoodsItemTitle.setText(alist.get(position).getSupplierIdName());
        holder.mycollCartGoodsSprice.setText("收藏于"+ToolsUtils.dateToStamp(alist.get(position).getCollectionTime())+"");
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getSupplierPicId()+"-150-150.jpg").into(holder.mycollCartGoodsIcon);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent(context,SupplierActivity.class);
                intent1.putExtra("supplierId", alist.get(position).getSupplierId());
                context.startActivity(intent1);
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
                                Share share = new Share(Urls.URL_BASE_HTTP+"/file/v3/download-" + alist.get(position).getSupplierPicId() + "-150-150.jpg",
                                        Urls.URL_SHARE+"/mall/search-shop-detail.html"+"?supplierId="+alist.get(position).getSupplierId(),
                                        alist.get(position).getSupplierIdName(),"卖货郎商城",context);
                                   share.goShare();
                            }
                        }).addMenu("取消收藏", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d5.dismiss();
                                delete(alist.get(position).getSupplierId(),position);
                            }
                        }).create();

                d5.show();
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        CircleImageView mycollCartGoodsIcon;
        TextView mycollCartGoodsItemTitle;
        TextView mycollCartGoodsSprice;
        LinearLayout more;
        LinearLayout item;

    }
    /**
     * 取消收藏
     *
     */
    private void delete(String id, final int position)
    {
        OkGo.post(Urls.URL_DELETE_SUPPLIER)//
                .tag(this)
                .params("supplierId",id)
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(context,  "已取消该供应商的收藏");
                                     //刷新
                                     if (MeInterface.onMyAllCollectSupplierListener!=null) {
                                         MeInterface.onMyAllCollectSupplierListener.OnMyAllCollectSupplierRefresh("",0);
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
