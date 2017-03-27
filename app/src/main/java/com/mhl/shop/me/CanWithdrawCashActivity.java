package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.been.Authentication;
import com.mhl.shop.finance.been.Balance;
import com.mhl.shop.finance.been.TransactionRecord;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.me.adapter.CanwithdrawAdpter;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-18 14:24
 * 描述：可提现金额
 */
public class CanWithdrawCashActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.ll_go_pay)
    LinearLayout llGoPay;
    @Bind(R.id.ll_acount_balance_withdraw)
    LinearLayout llAcountBalanceWithdraw;
    @Bind(R.id.lv_data)
    AllListView lvData;
    @Bind(R.id.me_my_zero)
    LinearLayout meMyZero;
    private List<TransactionRecord.RowsBean> list;
    private CanwithdrawAdpter adapter;
    private int page=1;//加载的页数
    private String availableBalance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canwithdraw);
        ButterKnife.bind(this);
        initViews();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initData(true,page);
        newBalance();
    }

    private void initViews() {
        titleCenterTextview.setText("可提现金额");
        titleRightImageview.setVisibility(View.VISIBLE);
        titleRightImageview.setBackgroundResource( R.drawable.icon_problem);
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
        Intent intent=getIntent();
        availableBalance= intent.getStringExtra("AvailableBalance");
//        tvBalance.setText("¥"+availableBalance);
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
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_RECORD_FUND)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .params("fundFlows", "")//0-支出 1-收入
                .params("fundType", "1")//1-可提现余额 4-赠送金额 6-激活金额
                .params("period", "")//7-最近一周 30-最近一个月 365-最近一年
                .params("userRole", "1")//1-会员 2-服务站 3-运营中心 4-供应商
                .execute(new DialogCallback<LzyResponse<TransactionRecord>>(CanWithdrawCashActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<TransactionRecord> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             meMyZero.setVisibility(View.VISIBLE);
                                             lvData.setVisibility(View.GONE);
                                         } else {
                                             adapter = new CanwithdrawAdpter(CanWithdrawCashActivity.this, list);
                                             lvData.setAdapter(adapter);
                                             lvData.setOnRefreshComplete();
                                             lvData.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(CanWithdrawCashActivity.this,"没有更多数据了");
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
    private void newBalance() {
        // 刷新金额
        OkGo.post(Urls.URL_USER_BALANCE)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Balance>>(CanWithdrawCashActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<Balance> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.data!=null){
                                     availableBalance= ToolsUtils.Tow(responseData.data.getAvailableBalance()+"");
                                     tvBalance.setText("¥"+ ToolsUtils.Tow(responseData.data.getAvailableBalance()+""));
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
    @OnClick({R.id.title_left_imageview, R.id.title_right_imageview, R.id.ll_acount_balance_withdraw,R.id.ll_go_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_imageview:
//                IntentUtils.startActivity(this, HelpCenterActivity.class);
                Intent intent4 = new Intent(this,WebActivity.class);
                intent4.putExtra(Constant.TITLE, "可提现金额说明");
                intent4.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/cus-deposit-detail.html");
                startActivity(intent4);
                break;
            case R.id.ll_acount_balance_withdraw://提现
                isAuthentication();
                break;
            case R.id.ll_go_pay://去充值
                IntentUtils.startActivity(this,RechargeActivity.class);


                break;
        }
    }

    private void isAuthentication() {
        // APP获取用户认证状态
        OkGo.post(Urls.URL_AUTHENTICATION_STATUS)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Authentication>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Authentication> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.data!=null){
                                     // 是否认证|(--1已认证--2未认证--3审核中--4审核失败) ,
                                     if(responseData.data.getAuthenticationFlag()==1){
                                         Intent Intent = new Intent(CanWithdrawCashActivity.this, CashWithdrawalActivity.class);
                                         Intent.putExtra("AvailableBalance",availableBalance);
                                         startActivity(Intent);
                                     }else if(responseData.data.getAuthenticationFlag()==2){
                                         new AlertDialog(CanWithdrawCashActivity.this).builder().setTitle("温馨提示")
                                                 .setMsg("您还没有通过认证，通过认证才能使用提现功能（所有老用户因公安系统均需重新在线认证）")
                                                 .setPositiveButton("立即认证", new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         IntentUtils.startActivity(CanWithdrawCashActivity.this, CertificationActivity.class);
                                                     }
                                                 }).setNegativeButton("取消", new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                             }
                                         }).show();
                                     }
                                     else if(responseData.data.getAuthenticationFlag()==3){
                                         new AlertDialog(CanWithdrawCashActivity.this).builder().setTitle("温馨提示")
                                                 .setMsg("您已提交实名信息，我们将尽快为您审核，预计时间1-3个工作日，请您耐心等待，审核通过后才能进行提现操作")
                                            .setPositiveButton("我知道了", new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                             }
                                         }).show();

                                     }else if(responseData.data.getAuthenticationFlag()==4){
                                         new AlertDialog(CanWithdrawCashActivity.this).builder().setTitle("温馨提示")
                                                 .setMsg("您还没有通过认证，通过认证才能使用提现功能（所有老用户因公安系统均需重新在线认证）")
                                                 .setPositiveButton("立即认证", new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         IntentUtils.startActivity(CanWithdrawCashActivity.this, CertificationActivity.class);
                                                     }
                                                 }).setNegativeButton("取消", new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                             }
                                         }).show();
                                     }

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
