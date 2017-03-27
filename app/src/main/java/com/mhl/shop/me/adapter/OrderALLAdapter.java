package com.mhl.shop.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.me.LogisticsActivity;
import com.mhl.shop.me.OrderConfirmReceiptActivity;
import com.mhl.shop.me.OrderDetailActivity;
import com.mhl.shop.me.OrderDetailOthersActivity;
import com.mhl.shop.me.OrderEvaluateActivity;
import com.mhl.shop.me.OrderRefundActivity;
import com.mhl.shop.me.OrderRefundChooiseActivity;
import com.mhl.shop.me.ReturnDetailActivity;
import com.mhl.shop.me.been.Order;
import com.mhl.shop.shopdetails.MyCheckStandActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.shopdetails.ViewEvaluationActivity;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.DialogView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-13 17:13
 * 描述：全部订单列表
 */
public class OrderALLAdapter extends RecyclerView.Adapter<OrderALLAdapter.MyViewHolder> {
    private Context context;
    private List<Order.RowsBean> list;
    private int orderStatus;
    public OrderALLAdapter(Context context, List<Order.RowsBean> data){
        this.context = context;
        this.list = data;
    }

    @Override
    public OrderALLAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderALLAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderALLAdapter.MyViewHolder holder, final int position) {
            orderStatus=list.get(position).getOrderStatus();
            if (list.get(position).getOrderStatus() == 11 || list.get(position).getOrderStatus() == 99 || list.get(position).getOrderStatus() == 80|| list.get(position).getOrderStatus() == 98) {
                holder.foot_view_button_litter.setVisibility(View.GONE);
                holder.shop_state.setVisibility(View.GONE);
                if (position > 0) {
                if(list.get(position).getVirtualOrderNo().equals(list.get(position-1).getVirtualOrderNo())){
                holder.head_order_view.setVisibility(View.GONE);
                holder.head_supplier_view.setVisibility(View.GONE);
                           }
                else {
                holder.head_order_view.setVisibility(View.VISIBLE);
                    holder.head_supplier_view.setVisibility(View.GONE);
                    holder.order_number.setText(list.get(position).getVirtualOrderNo());
                    if(list.get(position).getOrderStatus() == 11){
                        holder.order_state.setText("待付款");
                    }else
                    if(list.get(position).getOrderStatus() == 99){
                        holder.order_state.setText("已取消");
                    }else
                    if(list.get(position).getOrderStatus() == 98){
                        holder.order_state.setText("已取消");
                    }else
                    if(list.get(position).getOrderStatus() == 80){
                        holder.order_state.setText("已失效");
                    }else {
                        holder.order_state.setText("");
                    }

                             }
                }else {
                    holder.foot_order_view.setVisibility(View.VISIBLE);
                    holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");
                    holder.head_order_view.setVisibility(View.VISIBLE);
                    holder.head_supplier_view.setVisibility(View.GONE);
                    holder.order_number.setText(list.get(position).getVirtualOrderNo());
                    if(list.get(position).getOrderStatus() == 11){
                        holder.order_state.setText("待付款");
                    }else
                    if(list.get(position).getOrderStatus() == 99){
                        holder.order_state.setText("已取消");
                    }else
                    if(list.get(position).getOrderStatus() == 98){
                        holder.order_state.setText("已取消");
                    }else
                    if(list.get(position).getOrderStatus() == 80){
                        holder.order_state.setText("已失效");
                    }else {
                        holder.order_state.setText("");
                    }
                }
                //底部的处理
                if(list.size()>position+1){
                    if(!list.get(position).getVirtualOrderNo().equals(list.get(position+ 1).getVirtualOrderNo())){
                        holder.foot_order_view.setVisibility(View.VISIBLE);
                        holder.foot_view_button_big.setVisibility(View.VISIBLE);
                        holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");
                    } else {
                        holder.foot_order_view.setVisibility(View.GONE);
                        holder.foot_view_button_big.setVisibility(View.GONE);

                    }

                }else {
                    holder.foot_view_button_big.setVisibility(View.VISIBLE);
                    holder.foot_order_view.setVisibility(View.VISIBLE);
                    holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");

                }
                //底部大订单操作按钮的判断
                if(list.get(position).getOrderStatus()==11){
                    holder.canle_order.setVisibility(View.VISIBLE);
                    holder.go_pay.setVisibility(View.VISIBLE);
                    holder.agin_shop.setVisibility(View.GONE);
                }else  if(list.get(position).getOrderStatus()==99){
                    holder.agin_shop.setVisibility(View.VISIBLE);
                    holder.canle_order.setVisibility(View.GONE);
                    holder.go_pay.setVisibility(View.GONE);
                }else    if(list.get(position).getOrderStatus()==98){
                    holder.agin_shop.setVisibility(View.VISIBLE);
                    holder.canle_order.setVisibility(View.GONE);
                    holder.go_pay.setVisibility(View.GONE);
                }else if(list.get(position).getOrderStatus()==80){
                    holder.agin_shop.setVisibility(View.VISIBLE);
                    holder.canle_order.setVisibility(View.GONE);
                    holder.go_pay.setVisibility(View.GONE);
                }
        }else { //出去大订单以外的 都不显示顶部按钮，显示商品操作按钮
                holder.shop_state.setVisibility(View.VISIBLE);
                holder.head_order_view.setVisibility(View.GONE);
                holder.foot_view_button_big.setVisibility(View.GONE);
                holder.foot_view_button_litter.setVisibility(View.VISIBLE);
                if(orderStatus==12){
                    holder.canle_order_litter.setVisibility(View.VISIBLE);
                    holder.view_Logistics_bt.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("已付款");
                }else if(orderStatus==13){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.shenqing_tuikuan.setVisibility(View.VISIBLE);
                    holder.queren_shouhuo.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("已发货");
                }else if(orderStatus==14){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.shenqing_tuikuan.setVisibility(View.VISIBLE);
                    holder.liji_pingjia.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("已收货");
                }else if(orderStatus==15){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.shenqing_tuikuan.setVisibility(View.VISIBLE);
                    holder.chakan_pingjia.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("会员已评价");

                }else if(orderStatus==16){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.shenqing_tuikuan.setVisibility(View.VISIBLE);
                    holder.chakan_pingjia.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.shop_state.setText("双方已评价");
                }else if(orderStatus==20){
                    holder.canle_order_litter.setVisibility(View.VISIBLE);
                    holder.view_Logistics_bt.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("已付款");
                }else if(orderStatus==60){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.chakan_pingjia.setVisibility(View.VISIBLE);
                    holder.agin_shop_litter.setVisibility(View.VISIBLE);
                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.tuikuan_xiangqing.setVisibility(View.GONE);
                    holder.shop_state.setText("已完成");
                }else if(orderStatus==31){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待供应商审核");
                }else if(orderStatus==32){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);
                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待平台审核");
                }else if(orderStatus==41){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);
                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待供应商审核");
                }else if(orderStatus==42){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);
                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待会员退货");
                }else if(orderStatus==43){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待供应商收货");
                }else if(orderStatus==44){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.agin_shop_litter.setVisibility(View.GONE);
                    holder.shop_state.setText("待平台审核");
                }else if(orderStatus==61){
                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);
                    holder.agin_shop_litter.setVisibility(View.VISIBLE);

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);
                    holder.shop_state.setText("退款完成");
                }else if(orderStatus==62){

                    holder.canle_order_litter.setVisibility(View.GONE);
                    holder.shenqing_tuikuan.setVisibility(View.GONE);
                    holder.queren_shouhuo.setVisibility(View.GONE);
                    holder.liji_pingjia.setVisibility(View.GONE);
                    holder.chakan_pingjia.setVisibility(View.GONE);

                    holder.view_Logistics_bt.setVisibility(View.VISIBLE);
                    holder.tuikuan_xiangqing.setVisibility(View.VISIBLE);
                    holder.agin_shop_litter.setVisibility(View.VISIBLE);
                    holder.shop_state.setText("退货退款完成");
                }

                if (position > 0) {
                    if (list.get(position).getOrderNo().equals(list.get(position - 1).getOrderNo())) {
                        holder.head_supplier_view.setVisibility(View.GONE);

                    } else {
                        holder.head_supplier_view.setVisibility(View.VISIBLE);
                        holder.head_order_view.setVisibility(View.GONE);
                        Glide.with(context).load(Urls.URL_PHOTO + "/file/v3/download-" + list.get(position).getIdentificationId() + "-100-100.jpg"
                        ).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.write_order_supplier_icon);
                        holder.foot_order_view.setVisibility(View.VISIBLE);
                        holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");
                    }

                }else {
                    holder.head_supplier_view.setVisibility(View.VISIBLE);
                    holder.head_order_view.setVisibility(View.GONE);
                    Glide.with(context).load(Urls.URL_PHOTO + "/file/v3/download-" + list.get(position).getIdentificationId() + "-100-100.jpg"
                    ).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.icon_bg).into(holder.write_order_supplier_icon);
                    holder.foot_order_view.setVisibility(View.VISIBLE);
                    holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");
                }
                //底部的处理
                if(list.size()>position+1){
                    if(!list.get(position).getOrderNo().equals(list.get(position + 1).getOrderNo())){
                        holder.foot_order_view.setVisibility(View.VISIBLE);
                        holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");

                    } else {
                        holder.foot_order_view.setVisibility(View.GONE);
                    }
                }else {
                    holder.foot_order_view.setVisibility(View.VISIBLE);
                    holder.order_money.setText("共"+list.get(position).getConvergeGoodsCount()+"件，合计：¥"+ToolsUtils.Tow(list.get(position).getConvergeTotalPrice()+"")+"(含运费：¥"+ToolsUtils.Tow(list.get(position).getConvergeFreightMoney()+"")+")");

                }
            }

        holder.write_order_supplier_name.setText(list.get(position).getSupplierIdName());
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+list.get(position).getGoodsIdPic()+"-200-200.jpg"
        ).into(holder.item_image);
        holder.shop_name.setText(list.get(position).getGoodsIdName());
        holder.big_money.setText("¥"+ToolsUtils.Tow(list.get(position).getGoodsIdPrice()+""));
        holder.guige.setText(list.get(position).getGoodsSpec());
        holder.shop_count.setText("x"+list.get(position).getGoodsCount()+"");
        holder.lit_item_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getOrderStatus() == 11 || list.get(position).getOrderStatus() == 99 || list.get(position).getOrderStatus() == 80) {
                    //跳转到订单详情
                    Intent intent = new Intent();
                    intent.putExtra("orderVirtualId", list.get(position).getOrderVirtualId());//虚拟订单ID
                    intent.setClass(context, OrderDetailOthersActivity.class);
                    context.startActivity(intent);

                }else {//跳转到订单详情
                    Intent intent = new Intent();
                    intent.putExtra("PkId", list.get(position).getPkId());//PkId
                    intent.setClass(context, OrderDetailActivity.class);
                    context.startActivity(intent);

                }
            }
        });
        //商品取消订单
        holder.canle_order_litter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("pkId",  list.get(position).getPkId());
                intent1.putExtra("money", list.get(position).getTotalPrice()+"");
                intent1.putExtra("type", "1");
                intent1.putExtra("supplierId", list.get(position).getSupplierId());
                intent1.putExtra("goodsId", list.get(position).getGoodsId());
                intent1.setClass(context, OrderRefundActivity.class);
                context.startActivity(intent1);

            }
        });
        //待付款取消订单
        holder.canle_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView dialogView2 = new DialogView(context, R.style.myDialog,"确定取消订单 ？", new DialogView.OnYesButtonListener() {

                    @Override
                    public void YesButtonListener()
                    {
                    canleOrder(list.get(position).getOrderVirtualId(),position);
                    }

                }, null);
                dialogView2.show();

            }
        });
        //立即付款
        holder.go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay(position);
            }
        });
        holder.shenqing_tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                intent.putExtra("pkId", list.get(position).getPkId());
                intent.putExtra("money", list.get(position).getTotalPrice()+"");
                intent.putExtra("supplierId", list.get(position).getSupplierId());
                intent.putExtra("goodsId", list.get(position).getGoodsId());
                intent.setClass(context, OrderRefundChooiseActivity.class);
                context.startActivity(intent);

            }
        });
        holder.chakan_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewEvaluationActivity.class);
                intent.putExtra("goodsId", list.get(position).getPkId());
                intent.putExtra("url", Urls.URL_USER_EVALYATE);

                context.startActivity(intent);

            }
        });
        holder.tuikuan_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReturnDetailActivity.class);
                intent.putExtra("pkId", list.get(position).getPkId());
                intent.putExtra("supplierId", list.get(position).getSupplierId());
                intent.putExtra("goodsId", list.get(position).getGoodsId());
                context.startActivity(intent);

            }
        });
        //确认收货
        holder.queren_shouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmReceipt(list.get(position).getPkId(),position);

            }
        });
        holder.agin_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aginShopBig(list.get(position).getOrderVirtualId());

            }
        });
        holder.agin_shop_litter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_cart(list.get(position).getGoodsSpecDetailId(),"1");

            }
        });
        holder.head_supplier_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context,SupplierActivity.class);
                intent1.putExtra("supplierId", list.get(position).getSupplierId());
                context.startActivity(intent1);

            }
        });
        holder.view_Logistics_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent9 = new Intent(context,LogisticsActivity.class);
                intent9.putExtra("pkId", list.get(position).getPkId());
                intent9.putExtra("type","1");

                context.startActivity(intent9);
            }
        });
        //立即评价
        holder.liji_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context,OrderEvaluateActivity.class);
                intent1.putExtra("item_image", list.get(position).getGoodsIdPic());
                intent1.putExtra("shop_name", list.get(position).getGoodsIdName());
                intent1.putExtra("shop_count", list.get(position).getGoodsCount()+"");
                intent1.putExtra("guige", list.get(position).getGoodsSpec());
                intent1.putExtra("money", list.get(position).getGoodsIdPrice()+"");
                intent1.putExtra("orderDetailId", list.get(position).getPkId()+"");
                intent1.putExtra("goodsId", list.get(position).getGoodsId());
                context.startActivity(intent1);

            }
        });
    }
    //立即付款
    private void pay(int position) {
        OkGo.post(Urls.URL_GO_PAYMENT)//
                .tag(this)
                .params("orderVirtualId",list.get(position).getOrderVirtualId())
                .execute(new DialogCallback<LzyResponse<SumbitOk>>((Activity) context, true) {
                             @Override
                             public void onSuccess(LzyResponse<SumbitOk> lzyResponse, Call call, Response response) {
                                 if (lzyResponse.code == 200) {
                                     Intent mIntent = new Intent(context,MyCheckStandActivity.class);
                                     Bundle mBundle = new Bundle();
                                     mBundle.putSerializable("subok",lzyResponse.data);
                                     mIntent.putExtra("t", "1");
                                     mIntent.putExtras(mBundle);
                                     context.startActivity(mIntent);
                                 } else {
                                     T.showShort(context, lzyResponse.info);
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                             }
                         }
                );
    }

    //待付款取消订单
    private void canleOrder(String orderVirtualId, final int position) {
        OkGo.post(Urls.URL_ORDER_RETURN_ANCEL)//
                .tag(this)
                .params("orderVirtualId",orderVirtualId)
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 if(lzyResponse.code==200){

                                     //跳转到订单详情
                                     Intent intent = new Intent();
                                     intent.putExtra("orderVirtualId", list.get(position).getOrderVirtualId());//虚拟订单ID
                                     intent.setClass(context, OrderDetailOthersActivity.class);
                                     context.startActivity(intent);
                                 }else {
                                     T.showShort(context, "取消订单失败");
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(context, e.getMessage());
                             }
                         }
                );
    }

    //确认收货
    private void confirmReceipt(String pkId, final int position) {
        OkGo.post(Urls.URL_ORDER_CONSIGN)//
                .tag(this)
                .params("orderDetailId",pkId)
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 if(lzyResponse.code==200){
                                     Intent intent1 = new Intent(context,OrderConfirmReceiptActivity.class);

                                     intent1.putExtra("item_image", list.get(position).getGoodsIdPic());
                                     intent1.putExtra("shop_name", list.get(position).getGoodsIdName());
                                     intent1.putExtra("shop_count", list.get(position).getGoodsCount()+"");
                                     intent1.putExtra("guige", list.get(position).getGoodsSpec());
                                     intent1.putExtra("money", list.get(position).getGoodsIdPrice()+"");
                                     intent1.putExtra("orderDetailId", list.get(position).getPkId()+"");
                                     context.startActivity(intent1);

                                 }else {
                                     T.showShort(context, "确认收货失败");
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(context, e.getMessage());
                             }
                         }
                );
    }

    //再次购买
    Data angindata;
    private void aginShopBig(String orderVirtualId) {
        OkGo.post(Urls.URL_ORDER_AGAIN)//
                .tag(this)
                .params("orderVirtualId",orderVirtualId)
                .execute(new StringDialogCallback((Activity) context, true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 angindata=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(angindata.code==200){
                                     IntentUtils.startActivity((Activity) context, MainActivity.class);
                                     MyApplication.setTag(3);
                                     MyApplication.setType(1);
                                 }else{
                                     if(!TextUtils.isEmpty(angindata.getInfo())){
                                         T.showShort(context,angindata.getInfo());

                                     }
                                 }
                             }
                         }
                );
    }


    private void add_cart(String data, String s) {

        OkGo.post(Urls.URL_CART_ADD)//
                .tag(this)
                .params("goodsSpecId",data)
                .params("goodsCount",s)
                .execute(new StringDialogCallback((Activity)context, true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Data body=(Data) GsonUtils.fromJson(s,
                                Data.class);
                        if(body.getCode()==200){
                            IntentUtils.startActivity((Activity) context, MainActivity.class);
                            MyApplication.setTag(3);
                            MyApplication.setType(1);
                        }else {
                            if(!TextUtils.isEmpty(body.getInfo())){
                            T.showShort(context, body.getInfo());
                            }
                        }
                    }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(context, e.getMessage());
                             }
                         }
                );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView shop_name,shop_state,guige,shop_count,write_order_supplier_name,order_number,order_state,big_money,order_money;
        private RelativeLayout head_supplier_view;
        private LinearLayout head_order_view,foot_order_view,foot_view_button_big,foot_view_button_litter,lit_item_ly;
        private ImageView item_image;
        private CircleImageView write_order_supplier_icon;
        private Button canle_order,agin_shop,go_pay,canle_order_litter,view_Logistics_bt,shenqing_tuikuan
                ,queren_shouhuo,liji_pingjia,chakan_pingjia,agin_shop_litter,tuikuan_xiangqing;
        public MyViewHolder(View view)
        {
            super(view);

            head_supplier_view= (RelativeLayout) view.findViewById(R.id.head_supplier_view);//供应商头布局
            head_order_view= (LinearLayout) view.findViewById(R.id.head_order_view);//订单编号头布局
            foot_order_view= (LinearLayout) view.findViewById(R.id.foot_order__view);//尾部布局
            lit_item_ly= (LinearLayout) view.findViewById(R.id.lit_item_ly);
            foot_view_button_big= (LinearLayout) view.findViewById(R.id.foot_view_button_big);
            foot_view_button_litter= (LinearLayout) view.findViewById(R.id.foot_view_button_litter);
            item_image = (ImageView) view.findViewById(R.id.item_image);//商品头像
            write_order_supplier_icon = (CircleImageView) view.findViewById(R.id.write_order_supplier_icon);//供应商头像
            write_order_supplier_name= (TextView) view.findViewById(R.id.write_order_supplier_name);//供应商名字
            order_number= (TextView) view.findViewById(R.id.order_number);//订单编号
            order_state= (TextView) view.findViewById(R.id.order_state);//订单状态
            shop_state= (TextView) view.findViewById(R.id.shop_state);//状态
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            big_money = (TextView) view.findViewById(R.id.big_money);
            guige = (TextView) view.findViewById(R.id.guige);
            shop_count = (TextView) view.findViewById(R.id.shop_count);
            order_money = (TextView) view.findViewById(R.id.order_money);
            canle_order= (Button) view.findViewById(R.id.canle_order);
            agin_shop= (Button) view.findViewById(R.id.agin_shop);
            go_pay= (Button) view.findViewById(R.id.go_pay);
            canle_order_litter= (Button) view.findViewById(R.id.canle_order_litter);
            view_Logistics_bt= (Button) view.findViewById(R.id.view_Logistics_bt);
            shenqing_tuikuan= (Button) view.findViewById(R.id.shenqing_tuikuan);
            queren_shouhuo= (Button) view.findViewById(R.id.queren_shouhuo);
            liji_pingjia= (Button) view.findViewById(R.id.liji_pingjia);
            chakan_pingjia= (Button) view.findViewById(R.id.chakan_pingjia);
            agin_shop_litter= (Button) view.findViewById(R.id.agin_shop_litter);
            tuikuan_xiangqing= (Button) view.findViewById(R.id.tuikuan_xiangqing);
        }
    }
}
