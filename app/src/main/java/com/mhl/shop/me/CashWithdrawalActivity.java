package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.AddEditBankActivity;
import com.mhl.shop.finance.been.Bank;
import com.mhl.shop.finance.been.SecurityCenterStatus;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.SettingPayPwdActivity;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.me.myview.PayPasswordView;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.DialogWidget;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：LFF
 * 时间；2016-11-18 17:48
 * 描述：提现
 */
public class CashWithdrawalActivity extends MyBaseActivity implements View.OnClickListener{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.money_et)
    EditText money_et;
    @Bind(R.id.tx_btn)
    Button tx_btn;
    @Bind(R.id.my_ban)
    TextView my_ban;
    @Bind(R.id.card_name)
    TextView card_name;
    @Bind(R.id.card_num)
    TextView card_num;
    @Bind(R.id.no_card_ly)
    LinearLayout no_card_ly;
    @Bind(R.id.have_card_ly)
    LinearLayout have_card_ly;
    private String all_money;//可提现金额
    private boolean isPayPassword=false;//是否设置过支付密码
    private DialogWidget mDialogWidget;
    private Bank bankData;
    private String bankCardId;//绑定银行卡ID
    private String type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);

    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initViews();
        initData();
    }
    private void initData() {
        OkGo.post(Urls.URL_BANK_INFO)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Bank>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Bank> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.data!=null){
                                     bankData=responseData.data;
                                     no_card_ly.setVisibility(View.GONE);
                                     have_card_ly.setVisibility(View.VISIBLE);
                                     card_name.setText(responseData.data.getBankName());
                                     card_num.setText(responseData.data.getBankCardCode());
                                     bankCardId=responseData.data.getPkId()+"";
                                     type="edit";
                                 }else {
                                     no_card_ly.setVisibility(View.VISIBLE);
                                     have_card_ly.setVisibility(View.GONE);
                                     type="add";
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

    private void initViews() {
        titleCenterTextview.setText("账户提现");
        titleRightTextview.setText("提现规则");
        titleRightTextview.setVisibility(View.GONE);
        money_et.addTextChangedListener(textWatcher);
        Intent intent=getIntent();
        my_ban.setText(intent.getStringExtra("AvailableBalance"));
        money_et.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }
    @OnClick({R.id.title_right_textview,R.id.title_left_imageview, R.id.max_money, R.id.tx_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_textview:
                Intent intent3 = new Intent(CashWithdrawalActivity.this,WebActivity.class);
                intent3.putExtra(Constant.LBONCLICKURL, "http://www.51mdx.net/page/user/rule/appwithdrawalsrule.html");
                intent3.putExtra("title", "提现规则");
                startActivity(intent3);
                break;
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.max_money:
                Double d = Double.valueOf(my_ban.getText() + "");
                if (d < 100) {
                    T.showShort(this, "可提现金额不足100元");
                }else {
                    money_et.setText(my_ban.getText() + "");
                    money_et.setSelection(money_et.getText().length());
                }
//                if (d < 50000) {
//                    // int i = (new Double(d)).intValue();
//                    money_et.setText(my_ban.getText() + "");
//                    money_et.setSelection(money_et.getText().length());
//                } else {
//                    money_et.setText(50000 + "");
//                }
                break;
            case R.id.tx_btn:
                if (TextUtils.isEmpty(card_name.getText() + "")
                        || TextUtils.isEmpty(card_num.getText() + "")
                        || TextUtils.isEmpty(money_et.getText() + "")) {
                    T.showShort(this, "请完善信息");
                    return;
                }
              if(Double.parseDouble(money_et.getText().toString())+bankData.getCommissionCharge()<bankData.getAvailableBalance()){
                  if( Double.parseDouble(money_et.getText().toString())>bankData.getUpperLimit()){
                      T.showShort(this, "本次提现额度上限为人民币¥"+bankData.getUpperLimit()+"");
                      return;
                  }
                  if ( Double.parseDouble(money_et.getText().toString())<bankData.getLowerLimit()){
                      T.showShort(this, "本次提现额度下限为人民币¥"+bankData.getLowerLimit()+"");
                      return;
                  }
              }else
              {
                  if(Double.parseDouble(money_et.getText().toString())+bankData.getCommissionCharge()<bankData.getAvailableBalance()){
                      T.showShort(this, "扣除手续费¥"+bankData.getCommissionCharge()+""+"金额不足");
                      return;
                  }
                  if( Double.parseDouble(money_et.getText().toString())+bankData.getCommissionCharge()>bankData.getUpperLimit()){
                  T.showShort(this, "本次提现额度上限为人民币¥"+bankData.getUpperLimit()+"");
                  return;
                   }
                  if ( Double.parseDouble(money_et.getText().toString())+bankData.getCommissionCharge()<bankData.getLowerLimit()){
                      T.showShort(this, "本次提现额度下限为人民币¥"+bankData.getLowerLimit()+"");
                      return;
                  }

              }

                new AlertDialog(CashWithdrawalActivity.this).builder().setTitle("温馨提示")
                        .setMsg("您本次提现需要扣除手续费¥"+bankData.getCommissionCharge()+""+"确定要去提现吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isPayPassword();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


                break;
            default:
                break;
        }

    }

    private void isPayPassword() {
        //检测是否设置支付密码
        OkGo.post(Urls.URL_SECURITY_INFO)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<SecurityCenterStatus>>(CashWithdrawalActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<SecurityCenterStatus> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.data!=null){
                                     if (responseData.data.getPayPwdStatus()==1)// 已设置支付密码
                                     {
                                         // 打开密码框
                                         mDialogWidget = new DialogWidget(CashWithdrawalActivity.this, getDecorViewDialog());
                                         mDialogWidget.show();
                                     } else {// 未设置支付面码
                                         new AlertDialog(CashWithdrawalActivity.this).builder().setTitle("温馨提示")
                                                 .setMsg("您还没有设置支付密码，要去设置支付密码吗？")
                                                 .setPositiveButton("去设置", new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {Intent intent1 = new Intent();
                                                 intent1.setClass(CashWithdrawalActivity.this, SettingPayPwdActivity.class);
                                                 startActivity(intent1);
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

    public void addcard(View view) {
        Intent intent = new Intent(CashWithdrawalActivity.this,
                AddEditBankActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);

    }
    protected View getDecorViewDialog() {

        return PayPasswordView.getInstance(
                this,
                new PayPasswordView.OnPayListener() {
                    @Override
                    public void onSurePay(String password) {
                        commitCard(password);//提现
                    }
                    @Override
                    public void onCancelPay() {
                        mDialogWidget.dismiss();
                        mDialogWidget = null;
                    }
                    @Override
                    public void onBackPay() {
                        Toast.makeText(getApplicationContext(), "返回",
                                Toast.LENGTH_SHORT).show();
                        mDialogWidget.dismiss();
                        mDialogWidget = null;
                        finish();
                    }
                }).getView();

    }

    private void commitCard(String password) {
       // 提现
        OkGo.post(Urls.URL_WITHDRAWAL)//
                .tag(this)
                .params("bankCardId",bankCardId)//绑定银行卡ID
                .params("payPwd",password)
                .params("amount",money_et.getText().toString())
                .execute(new DialogCallback<LzyResponse<Null>>(CashWithdrawalActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<Null> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                          if(responseData.code==200){
                                              finish();

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

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            if(temp.length()>3){
//                    TextUtils.isTwo(Double.parseDouble(temp));
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            if (!TextUtils.isEmpty(s)) {
                double w = 0;
//                if (money_et.getText().toString().indexOf(".") >= 0) {
//                    if (money_et.getText().toString().indexOf(".", money_et.getText().toString().indexOf(".") + 1) > 0) {
//                        money_et.setText(money_et.getText().toString().substring(0, money_et.getText().toString().length() - 1));
//                        money_et.setSelection(money_et.getText().toString().length());
//                    }
//
//                }
                // double r = w / 100;
//                if (w > 50000) {
//                    Toast.makeText(getApplicationContext(), "一次提现金额最高为50000", Toast.LENGTH_SHORT).show();
//                    tx_btn.setBackgroundResource(R.color.button_color_pressed);
//                    tx_btn.setOnClickListener(null);
//                } else

                if((s+"").equals("00")){
                    tx_btn.setOnClickListener(null);
                    money_et.setText("0");
                }else
                if((s+"").equals(".")){
                    tx_btn.setOnClickListener(null);
                    money_et.setText("");
                }
                else {
                    w = Double.parseDouble(s + "");
                }
                if (w > Double.valueOf(my_ban.getText() + "")) {
                    Toast.makeText(getApplicationContext(), "您输入的金额大于可提现金额，请重新输入！", Toast.LENGTH_SHORT).show();
                    tx_btn.setBackgroundResource(R.drawable.btn_grey);
                    tx_btn.setOnClickListener(null);
                } else if (w < 100) {
                    tx_btn.setBackgroundResource(R.drawable.btn_grey);
                    tx_btn.setOnClickListener(null);
                } else if (Double.parseDouble(s + "") == 0.00) {
                    tx_btn.setBackgroundResource(R.drawable.btn_grey);
                    tx_btn.setOnClickListener(null);
                } else {
                    tx_btn.setBackgroundResource(R.drawable.button_bg_selector);
                    tx_btn.setOnClickListener(CashWithdrawalActivity.this);
                }
            } else {
                tx_btn.setBackgroundResource(R.drawable.btn_grey);
                tx_btn.setOnClickListener(null);
            }
            money_et.setSelection(money_et.getText().length());//将光标追踪到内容的最后

        }
    };

}
