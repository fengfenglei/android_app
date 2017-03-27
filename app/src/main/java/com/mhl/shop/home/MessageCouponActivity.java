package com.mhl.shop.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.home.adpter.MessageCouponAdapter;
import com.mhl.shop.home.adpter.MessageLogisticsAdapter;
import com.mhl.shop.homepage.bean.Message;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
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
 * 时间；2017-1-19 17:20
 * 描述：优惠券(和账户通知,物流共用)
 */
public class MessageCouponActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.lv_data)
    AllListView lvData;
    @Bind(R.id.me_my_zero)
    LinearLayout meMyZero;
    private int page=1;//加载的页数
    private String type;
    MessageCouponAdapter adapter;
    MessageLogisticsAdapter logisticsAdapter;
    List<Message> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mag_coupon);
        ButterKnife.bind(this);
        initView();

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void initView() {
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");//通知类型(--0全部--1物流通知--2账户提醒--3货郎活动--4优惠券)
        if(type.equals("2")){
            titleCenterTextview.setText("账户提醒");
        }else if(type.equals("4")){
            titleCenterTextview.setText("优惠券");
        }else if(type.equals("1")){
            titleCenterTextview.setText("物流通知");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(true,page);
        MobclickAgent.onResume(this);

    }

    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_INFO_NOTICE_LIST)//
                .tag(this)
                .params("page",page)
                .params("rows", 10)
                .params("msgType", type)//通知类型(--0全部--1物流通知--2账户提醒--3货郎活动--4优惠券)
                .execute(new DialogCallback<LzyResponse<List<Message>>>(MessageCouponActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<List<Message>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data;
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             meMyZero.setVisibility(View.VISIBLE);
                                             lvData.setVisibility(View.GONE);
                                         } else {
                                             if(type.equals("1")){
                                                 logisticsAdapter = new MessageLogisticsAdapter(MessageCouponActivity.this, list);
                                                 lvData.setAdapter(logisticsAdapter);
                                                 lvData.setOnRefreshComplete();
                                                 lvData.setSelection(0);
                                             }else {
                                             adapter = new MessageCouponAdapter(MessageCouponActivity.this, list);
                                             lvData.setAdapter(adapter);
                                             lvData.setOnRefreshComplete();
                                             lvData.setSelection(0);
                                             }
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.size()>0){
                                         list.addAll(lzyResponse.data);
                                         if(type.equals("1")){
                                             logisticsAdapter.notifyDataSetChanged();
                                         }else {
                                             adapter.notifyDataSetChanged();
                                         }

                                     }else {
//                                         T.showShort(MessageCouponActivity.this,"没有更多数据了");
                                     }
                                     lvData.hideFooterView();

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

    @Override
    public void onRefresh() {
        initData(false,1);
        page=1;
    }
    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }
}
