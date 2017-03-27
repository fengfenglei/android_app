package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.CheckOrderLogAdapter;
import com.mhl.shop.me.been.OrderLog;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-19 17:58
 * 描述：订单日志
 */
public class OrderLogActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.check_log_info)
    ListView checkLogInfo;
    private CheckOrderLogAdapter adapter;
    private String orderDetailId,orderNum,orderStatus,orderVirtualId,type;
    private List<OrderLog> list;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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
        titleCenterTextview.setText("订单日志");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        orderVirtualId = intent.getStringExtra("orderVirtualId");
        orderDetailId = intent.getStringExtra("orderDetailId");
        orderNum = intent.getStringExtra("orderNum");
        orderStatus = intent.getStringExtra("orderStatus");
        orderNumber.setText(orderNum);
        orderState.setText(orderStatus);
        if(type.equals("1")){
            initData(orderDetailId);
        }else {
            initData(orderVirtualId);
        }
    }

    //获取订单日志信息
    private void initData(String orderDetailId) {
        String URL;
        String key;
        if(type.equals("1")){
            URL=Urls.URL_ORDER_LOG;
            key="orderDetailId";
        }else {
            URL=Urls.URL_ORDERVIRTUAL_LOG;
            key="orderVirtualId";
        }
        OkGo.post(URL)//
                .tag(this)
                .params(key, orderDetailId)
                .execute(new DialogCallback<LzyResponse<List<OrderLog>>>(OrderLogActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<List<OrderLog>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     if (adapter == null) {
                                         list=lzyResponse.data;
                                         adapter = new CheckOrderLogAdapter(OrderLogActivity.this, list);
                                         checkLogInfo.setAdapter(adapter);
                                     } else {
                                         adapter.notifyDataSetChanged();
                                     }
                                 } else {
                                     T.showShort(OrderLogActivity.this, lzyResponse.info);

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

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
