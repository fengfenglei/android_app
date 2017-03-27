package com.mhl.shop.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.home.been.MobileMoney;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.PayType;
import com.mhl.shop.shopdetails.MyCheckStandActivity;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.UIUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-2-7 15:20
 * 描述：话费充值
 */
public class PrepaidrechargeActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.put_edit)
    EditText putEdit;
    @Bind(R.id.singleChoose)
    MultiLineChooseLayout singleChoose;
    @Bind(R.id.accounts_payable)
    TextView accountsPayable;
    @Bind(R.id.belonging_to_tv)
    TextView belongingToTv;
    private List<String> mDataList = new ArrayList<>();
    private List<MobileMoney> list;
    private String  amount;
    private String sucpay="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepaid_recharge);
        ButterKnife.bind(this);
        initView();
        initData();
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
        titleCenterTextview.setText("话费充值");
        putEdit.addTextChangedListener(tbxSearch_TextChanged);
        //单选
        singleChoose.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
//                singleChooseTv.setText("结果：position: " + position + "   " + text);
                for (int j = 0; j <list.size(); j++) {
                 if((list.get(j).getRecharge()+"").equals(text.replace("元",""))){
                     accountsPayable.setText(list.get(j).getPay()+""+"元");
                      sucpay=list.get(j).getPay()+"";
                     amount=text.replace("元","");
                    }
                }
            }
        });
    }

    private void initData() {
        OkGo.post(Urls.URL_MOBILE_BILLINFO)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<MobileMoney>>>(PrepaidrechargeActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<List<MobileMoney>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     list= lzyResponse.data;
                                     for (int j = 0; j < lzyResponse.data.size(); j++) {
                                         mDataList.add(lzyResponse.data.get(j).getRecharge()+""+"元");
                                         singleChoose.setList(mDataList);
                                     }
                                 } else {
                                     T.showShort(PrepaidrechargeActivity.this, lzyResponse.info);

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
    /**
     * 关键字输入框文本变化监听
     */
    private TextWatcher tbxSearch_TextChanged = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            // 手机号码（段）
            String phoneSec = putEdit.getText().toString().trim();

            if (phoneSec.length() == 11) {
                // 查询手机号码（段）信息
                if (!UIUtils.isMobile(putEdit.getText().toString())) {
                    T.show(PrepaidrechargeActivity.this,
                            "手机号码不正确请重新输入！", Toast.LENGTH_SHORT);
                    return;
                }
                getPhoneSec(phoneSec);
            }else{
                belongingToTv.setText("");
            }

        }
       Data mobileString;
        private void getPhoneSec(String phoneSec) {
            OkGo.post(Urls.URL_MOBILE_ATTRIBUTION)
                    .tag(this)
                    .params("mobile", phoneSec)
                    .execute(new StringDialogCallback(PrepaidrechargeActivity.this,false) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            mobileString=(Data) GsonUtils.fromJson(s,
                                    Data.class);
                            if(mobileString.getCode()==200){
                                String a[] = mobileString.getData().split("\\|");
                                belongingToTv.setText(a[1]+a[2]);
                            }

                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
//                            T.showShort(PrepaidrechargeActivity.this, "用户或密码错误");
                        }
                    });

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    @OnClick({R.id.title_left_imageview, R.id.recharge_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.recharge_btn:
                recharge();
                break;
        }
    }

    private void recharge() {
        if(TextUtils.isEmpty(putEdit.getText().toString())){
            T.showShort(PrepaidrechargeActivity.this, "请输入要充值的电话号码");
            return;
        }
        if (!UIUtils.isMobile(putEdit.getText().toString())) {
            T.show(PrepaidrechargeActivity.this,"手机号码不正确请重新输入！", Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(amount)){
            T.showShort(PrepaidrechargeActivity.this, "请选择充值金额");
            return;
        }

        OkGo.post(Urls.URL_MOBILE_BILL)//
                .tag(this)
                .params("mobile",putEdit.getText().toString().trim())
                .params("amount",amount)
                .execute(new DialogCallback<LzyResponse<SumbitOk>>(PrepaidrechargeActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<SumbitOk> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     Intent mIntent = new Intent(PrepaidrechargeActivity.this,MyCheckStandActivity.class);
                                     Bundle mBundle = new Bundle();
                                     mBundle.putSerializable("subok",lzyResponse.data);
                                     mIntent.putExtra("t", "4");
                                     mIntent.putExtras(mBundle);
                                     startActivity(mIntent);
                                     PayType.setPhone(putEdit.getText().toString().trim());
                                     PayType.setAddress(belongingToTv.getText().toString().trim());
                                     PayType.setMoney(sucpay);
                                 } else {
                                     T.showShort(PrepaidrechargeActivity.this, lzyResponse.info);
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
