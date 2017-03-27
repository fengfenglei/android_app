package com.mhl.shop.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.been.TransactionDetails;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
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
 * 时间；2017-1-3 20:48
 * 描述：交易详情
 */
public class TransactionDetailsActivity extends MyBaseActivity {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.explain)
    TextView explain;
    @Bind(R.id.all_ly)
    LinearLayout allLy;
   private  String HldId="",typedetails;
    private  String TransactionRecordId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
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
        Intent intent=getIntent();
        HldId=intent.getStringExtra("HldId");//货郎豆传过来的
        TransactionRecordId=intent.getStringExtra("TransactionRecordId");//交易记录传过来的
        typedetails=intent.getStringExtra("typedetails");//交易记录传过来的
        titleCenterTextview.setText("交易详情");
    }
    private void initData(boolean b) {
        String url = null,id = null;
        if(typedetails.equals("Hld")){
            url=Urls.URL_RECORD_DETAIL;
            id=HldId;
        }else if(typedetails.equals("TransactionRecord")){
            url=Urls.URL_RECORD_FUND_DETAIL;
            id=TransactionRecordId;
        }
        OkGo.post(url)
                .tag(this)
                .params("recordId",id)
                .execute(new DialogCallback<LzyResponse<TransactionDetails>>(TransactionDetailsActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<TransactionDetails> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                  setData(lzyResponse.data);
                                 if(lzyResponse.data!=null){
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

    private void setData(TransactionDetails data) {
             money.setText(data.getChangeAmountStr());
        if(typedetails.equals("Hld")){
            type.setText(data.getOrderTypeStr());
        }else if(typedetails.equals("TransactionRecord")){
            type.setText(data.getFundTypeStr());
        }

             time.setText(ToolsUtils.dateToStampLit(data.getRecordTime()));
             number.setText(data.getRunningNo());
//             state.setText(data);
             explain.setText(data.getRemark());

    }



    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
