package com.mhl.shop.shopdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.homepage.RechargeSuccessActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.PayType;
import com.mhl.shop.me.OrderMainActivity;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.shopdetails.been.pay;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-28 15:31
 * 描述：收银台
 */
public class MyCheckStandActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.all_money_tv)
    TextView allMoneyTv;
    @Bind(R.id.all_money_ly)
    LinearLayout allMoneyLy;
    @Bind(R.id.coupons_money_tv)
    TextView couponsMoneyTv;
    @Bind(R.id.coupons_money_ly)
    LinearLayout couponsMoneyLy;
    @Bind(R.id.hld_money_tv)
    TextView hldMoneyTv;
    @Bind(R.id.hld_money_ly)
    LinearLayout hldMoneyLy;
    @Bind(R.id.deposit_money_tv)
    TextView depositMoneyTv;
    @Bind(R.id.deposit_money_ly)
    LinearLayout depositMoneyLy;
    @Bind(R.id.money_now_tv)
    TextView moneyNowTv;
    @Bind(R.id.mhl_checkout_wx_icon)
    ImageView mhlCheckoutWxIcon;
    @Bind(R.id.wx_pay_lay)
    RelativeLayout wxPayLay;
    @Bind(R.id.mhl_checkout_alipay_icon)
    ImageView mhlCheckoutAlipayIcon;
    @Bind(R.id.alipay)
    RelativeLayout alipay;
    private SumbitOk mSubok;
    private String t;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public static final String APP_ID = "wx1c24b0898474d4c6";
    private IWXAPI api;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stand);
        ButterKnife.bind(this);
        initView();
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
        api = WXAPIFactory.createWXAPI(MyCheckStandActivity.this, APP_ID, true);
        api.registerApp(APP_ID);
         mSubok = (SumbitOk) getIntent().getSerializableExtra("subok");
        titleCenterTextview.setText("收银台");
        Intent intent=getIntent();
         t=intent.getStringExtra("t");
        if(mSubok!=null){
            if(mSubok.getCouponMoney()>0){
                couponsMoneyLy.setVisibility(View.VISIBLE);
                couponsMoneyTv.setText("¥"+ ToolsUtils.Tow(mSubok.getCouponMoney()+""));
            }else {
                couponsMoneyLy.setVisibility(View.GONE);
            }

            if(mSubok.getActivationMoney()>0){
                hldMoneyLy.setVisibility(View.VISIBLE);
                hldMoneyTv.setText("¥"+ ToolsUtils.Tow(mSubok.getActivationMoney()+""));
            }else {
                hldMoneyLy.setVisibility(View.GONE);
            }
            if(mSubok.getDepositMoney()>0 ){
                depositMoneyLy.setVisibility(View.VISIBLE);
                depositMoneyTv.setText("¥"+ ToolsUtils.Tow(mSubok.getDepositMoney()+""));
            }else {
                depositMoneyLy.setVisibility(View.GONE);
            }
            moneyNowTv.setText("¥"+ ToolsUtils.Tow(mSubok.getPayMoney()+""));
            allMoneyTv.setText("¥"+ ToolsUtils.Tow(mSubok.getTotalPrice()+""));
        }
    }

    @OnClick({R.id.title_left_imageview, R.id.wx_pay_lay, R.id.alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.wx_pay_lay://微信支付
                paywx();
                PayType.setT(t);
                break;
            case R.id.alipay://支付宝支付
                pay();
                PayType.setT(t);
                break;
        }
    }

    private void paywx() {
        OkGo.post(Urls.URL_ORDER_WXPAY)
                .tag(this)
                .params("o",mSubok.getOrderVirtualId())
                .params("t",t)
                .execute(new DialogCallback<LzyResponse<pay>>(this,true) {
                             @Override
                             public void onSuccess(LzyResponse<pay> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                     if(lzyResponse.code==200){
                                         PayReq req = new PayReq();
                                         req.appId = APP_ID;
                                         req.partnerId = lzyResponse.data.getPartnerid();
                                         req.prepayId = lzyResponse.data.getPrepayid();
                                         req.packageValue = lzyResponse.data.getPackageValue();
                                         req.nonceStr = lzyResponse.data.getNoncestr();
                                         req.timeStamp = lzyResponse.data.getTimestamp();
                                         req.sign =lzyResponse.data.getSign();
                                         api.sendReq(req);
                                         finish();
                                     }else {
                                         T.showShort(MyCheckStandActivity.this,"提交异常");
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

    String orderInfo = "";   // 订单信息
    Data data;
    private void pay() {
        OkGo.post(Urls.URL_ORDER_ALIPAY)//
                .tag(this)
                .params("o",mSubok.getOrderVirtualId())
                .params("t",t)
                .execute(
                        new StringDialogCallback(MyCheckStandActivity.this, true) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                data=(Data) GsonUtils.fromJson(s,
                                        Data.class);
                                if(data.getCode()==200){
                                    orderInfo=data.getData();
                                    Runnable payRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(MyCheckStandActivity.this);
                                            Map<String, String> result = alipay.payV2(orderInfo, true);
                                            Log.d("123456", "orderInfo====" + orderInfo);
                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();
                                }else{
                                    if(!TextUtils.isEmpty(data.getInfo())){
                                        T.showShort(MyCheckStandActivity.this,data.getInfo());

                                    }
                                }
                            }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                handleError(call, response, e);
                            }
                        });


    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if(PayType.getT().equals("1")){
                            Intent intent = new Intent();
                            intent.putExtra(Constant.ORDER_STATE, Constant.ALL_ORDER);
                            intent.setClass(MyCheckStandActivity.this, OrderMainActivity.class);
                            startActivity(intent);
                            Toast.makeText(MyCheckStandActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        }else if(PayType.getT().equals("4")){
                            Intent mIntent = new Intent(MyCheckStandActivity.this,RechargeSuccessActivity.class);
                            startActivity(mIntent);
                        }else if(PayType.getT().equals("3")){
                            Toast.makeText(MyCheckStandActivity.this, "余额充值成功", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }else if (TextUtils.equals(resultStatus, "4000")) {
                        Toast.makeText(MyCheckStandActivity.this, "支付订单失败", Toast.LENGTH_SHORT).show();


                    }  else if (TextUtils.equals(resultStatus, "6001")) {
                        daiFu("用户取消支付");
                    }   else if (TextUtils.equals(resultStatus, "6002")) {
                        Toast.makeText(MyCheckStandActivity.this, "网络连接出错", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        daiFu("正在处理中");
                    } else if (TextUtils.equals(resultStatus, "6004")) {
                        daiFu("正在处理中");
                    }else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        daiFu("支付错误");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void daiFu(String msg) {
        Toast.makeText(MyCheckStandActivity.this, msg, Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.putExtra(Constant.ORDER_STATE, Constant.DAIFUKUAI_ORDER);
        intent1.setClass(MyCheckStandActivity.this, OrderMainActivity.class);
        startActivity(intent1);
    }

}
