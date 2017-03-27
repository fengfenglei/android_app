package com.mhl.shop.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.been.ActivatedNoDetails;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.OrderDetailActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-5 19:59
 * 描述：待激活订单详情
 */
public class ActivatedNoDetailsActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.order_detail)
    TextView orderDetail;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.receiving_time)
    TextView receivingTime;
    @Bind(R.id.activation_time)
    TextView activationTime;
    @Bind(R.id.all_money)
    TextView allMoney;
    @Bind(R.id.activation_money)
    TextView activationMoney;
    @Bind(R.id.write_order_shop_icon)
    ImageView writeOrderShopIcon;
    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.guige)
    TextView guige;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.shop_count)
    TextView shopCount;
    @Bind(R.id.shop_details)
    LinearLayout shopDetails;
    @Bind(R.id.all_ly)
    LinearLayout allLy;
    private String ActivatedNoId = "";
    private ActivatedNoDetails datano;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activatedno_details);
        ButterKnife.bind(this);
        initView();
        initData(true);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void initView() {
        Intent intent = getIntent();
        ActivatedNoId = intent.getStringExtra("ActivatedNoId");//货郎豆传过来的
        titleCenterTextview.setText("待激活详情");
    }

    private void initData(boolean b) {
        OkGo.post(Urls.URL_ACTIVATION_DETAIL)
                .tag(this)
                .params("orderId", ActivatedNoId)
                .execute(new DialogCallback<LzyResponse<ActivatedNoDetails>>(ActivatedNoDetailsActivity.this, b) {
                             @Override
                             public void onSuccess(LzyResponse<ActivatedNoDetails> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                  datano= lzyResponse.data;
                                 setData(lzyResponse.data);
                                 if (lzyResponse.data != null) {
                                     allLy.setVisibility(View.VISIBLE);
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

    private void setData(ActivatedNoDetails data) {
            number.setText(data.getOrderNo());
        if (data.getOrderStatus() == 99) {
            state.setText("已取消");
        } else if (data.getOrderStatus() == 80) {
            state.setText("已失效");
        } else if (data.getOrderStatus() == 11) {
            state.setText("待付款");
        } else if (data.getOrderStatus() == 12) {
            state.setText("已付款");
        } else if (data.getOrderStatus() == 20) {
            state.setText("已付款");
        } else if (data.getOrderStatus() == 13) {
            state.setText("已发货");
        } else if (data.getOrderStatus() == 14) {
            state.setText("已收货");
        } else if (data.getOrderStatus() == 15) {
            state.setText("会员已评价");
        } else if (data.getOrderStatus() == 16) {
            state.setText("双方已评价");
        } else if (data.getOrderStatus() == 31) {
            state.setText("待供应商审核");
        } else if (data.getOrderStatus() == 32) {
            state.setText("待平台审核");
        } else if (data.getOrderStatus() == 41) {
            state.setText("待供应商审核");
        } else if (data.getOrderStatus() == 42) {
            state.setText("待会员退货");
        } else if (data.getOrderStatus() == 43) {
            state.setText("待供应商收货");
        } else if (data.getOrderStatus() == 44) {
            state.setText("待平台审核");
        } else if (data.getOrderStatus() == 61) {
            state.setText("退款完成");
        } else if (data.getOrderStatus() == 62) {
            state.setText("退货退款完成");
        }

        time.setText(ToolsUtils.dateToStampLit(data.getAddTime()));

        if(data.getConfirmTime()>0){
            receivingTime.setText(ToolsUtils.dateToStampLit(data.getConfirmTime()));
        }else {
            receivingTime.setText("--");
        }
        if(data.getExpectedActivationDate()>0){
            activationTime.setText(ToolsUtils.dateToStampLit(data.getExpectedActivationDate()));
        }else {
            activationTime.setText(data.getCanActivationDate());
        }

        allMoney.setText("¥"+ToolsUtils.Tow(data.getTotalPrice()+""));
        activationMoney.setText("¥"+ToolsUtils.Tow(data.getExpectedActivationMoney()+""));
        Glide.with(this).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getGoodsIdPic() + "-200-200.jpg"
        ).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.icon_bg).into(writeOrderShopIcon);
        shopName.setText(data.getGoodsIdName());
        guige.setText(data.getGoodsSpec());
        shopCount.setText("x"+data.getGoodsCount());
    }

    @OnClick({R.id.title_left_imageview, R.id.order_detail, R.id.shop_details})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.order_detail:
                Intent intent = new Intent();
                intent.putExtra("PkId", datano.getPkId());//PkId
                intent.setClass(ActivatedNoDetailsActivity.this, OrderDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_details:
                break;
        }
    }
}
