package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.shopdetails.MyCheckStandActivity;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-18 16:45
 * 描述：充值
 */
public class RechargeActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.et_money)
    EditText etMoney;
    @Bind(R.id.bt_save)
    Button btSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        titleCenterTextview.setText("账户充值");
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
    @OnClick({R.id.title_left_imageview, R.id.bt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.bt_save:
                if(TextUtils.isEmpty(etMoney.getText().toString())){
                    Toast.makeText(RechargeActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(etMoney.getText().toString())>50000){
                    Toast.makeText(RechargeActivity.this, "最多充值50000元", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(etMoney.getText().toString())<100){
                    Toast.makeText(RechargeActivity.this, "最少充值100元", Toast.LENGTH_SHORT).show();
                    return;
                }

             if(Integer.parseInt(etMoney.getText().toString())>0){
                 // 充值金额
                 OkGo.post(Urls.URL_RECHARGE_APP)//
                         .tag(this)
                         .params("amount",etMoney.getText().toString())
                         .params("remark","")
                         .execute(new DialogCallback<LzyResponse<SumbitOk>>(RechargeActivity.this, true) {
                                      @Override
                                      public void onSuccess(LzyResponse<SumbitOk> lzyResponse, Call call, Response response) {
                                          handleResponse(lzyResponse, call, response);
                                          if (lzyResponse.code == 200) {
                                              Intent mIntent = new Intent(RechargeActivity.this,MyCheckStandActivity.class);
                                              Bundle mBundle = new Bundle();
                                              mBundle.putSerializable("subok",lzyResponse.data);
                                              mIntent.putExtra("t", "3");
                                              mIntent.putExtras(mBundle);
                                              startActivity(mIntent);
                                              finish();
                                          } else {
                                              T.showShort(RechargeActivity.this, lzyResponse.info);
                                          }
                                      }
                                      @Override
                                      public void onError(Call call, Response response, Exception e) {
                                          super.onError(call, response, e);
                                          handleError(call, response, e);
                                      }
                                  }
                         );
            } else {

             }


                break;
        }
    }
}
