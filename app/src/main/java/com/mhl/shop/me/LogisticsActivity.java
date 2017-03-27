package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.CheckOrderLogisticsAdapter;
import com.mhl.shop.me.been.OrderLogistics;
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
 * 时间；2017-1-22 16:50
 * 描述：物流
 */
public class LogisticsActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.check_logistics)
    ListView checkLogistics;
    private String pkId,type;
    CheckOrderLogisticsAdapter adapter;
    private List<OrderLogistics.ExpressProgressBean> list;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);
        initView();
        initData();
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
        titleCenterTextview.setText("查看物流");
        Intent intent = getIntent();
        pkId = intent.getStringExtra("pkId");
        type = intent.getStringExtra("type");//1为正常商品物流  2为退货物流
    }
    //获取物流信息
    private void initData() {
        String URL="";
        if(type.equals("1")){
            URL=Urls.URL_PROGRESS_FOLLOWING;
        }else  if(type.equals("2")){
            URL=Urls.URL_RETURN_FOLLOWING;
        }
        OkGo.post(URL)//
                .tag(this)
//              .params("e", orderExpressId)//订单物流ID
                .params("o", pkId)//订单ID
                .execute(new DialogCallback<LzyResponse<OrderLogistics>>(LogisticsActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<OrderLogistics> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     if (adapter == null) {
                                         if(lzyResponse.data!=null) {
                                             list = lzyResponse.data.getExpressProgress();

                                             Glide.with(LogisticsActivity.this).load(Urls.URL_PHOTO + "/file/v4/download-" + lzyResponse.data.getLogo() + "-240-240.jpg").placeholder(R.drawable.icon_bg).into(icon);
                                             name.setText("物流公司：" + lzyResponse.data.getExpressCompany());
                                             number.setText("物流编号：" + lzyResponse.data.getExpressNo());
                                             String s = "物流单号有误或暂无物流信息";
                                             if(lzyResponse.data.getState()!=null){
                                                 if (lzyResponse.data.getState().equals("0")) {
                                                     s = "在途中";
                                                 } else if (lzyResponse.data.getState().equals("1")) {
                                                     s = "已揽收";
                                                 } else if (lzyResponse.data.getState().equals("2")) {
                                                     s = "疑难";
                                                 } else if (lzyResponse.data.getState().equals("3")) {
                                                     s = "已签收";
                                                 } else if (lzyResponse.data.getState().equals("4")) {
                                                     s = "退签";
                                                 } else if (lzyResponse.data.getState().equals("5")) {
                                                     s = "同城派送中";
                                                 } else if (lzyResponse.data.getState().equals("6")) {
                                                     s = "退回";
                                                 } else if (lzyResponse.data.getState().equals("7")) {
                                                     s = "转单";
                                                 }
                                             }else {
                                                 state.setText("物流状态：" + s);

                                             }

                                             if (list != null) {
                                                 adapter = new CheckOrderLogisticsAdapter(LogisticsActivity.this, list);
                                                 checkLogistics.setAdapter(adapter);
                                             }
                                         }
                                     } else {
                                         adapter.notifyDataSetChanged();
                                     }
                                 } else {
                                     T.showShort(LogisticsActivity.this, lzyResponse.info);

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
