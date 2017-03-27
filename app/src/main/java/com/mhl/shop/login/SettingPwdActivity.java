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
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：LFF
 * 时间；2016-11-17 14:53
 * 描述：设置密码
 */
public class SettingPwdActivity extends MyBaseActivity implements View.OnClickListener {
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.et_login_setting_pwd)
    ClearEditText et_login_setting_pwd;
    @Bind(R.id.et_login_setting_pwd_again)
    ClearEditText et_login_setting_pwd_again;
    @Bind(R.id.tv_login_setting)
    TextView tv_login_setting;
    @Bind(R.id.btn_login_setting_pwd_commit)
    Button btn_login_setting_pwd_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pwd);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        titleCenterTextview.setText("设置密码");
        et_login_setting_pwd.addTextChangedListener(new myTextWatcher());
        et_login_setting_pwd_again.addTextChangedListener(new myTextWatcher());
    }
    @OnClick({R.id.title_left_imageview, R.id.title_center_textview, R.id.btn_login_setting_pwd_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_center_textview:
                break;
            case R.id.btn_login_setting_pwd_commit:
                setPas();

                break;
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setPas() {

        String pwd = et_login_setting_pwd.getText().toString().trim();
        String pwd_again = et_login_setting_pwd_again.getText().toString().trim();
        if (TextUtils.isEmpty(pwd))
        {
            T.showShort(this, R.string.toast_setting_new_pwd);
            return;
        }
        if (pwd.length() < 6)
        {
            T.showShort(this, R.string.toast_setting_pwd_6);
            return;
        }
        if (pwd.length() > 18)
        {
            T.showShort(this, R.string.toast_setting_pwd_18);
            return;
        }
        if (!pwd.equals(pwd_again))
        {
            tv_login_setting.setVisibility(View.VISIBLE);
            return;
        }
        if(!ToolsUtils.isPassword(pwd)){
            T.showShort(SettingPwdActivity.this,"密码格式为数字，特殊字符或字母任意两种以上组成");
            return;
        }
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String code = intent.getStringExtra("code");
        // 找回密码重置密码
        OkGo.post(Urls.URL_PWD_FIND_PWD_RESET)//
                .tag(this)
                .params("mobi",phone)
                .params("pwd",pwd)
                .params("confirmPwd",pwd_again)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(SettingPwdActivity.this,  "设置成功");
                                     IntentUtils.startActivity(SettingPwdActivity.this, LoginActivity.class);
                                 }else{
                                     T.showShort(SettingPwdActivity.this, lzyResponse.info);
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
            String newPwd = et_login_setting_pwd.getText().toString();
            String pwdAgain = et_login_setting_pwd_again.getText().toString();
            if (!TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(pwdAgain))
            {
                // 当输入框都不为空时按钮变红色才能点
                btn_login_setting_pwd_commit.setBackgroundResource(R.drawable.button_bg_selector);
                btn_login_setting_pwd_commit.setOnClickListener(SettingPwdActivity.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                btn_login_setting_pwd_commit.setBackgroundResource(R.drawable.btn_grey);
                btn_login_setting_pwd_commit.setOnClickListener(null);
            }

            if (TextUtils.isEmpty(pwdAgain) || TextUtils.isEmpty(newPwd))
            {
                tv_login_setting.setVisibility(View.INVISIBLE);
            }
        }

    }
}
