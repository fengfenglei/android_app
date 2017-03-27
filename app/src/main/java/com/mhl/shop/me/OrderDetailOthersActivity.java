package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.mhl.shop.me.adapter.OrderOthersAdapter;
import com.mhl.shop.me.been.OrderOthers;
import com.mhl.shop.shopdetails.MyCheckStandActivity;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.DialogView;
import com.umeng.analytics.MobclickAgent;

import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-19 13:51
 * 描述：订单状态为11/99/80/98 的订单详情
 */
public class OrderDetailOthersActivity extends MyBaseActivity {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.name_phone)
    TextView namePhone;
    @Bind(R.id.freightMoney)
    TextView freightMoney;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.order_item)
    RecyclerView orderItem;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.write_order_all_money)
    TextView writeOrderAllMoney;
    @Bind(R.id.write_order_coupons_money)
    TextView writeOrderCouponsMoney;

    @Bind(R.id.write_order_hld_money)
    TextView writeOrderHldMoney;

    @Bind(R.id.jihuo_money)
    TextView jihuoMoney;

    @Bind(R.id.write_order_ketixian)
    TextView writeOrderKetixian;
    @Bind(R.id.tixian_money)
    TextView tixianMoney;
    @Bind(R.id.need_price)
    TextView needPrice;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.canle_order)
    Button canleOrder;
    @Bind(R.id.agin_shop)
    Button aginShop;
    @Bind(R.id.go_pay)
    Button goPay;
    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.all_ly)
    LinearLayout all_ly;
    @Bind(R.id.freight_lay)
    RelativeLayout freightLay;
    @Bind(R.id.coupons_lay)
    RelativeLayout couponsLay;
    @Bind(R.id.hld_lay)
    RelativeLayout hldLay;
    @Bind(R.id.jihuo_money_lay)
    RelativeLayout jihuoMoneyLay;
    @Bind(R.id.ketixian_lay)
    RelativeLayout ketixianLay;
    private String orderVirtualId;
    private OrderOthers dataDetail;
    OrderOthersAdapter adapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order_detail);
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
        orderVirtualId = intent.getStringExtra("orderVirtualId");
        titleCenterTextview.setText("订单详情");
        titleRightTextview.setText("订单日志");
        titleRightTextview.setVisibility(View.VISIBLE);
        initData(orderVirtualId);
    }

    //获取订单详情信息
    private void initData(String orderVirtualId) {

        OkGo.post(Urls.URL_ORDER_VIR_DETAIL)//
                .tag(this)
                .params("orderVirtualId", orderVirtualId)
                .execute(new DialogCallback<LzyResponse<OrderOthers>>(OrderDetailOthersActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<OrderOthers> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {

                                     dataDetail = lzyResponse.data;
                                     //指定排序器(把相同供应商的排列在一起)
                                     Collections.sort(dataDetail.getOrderDetail(), new Comparator<OrderOthers.OrderDetailBean>() {
                                         @Override
                                         public int compare(OrderOthers.OrderDetailBean o1, OrderOthers.OrderDetailBean o2) {
                                             return (int) (Long.parseLong(o1.getSupplierId()) - Long.parseLong(o2.getSupplierId()));
                                         }
                                     });
                                     setData(lzyResponse.data);
                                     all_ly.setVisibility(View.VISIBLE);
                                 } else {
                                     T.showShort(OrderDetailOthersActivity.this, lzyResponse.info);

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

    private void setData(OrderOthers data) {
        orderNumber.setText(data.getVirtualOrderNo());
        if (data.getOrderStatus() == 11) {
            orderState.setText("待付款");
        } else if (data.getOrderStatus() == 99) {
            orderState.setText("已取消");
        } else if (data.getOrderStatus() == 98) {
            orderState.setText("已取消");
        } else if (data.getOrderStatus() == 80) {
            orderState.setText("已失效");
        } else {
            orderState.setText("");
        }
        if (data.getOrderStatus() == 11) {
            canleOrder.setVisibility(View.VISIBLE);
            goPay.setVisibility(View.VISIBLE);
            aginShop.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 99) {
            aginShop.setVisibility(View.VISIBLE);
            canleOrder.setVisibility(View.GONE);
            goPay.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 98) {
            aginShop.setVisibility(View.VISIBLE);
            canleOrder.setVisibility(View.GONE);
            goPay.setVisibility(View.GONE);

        } else if (data.getOrderStatus() == 80) {
            aginShop.setVisibility(View.VISIBLE);
            canleOrder.setVisibility(View.GONE);
            goPay.setVisibility(View.GONE);
        }
        namePhone.setText(data.getConsignee() + "   " + data.getConsigneeTel());
        address.setText(data.getAddressIdName());


        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailOthersActivity.this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        orderItem.setLayoutManager(layoutManager);
        orderItem.setHasFixedSize(true);
        orderItem.setNestedScrollingEnabled(false);
        adapter = new OrderOthersAdapter(OrderDetailOthersActivity.this, data.getOrderDetail());
        orderItem.setAdapter(adapter);
        //优惠券
        if (data.getCouponMoney() <= 0 || TextUtils.isEmpty(data.getCouponMoney() + "")) {
            couponsLay.setVisibility(View.GONE);
        } else {
            writeOrderCouponsMoney.setText("¥" + ToolsUtils.Tow(data.getCouponMoney() + ""));
            couponsLay.setVisibility(View.VISIBLE);
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
        orderTime.setText(ToolsUtils.dateToStamp(data.getAddTime()));
        needPrice.setText("¥" + ToolsUtils.Tow(data.getPayMoney() + ""));
        writeOrderAllMoney.setText("¥" + ToolsUtils.Tow((data.getTotalPrice()-data.getFreightMoney()) + ""));
        freightMoney.setText("¥" + ToolsUtils.Tow(data.getFreightMoney() + ""));

    }

    @OnClick({R.id.title_left_imageview, R.id.canle_order, R.id.agin_shop, R.id.go_pay, R.id.title_right_textview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.canle_order:
                DialogView dialogView2 = new DialogView(this, R.style.myDialog, "确定取消订单 ？", new DialogView.OnYesButtonListener() {

                    @Override
                    public void YesButtonListener() {
                        canleOrder(dataDetail.getOrderDetail().get(0).getOrderVirtualId());
                    }

                }, null);
                dialogView2.show();
                break;
            case R.id.agin_shop:
                aginShopBig(dataDetail.getOrderDetail().get(0).getOrderVirtualId());
                break;
            case R.id.go_pay:
                pay();
                break;
            case R.id.title_right_textview:
                Intent intent1 = new Intent();
                intent1.putExtra("orderDetailId", dataDetail.getPkId());
                intent1.putExtra("orderNum", orderNumber.getText().toString());
                intent1.putExtra("orderStatus", orderState.getText().toString());
                intent1.putExtra("orderVirtualId", dataDetail.getOrderDetail().get(0).getOrderVirtualId());
                intent1.putExtra("type", "2");
                intent1.setClass(OrderDetailOthersActivity.this, OrderLogActivity.class);
                startActivity(intent1);
                break;
        }

    }

    //立即付款
    private void pay() {
        OkGo.post(Urls.URL_GO_PAYMENT)//
                .tag(this)
                .params("orderVirtualId", orderVirtualId)
                .execute(new DialogCallback<LzyResponse<SumbitOk>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<SumbitOk> lzyResponse, Call call, Response response) {
                                 if (lzyResponse.code == 200) {
                                     Intent mIntent = new Intent(OrderDetailOthersActivity.this, MyCheckStandActivity.class);
                                     Bundle mBundle = new Bundle();
                                     mBundle.putSerializable("subok", lzyResponse.data);
                                     mIntent.putExtra("t", "1");
                                     mIntent.putExtras(mBundle);
                                     startActivity(mIntent);
                                 } else {
                                     T.showShort(OrderDetailOthersActivity.this, lzyResponse.info);
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                             }
                         }
                );
    }

    //取消订单
    //待付款取消订单
    private void canleOrder(final String orderVirtualId) {
        OkGo.post(Urls.URL_ORDER_RETURN_ANCEL)//
                .tag(this)
                .params("orderVirtualId", orderVirtualId)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 if (lzyResponse.code == 200) {

                                     initData(orderVirtualId);
                                 } else {
                                     T.showShort(OrderDetailOthersActivity.this, "取消订单失败");
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(OrderDetailOthersActivity.this, e.getMessage());
                             }
                         }
                );
    }

    //再次购买
    Data angindata;

    private void aginShopBig(String orderVirtualId) {
        OkGo.post(Urls.URL_ORDER_AGAIN)//
                .tag(this)
                .params("orderVirtualId", orderVirtualId)
                .execute(new StringDialogCallback(OrderDetailOthersActivity.this, true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 angindata = (Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if (angindata.code == 200) {
                                     IntentUtils.startActivity(OrderDetailOthersActivity.this, MainActivity.class);
                                     MyApplication.setTag(3);
                                     MyApplication.setType(1);
                                 } else {

                                     if(!TextUtils.isEmpty(angindata.getInfo())){
                                         T.showShort(OrderDetailOthersActivity.this,angindata.getInfo());

                                     }
                                 }
                             }
                         }
                );
    }
}
