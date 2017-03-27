package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.been.OrderDetail;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.shopdetails.ViewEvaluationActivity;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-15 20:16
 * 描述：订单详情
 */

public class OrderDetailActivity extends MyBaseActivity {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.wuliu1)
    TextView wuliu1;
    @Bind(R.id.wuliu2)
    TextView wuliu2;
    @Bind(R.id.view_Logistics)
    LinearLayout viewLogistics;
    @Bind(R.id.name_phone)
    TextView namePhone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.liuyan)
    TextView liuyan;
    @Bind(R.id.write_order_supplier_icon)
    CircleImageView writeOrderSupplierIcon;
    @Bind(R.id.write_order_supplier_name)
    TextView writeOrderSupplierName;
    @Bind(R.id.head_supplier_view)
    RelativeLayout headSupplierView;
    @Bind(R.id.item_image)
    ImageView itemImage;
    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.guige)
    TextView guige;
    @Bind(R.id.big_money)
    TextView bigMoney;
    @Bind(R.id.lit_money)
    TextView litMoney;
    @Bind(R.id.shop_count)
    TextView shopCount;
    @Bind(R.id.lit_item_ly)
    LinearLayout litItemLy;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.write_order_all_money)
    TextView writeOrderAllMoney;
    @Bind(R.id.write_order_coupons_money)
    TextView writeOrderCouponsMoney;
    @Bind(R.id.coupons_lay)
    RelativeLayout couponsLay;
    @Bind(R.id.write_order_hld_money)
    TextView writeOrderHldMoney;
    @Bind(R.id.hld_lay)
    RelativeLayout hldLay;
    @Bind(R.id.jihuo_money)
    TextView jihuoMoney;
    @Bind(R.id.write_order_ketixian)
    TextView writeOrderKetixian;
    @Bind(R.id.tixian_money)
    TextView tixianMoney;
    @Bind(R.id.write_order_money)
    TextView write_order_money;
    @Bind(R.id.write_order_freight_money)
    TextView write_order_freight_money;
    @Bind(R.id.pay_way)
    TextView pay_way;

    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.view_Logistics_bt)
    Button view_Logistics_bt;
    @Bind(R.id.shenqing_tuikuan)
    Button shenqing_tuikuan;
    @Bind(R.id.queren_shouhuo)
    Button queren_shouhuo;
    @Bind(R.id.liji_pingjia)
    Button liji_pingjia;
    @Bind(R.id.chakan_pingjia)
    Button chakan_pingjia;
    @Bind(R.id.tuikuan_xiangqing)
    Button tuikuan_xiangqing;
    @Bind(R.id.canle_order)
    Button canle_order;
    @Bind(R.id.agin_shop)
    Button agin_shop;
    @Bind(R.id.go_pay)
    Button go_pay;
    @Bind(R.id.all_ly)
    LinearLayout all_ly;
    @Bind(R.id.jihuo_money_lay)
    RelativeLayout jihuoMoneyLay;
    @Bind(R.id.ketixian_lay)
    RelativeLayout ketixianLay;
    @Bind(R.id.balance_lay)
    RelativeLayout balanceLay;
    @Bind(R.id.freight_lay)
    RelativeLayout freightLay;
    private String PkId, type;
    private OrderDetail dataDetail;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    private void initView() {
        Intent intent = getIntent();
        PkId = intent.getStringExtra("PkId");
        titleCenterTextview.setText("订单详情");
        titleRightTextview.setText("订单日志");
        titleRightTextview.setVisibility(View.VISIBLE);
        initData(PkId);
    }

    //获取订单详情信息
    private void initData(String PkId) {
        OkGo.post(Urls.URL_ORDER_DETAIL)//
                .tag(this)
                .params("orderDetailId", PkId)
                .execute(new DialogCallback<LzyResponse<OrderDetail>>(OrderDetailActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<OrderDetail> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     dataDetail = lzyResponse.data;
                                     setData(lzyResponse.data);
                                     all_ly.setVisibility(View.VISIBLE);

                                 } else {
                                     T.showShort(OrderDetailActivity.this, lzyResponse.info);

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

    //给视图绑定数据
    private void setData(OrderDetail data) {
        orderNumber.setText(data.getOrderVirtualId() + "");
        if (data.getOrderStatus() == 99) {
            orderState.setText("已取消");
        } else if (data.getOrderStatus() == 80) {
            orderState.setText("已失效");
        } else if (data.getOrderStatus() == 11) {
            orderState.setText("待付款");
        } else if (data.getOrderStatus() == 12) {
            orderState.setText("已付款");
            type = "1";
        } else if (data.getOrderStatus() == 20) {
            orderState.setText("已付款");
            type = "1";
        } else if (data.getOrderStatus() == 13) {
            orderState.setText("已发货");
            type = "2";
        } else if (data.getOrderStatus() == 14) {
            orderState.setText("已收货");
            type = "2";
        } else if (data.getOrderStatus() == 15) {
            orderState.setText("会员已评价");
        } else if (data.getOrderStatus() == 16) {
            orderState.setText("双方已评价");
        } else if (data.getOrderStatus() == 31) {
            orderState.setText("待供应商审核");
        } else if (data.getOrderStatus() == 32) {
            orderState.setText("待平台审核");
        } else if (data.getOrderStatus() == 41) {
            orderState.setText("待供应商审核");
        } else if (data.getOrderStatus() == 42) {
            orderState.setText("待会员退货");
        } else if (data.getOrderStatus() == 43) {
            orderState.setText("待供应商收货");
        } else if (data.getOrderStatus() == 44) {
            orderState.setText("待平台审核");
        } else if (data.getOrderStatus() == 61) {
            orderState.setText("退款完成");
        } else if (data.getOrderStatus() == 60) {
            orderState.setText("已完成");
        }  else if (data.getOrderStatus() == 62) {
            orderState.setText("退货退款完成");
        }
        if(data.getTrack() ==null){
            viewLogistics.setVisibility(View.GONE);
        }else {
            if (data.getTrack().getExpressProgress()!=null && !data.getTrack().getExpressProgress().isEmpty()) {
                wuliu1.setText(data.getTrack().getExpressProgress().get(0).getContext()+"");
                wuliu2.setText(data.getTrack().getExpressProgress().get(0).getFtime());
            }else {
                viewLogistics.setVisibility(View.GONE);

            }
        }
        namePhone.setText(data.getConsignee() + "   " + data.getConsigneeTel());
        address.setText(data.getAddressName());
        if (null == data.getLeaveMessage() || TextUtils.isEmpty(data.getLeaveMessage())) {
            liuyan.setText("无");
        } else {
            liuyan.setText(data.getLeaveMessage());
        }
        writeOrderSupplierName.setText(data.getSupplierIdName());
        Glide.with(this).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getIdentificationId() + "-100-100.jpg"
        ).into(writeOrderSupplierIcon);
        Glide.with(this).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getGoodsIdPic() + "-200-200.jpg"
        ).into(itemImage);
        shopName.setText(data.getGoodsIdName());
        guige.setText(data.getGoodsSpec());
        bigMoney.setText("¥" + ToolsUtils.Tow(data.getGoodsIdPrice() + ""));
        shopCount.setText("x" + data.getGoodsCount() + "");
        writeOrderAllMoney.setText("¥" + ToolsUtils.Tow((data.getTotalPrice()-data.getFreightMoney()) + ""));
        //优惠券
        if (data.getCouponMoney() <= 0 || TextUtils.isEmpty(data.getCouponMoney() + "")) {
            couponsLay.setVisibility(View.GONE);
        } else {
            writeOrderCouponsMoney.setText("¥" + ToolsUtils.Tow(data.getCouponMoney() + ""));
            couponsLay.setVisibility(View.VISIBLE);
        }
        //运费
        if (data.getFreightMoney() <= 0 || TextUtils.isEmpty(data.getFreightMoney() + "")) {
//            freightLay.setVisibility(View.GONE);
            write_order_freight_money.setText("¥" +"0.00");
        } else {
            write_order_freight_money.setText("¥" + ToolsUtils.Tow(data.getFreightMoney() + ""));
//            freightLay.setVisibility(View.VISIBLE);
        }
        //货郎豆
        if (data.getGoldMoney() <= 0 || TextUtils.isEmpty(data.getGoldMoney() + "")) {
            hldLay.setVisibility(View.GONE);
        } else {
            writeOrderHldMoney.setText("¥" + ToolsUtils.Tow(data.getGoldMoney() + ""));
            hldLay.setVisibility(View.VISIBLE);
        }
        //激活金额
        if (data.getActivationMoney() <= 0 || TextUtils.isEmpty(data.getActivationMoney() + "")) {
            jihuoMoneyLay.setVisibility(View.GONE);
        } else {
            jihuoMoney.setText("¥" + ToolsUtils.Tow(data.getActivationMoney() + ""));
            jihuoMoneyLay.setVisibility(View.VISIBLE);
        }
        //可提现金额
        if (data.getDepositMoney() <= 0 || TextUtils.isEmpty(data.getDepositMoney() + "")) {
            ketixianLay.setVisibility(View.GONE);
        } else {
            writeOrderKetixian.setText("¥" + ToolsUtils.Tow(data.getDepositMoney() + ""));
            ketixianLay.setVisibility(View.VISIBLE);
        }
        //在线支付
        if (data.getPayMoney() <= 0 || TextUtils.isEmpty(data.getPayMoney() + "")) {
            balanceLay.setVisibility(View.GONE);
        }else {
            balanceLay.setVisibility(View.VISIBLE);
            write_order_money.setText("¥" + ToolsUtils.Tow(data.getPayMoney() + ""));
            if (data.getPayType() == 1) {
                pay_way.setText("微信支付：");
            } else if (data.getPayType() == 2) {
                pay_way.setText("支付宝支付：");
            } else if (data.getPayType() == 3) {
                pay_way.setText("农行支付：");
            } else if (data.getPayType() == 4) {
                pay_way.setText("快钱支付：");
            } else if (data.getPayType() == 5) {
                pay_way.setText("农行对公支付：");
            }
        }
        int orderStatus = data.getOrderStatus();
        if (orderStatus == 12) {
            canle_order.setVisibility(View.VISIBLE);
            view_Logistics_bt.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 13) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            shenqing_tuikuan.setVisibility(View.VISIBLE);
            queren_shouhuo.setVisibility(View.VISIBLE);

            canle_order.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 14) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            shenqing_tuikuan.setVisibility(View.VISIBLE);
            liji_pingjia.setVisibility(View.VISIBLE);

            canle_order.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 15) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            shenqing_tuikuan.setVisibility(View.VISIBLE);
            chakan_pingjia.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 16) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            shenqing_tuikuan.setVisibility(View.VISIBLE);
            chakan_pingjia.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
        } else if (orderStatus == 20) {
            canle_order.setVisibility(View.VISIBLE);
            view_Logistics_bt.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 60) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            chakan_pingjia.setVisibility(View.VISIBLE);
            agin_shop.setVisibility(View.VISIBLE);

            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            tuikuan_xiangqing.setVisibility(View.GONE);
        } else if (orderStatus == 31) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);

            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 32) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 41) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 42) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 43) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 44) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            agin_shop.setVisibility(View.GONE);
        } else if (orderStatus == 61) {
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            agin_shop.setVisibility(View.VISIBLE);
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
        } else if (orderStatus == 62) {
            canle_order.setVisibility(View.GONE);
            shenqing_tuikuan.setVisibility(View.GONE);
            queren_shouhuo.setVisibility(View.GONE);
            liji_pingjia.setVisibility(View.GONE);
            chakan_pingjia.setVisibility(View.GONE);
            view_Logistics_bt.setVisibility(View.VISIBLE);
            tuikuan_xiangqing.setVisibility(View.VISIBLE);
            agin_shop.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.title_left_imageview, R.id.title_right_textview, R.id.view_Logistics,
            R.id.head_supplier_view, R.id.view_Logistics_bt, R.id.shenqing_tuikuan,
            R.id.queren_shouhuo, R.id.liji_pingjia, R.id.chakan_pingjia,
            R.id.tuikuan_xiangqing, R.id.canle_order, R.id.agin_shop, R.id.go_pay, R.id.lit_item_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_textview:
                Intent intent3 = new Intent();
                intent3.putExtra("orderDetailId", dataDetail.getPkId());
                intent3.putExtra("orderVirtualId", "");
                intent3.putExtra("type", "1");
                intent3.putExtra("orderNum", orderNumber.getText().toString());
                intent3.putExtra("orderStatus", orderState.getText().toString());
                intent3.setClass(OrderDetailActivity.this, OrderLogActivity.class);
                startActivity(intent3);
                break;
            case R.id.lit_item_ly:
                Intent intent1 = new Intent(OrderDetailActivity.this, GoodsDetailsActivity.class);
                intent1.putExtra("goodsid", dataDetail.getGoodsId());
                startActivity(intent1);
                break;
            case R.id.view_Logistics://顶部物流
                Intent intent10 = new Intent(OrderDetailActivity.this,LogisticsActivity.class);
                intent10.putExtra("pkId", dataDetail.getPkId());
                intent10.putExtra("type","1");
                startActivity(intent10);

                break;
            case R.id.head_supplier_view:
                Intent intent2 = new Intent(OrderDetailActivity.this, SupplierActivity.class);
                intent2.putExtra("supplierId", dataDetail.getSupplierId());
                startActivity(intent2);
                break;
            case R.id.view_Logistics_bt:
                Intent intent9 = new Intent();
                intent9.putExtra("pkId", dataDetail.getPkId());
                intent9.putExtra("type","1");
                intent9.setClass(OrderDetailActivity.this, LogisticsActivity.class);
                startActivity(intent9);
                break;
            case R.id.shenqing_tuikuan:
                Intent intent = new Intent();
                intent.putExtra("pkId", dataDetail.getPkId());
                intent.putExtra("money", dataDetail.getTotalPrice() + "");
                intent.putExtra("supplierId", dataDetail.getSupplierId());
                intent.putExtra("goodsId", dataDetail.getGoodsId());
                intent.setClass(OrderDetailActivity.this, OrderRefundChooiseActivity.class);
                startActivity(intent);

                break;
            case R.id.queren_shouhuo:
                confirmReceipt(dataDetail.getPkId());
                break;
            case R.id.liji_pingjia:
                Intent intent4 = new Intent(OrderDetailActivity.this, OrderEvaluateActivity.class);
                intent4.putExtra("item_image", dataDetail.getGoodsIdPic());
                intent4.putExtra("shop_name", dataDetail.getGoodsIdName());
                intent4.putExtra("shop_count", dataDetail.getGoodsCount() + "");
                intent4.putExtra("guige", dataDetail.getGoodsSpec());
                intent4.putExtra("money", dataDetail.getGoodsIdPrice() + "");
                intent4.putExtra("orderDetailId", dataDetail.getPkId() + "");
                startActivity(intent4);
                break;
            case R.id.chakan_pingjia:
                Intent intent5 = new Intent(OrderDetailActivity.this, ViewEvaluationActivity.class);
                intent5.putExtra("goodsId", dataDetail.getPkId());
                intent5.putExtra("url", Urls.URL_USER_EVALYATE);

                OrderDetailActivity.this.startActivity(intent5);
                break;
            case R.id.tuikuan_xiangqing:
                Intent intent7 = new Intent(OrderDetailActivity.this, ReturnDetailActivity.class);
                intent7.putExtra("pkId", dataDetail.getPkId());
                intent7.putExtra("supplierId", dataDetail.getSupplierId());
                intent7.putExtra("goodsId", dataDetail.getGoodsId());
                startActivity(intent7);
                break;
            case R.id.canle_order:
                Intent intent8 = new Intent();
                intent8.putExtra("pkId", dataDetail.getPkId());
                intent8.putExtra("money", dataDetail.getTotalPrice() + "");
                intent8.putExtra("type", "1");
                intent8.putExtra("supplierId", dataDetail.getSupplierId());
                intent8.putExtra("goodsId", dataDetail.getGoodsId());
                intent8.setClass(OrderDetailActivity.this, OrderRefundActivity.class);
                startActivity(intent8);
                finish();
                break;
            case R.id.agin_shop:
                add_cart(dataDetail.getGoodsSpecDetailId(), "1");
//                aginShopBig(dataDetail.getOrderVirtualId());
                break;
            case R.id.go_pay:
                Intent intent6 = new Intent();
//                intent6.putExtra("id", sb_pk.toString());
//                intent6.setClass(OrderDetailActivity.this, SubmitOrderActivity.class);
//                startActivity(intent6);

                break;
        }
    }


    //再次购买
    Data angindata;
    private void aginShopBig(String orderVirtualId) {
        OkGo.post(Urls.URL_ORDER_AGAIN)//
                .tag(this)
                .params("orderVirtualId",orderVirtualId)
                .execute(new StringDialogCallback(OrderDetailActivity.this, true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 angindata=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(angindata.code==200){
                                     IntentUtils.startActivity(OrderDetailActivity.this, MainActivity.class);
                                     MyApplication.setTag(3);
                                     MyApplication.setType(1);
                                 }else{
                                     T.showShort(OrderDetailActivity.this, "失败");
                                 }
                             }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        T.showShort(OrderDetailActivity.this, e.getMessage());
                    }
                         }
                );
    }
    //再次购买
    private void add_cart(String data, String s) {

        OkGo.post(Urls.URL_CART_ADD)//
                .tag(this)
                .params("goodsSpecId", data)
                .params("goodsCount", s)
                .execute(new StringDialogCallback(OrderDetailActivity.this, true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 angindata=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(angindata.code==200){
                                     IntentUtils.startActivity(OrderDetailActivity.this, MainActivity.class);
                                     MyApplication.setTag(3);
                                     MyApplication.setType(1);
                                 }else{
                                     if(!TextUtils.isEmpty(angindata.getInfo())){
                                         T.showShort(OrderDetailActivity.this, angindata.getInfo());

                                     }
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(OrderDetailActivity.this, e.getMessage());
                             }
                         }
                );
    }

    //确认收货
    private void confirmReceipt(String pkId) {
        OkGo.post(Urls.URL_ORDER_CONSIGN)//
                .tag(this)
                .params("orderDetailId", pkId)
                .execute(new DialogCallback<LzyResponse<Null>>(OrderDetailActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 if (lzyResponse.code == 200) {
                                     Intent intent1 = new Intent(OrderDetailActivity.this, OrderConfirmReceiptActivity.class);

                                     intent1.putExtra("item_image", dataDetail.getGoodsIdPic());
                                     intent1.putExtra("shop_name", dataDetail.getGoodsIdName());
                                     intent1.putExtra("shop_count", dataDetail.getGoodsCount() + "");
                                     intent1.putExtra("guige", dataDetail.getGoodsSpec());
                                     intent1.putExtra("money", dataDetail.getGoodsIdPrice() + "");
                                     intent1.putExtra("orderDetailId", dataDetail.getPkId() + "");
                                     startActivity(intent1);

                                 } else {
                                     T.showShort(OrderDetailActivity.this, "确认收货失败");
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(OrderDetailActivity.this, e.getMessage());
                             }
                         }
                );
    }
}
