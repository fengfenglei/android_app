package com.mhl.shop.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.login.myview.ClearEditText;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.been.LoginData;
import com.mhl.shop.utils.CountDownTimerUtils;
import com.mhl.shop.utils.MD5Util;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.UIUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 16:34
 * 描述：修改支付密码
 */
public class EditPayPwdActivity extends MyBaseActivity  implements View.OnClickListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.deit_pay_pwd_username)
    TextView deit_pay_pwd_username;
    @Bind(R.id.edit_pay_input_code)
    ClearEditText edit_pay_input_code;
    @Bind(R.id.edit_pay_code)
    Button edit_pay_code;
    @Bind(R.id.edit_pay_pwd_new_pwd)
    ClearEditText edit_pay_pwd_new_pwd;
    @Bind(R.id.edit_pay_pwd_new_pwd_again)
    ClearEditText edit_pay_pwd_new_pwd_again;
    @Bind(R.id.edit_pay_pwd_submit)
    Button edit_pay_pwd_submit;
    private Intent intent;
    private String   userName;//用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pay_pwd);
        ButterKnife.bind(this);
        titleCenterTextview.setText("修改支付密码");
        init();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void init() {
        edit_pay_input_code.addTextChangedListener(new myTextWatcher());
        edit_pay_pwd_new_pwd.addTextChangedListener(new myTextWatcher());
        edit_pay_pwd_new_pwd_again.addTextChangedListener(new myTextWatcher());
        intent = getIntent();
//        adressId = intent.getStringExtra("loginName");
//        if ((BaseApplication.getApplication().isLogin()))
//        {
//            userName = BaseApplication.getApplication().getLoginUser().getMobile();
//            userName = intent.getStringExtra("loginName");
        userName= LoginData.getLoginName();
            deit_pay_pwd_username.setText(userName + ",您好");
//        }
    }

    @OnClick({R.id.title_left_imageview, R.id.edit_pay_code, R.id.edit_pay_pwd_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.edit_pay_code:// 获取验证码
                getCodeTxt();
                break;
            case R.id.edit_pay_pwd_submit:// 提交支付密码
                payPwdCommit();

                break;
        }
    }
    /**
     * 获取验证码
     */
    private void getCodeTxt()
    {

        String time= UIUtils.isTime();
        String  s= "mobi="+userName+"&time="+time;
        String  signafter= MD5Util.encrypt(s);
        String  sign= signafter.substring(signafter.length()-6,signafter.length());


        OkGo.post(Urls.URL_PWD_SEND_LOGIN_CODE)//
                .tag(this)
                .params("mobi",userName)
                .params("time",time)
                .params("sign",sign)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(EditPayPwdActivity.this,  "已发送验证码");
                                     final CountDownTimerUtils timerUtils = new CountDownTimerUtils(60000, 1000, edit_pay_code, "重新获取验证码");
                                     timerUtils.start();
                                 }else{
                                     T.showShort(EditPayPwdActivity.this, lzyResponse.info);
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
     * 提交修改支付密码
     */

    private void payPwdCommit()
    {
        String newPwd = edit_pay_pwd_new_pwd.getText().toString().trim();
        String newPwdAgain = edit_pay_pwd_new_pwd_again.getText().toString().trim();
        String code = edit_pay_input_code.getText().toString().trim();
        if (newPwd.length() != 6)
        {
            T.showShort(this, "请输入6位数字支付密码");
            return;
        }

        if (!newPwd.equals(newPwdAgain))
        {
            T.showShort(this, "两次输入密码不一致");
            return;
        }
        OkGo.post(Urls.URL_PWD_EDIT)//
                .tag(this)
                .params("mobileCode",code)
                .params("payPwd",newPwd)
                .params("payPwdConfirm",newPwdAgain)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(EditPayPwdActivity.this,  "修改成功");
                                     finish();
                                 }else{
                                     T.showShort(EditPayPwdActivity.this, lzyResponse.info);
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
     * 监听输入框变化
     */
    class myTextWatcher implements TextWatcher
    {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            String code = edit_pay_input_code.getText().toString().trim();
            String newPwd = edit_pay_pwd_new_pwd.getText().toString().trim();
            String newPwdAgain = edit_pay_pwd_new_pwd_again.getText().toString().trim();
            if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(newPwdAgain))
            {
                // 当输入框都不为空时按钮变红色才能点
                edit_pay_pwd_submit.setBackgroundResource(R.drawable.button_bg_selector);
                edit_pay_pwd_submit.setOnClickListener(EditPayPwdActivity.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                edit_pay_pwd_submit.setBackgroundResource(R.drawable.btn_grey);
                edit_pay_pwd_submit.setOnClickListener(null);
            }

        }

    }
}
