package com.mhl.shop.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.login.myview.ClearEditText;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.utils.CountDownTimerUtils;
import com.mhl.shop.utils.MD5Util;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
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
 * 时间；2016-11-17 14:36
 * 描述：忘记密码
 */
public class LoginForgetActivity extends MyBaseActivity implements View.OnClickListener {

    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.tv_login_forget_phone)
    TextView tvLoginForgetPhone;
    @Bind(R.id.et_login_forget_phone)
    ClearEditText et_login_forget_phone;  // 手机号
    @Bind(R.id.et_login_forget_code)
    ClearEditText et_login_forget_code;  // 验证码
    @Bind(R.id.tv_login_forget_phone_code)
    Button tv_login_forget_phone_code;// 获取验证码
    @Bind(R.id.tv_login_forget_text)
    TextView tv_login_forget_text;// 验证码是否错误
    @Bind(R.id.btn_login_forget_next)
    Button btn_login_forget_next;// 下一步
    private String phone;
    private String code;
    private  boolean iscode =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        ButterKnife.bind(this);
        titleCenterTextview.setText("忘记密码");
        // 初始化控件
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
        tv_login_forget_phone_code.setOnClickListener(this);
        et_login_forget_code.addTextChangedListener(new myTextWatcher());// 实时监听输入框的变化
        et_login_forget_phone.addTextChangedListener(new myTextWatcher());//

    }

    /**
     * 监听输入框变化
     */
    class myTextWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phone = et_login_forget_phone.getText().toString();
            String code = et_login_forget_code.getText().toString();
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)) {
                // 当输入框都不为空时按钮变红色才能点
                btn_login_forget_next.setBackgroundResource(R.drawable.button_bg_selector);
                btn_login_forget_next.setOnClickListener(LoginForgetActivity.this);
            } else {
                // 当有输入框为空是按钮变灰并且无点击效果
                btn_login_forget_next.setBackgroundResource(R.drawable.btn_grey);
                btn_login_forget_next.setOnClickListener(null);
            }

            if (TextUtils.isEmpty(code)) {
                tv_login_forget_text.setVisibility(View.INVISIBLE);
            }
        }

    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login_forget_next:
                nextStep();  //是否下一步
                break;
            case R.id.tv_login_forget_phone_code:
                getMsgCode();
                break;

        }

    }

    /**
     * 找回密码下一步
     */

    private void nextStep() {
        phone = et_login_forget_phone.getText().toString();
        code = et_login_forget_code.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            T.showShort(this, "请输入手机号");
            return;
        }
        if (!ToolsUtils.isMobileNO(phone)) {
            T.showShort(this, "手机号格式错误");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            T.showShort(this, "请输入验证码");
            return;
        }
        if(iscode){//检查是否去获取过验证码
            nextStepSetting(phone, code);
        }else {
            T.showShort(this, "请点击获取验证码");
            return;
        }
    }

    private void nextStepSetting(final String phone, final String code) {

        OkGo.post(Urls.URL_PWD_SEND_CODE_NEXT)//
                .tag(this)
                .params("mobi",phone)
                .params("vcode",code)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     Intent settingPwd = new Intent(LoginForgetActivity.this, SettingPwdActivity.class);
                                     settingPwd.putExtra("phone", phone);
                                     settingPwd.putExtra("code", code);
                                     startActivity(settingPwd);
                                 }else{
                                     T.showShort(LoginForgetActivity.this, lzyResponse.info);
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
     * 获取短信验证码
     */
    private void getMsgCode() {
        String phones = et_login_forget_phone.getText().toString().trim();

        if (TextUtils.isEmpty(phones)) {
            T.showShort(this,
                    "请输入手机号");
            return;
        }
        if (!ToolsUtils.isMobileNO(phones)) {
            T.showShort(this, "手机号格式错误");
            return;
        }

        String time= UIUtils.isTime();
        String  s= "mobi="+phones+"&time="+time;
        String  signafter= MD5Util.encrypt(s);
        String  sign= signafter.substring(signafter.length()-6,signafter.length());
        iscode=true;

        OkGo.post(Urls.URL_PWD_SEND_CODE)//
                .tag(this)
                .params("mobi",phones)
                .params("time",time)
                .params("sign",sign)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     CountDownTimerUtils timerUtils = new CountDownTimerUtils(60000, 1000, tv_login_forget_phone_code, "重新获取");
                                     timerUtils.start();
                                     T.showShort(LoginForgetActivity.this,  "已发送验证码");
//                                     finish();
                                 }else{
                                     T.showShort(LoginForgetActivity.this, lzyResponse.info);
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
