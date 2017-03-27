package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LoginForgetActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseActivity;
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
 * 作者：lff
 * 时间；2016-11-17 15:35
 * 描述：修改用户密码
 */
public class UpdateUserPwdActivity extends MyBaseActivity implements OnClickListener{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    private TextView     forgetPasswordText;     //忘记密码
    private EditText     oldPasswordEdit;        //旧密码
    private EditText     newPasswordEdit;        //新密码
    private EditText     regNewPasswordEdit;     //再次输入新密码
    private Button       updateConfirmButton;    //更新按钮

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_update_loginpwd);
        ButterKnife.bind(this);
        //初始化控件
        initView();
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
        forgetPasswordText = (TextView) findViewById(R.id.forget_password_text);  //忘记密码
        forgetPasswordText.setOnClickListener(this);
        oldPasswordEdit = (EditText) findViewById(R.id.old_password_edit);  //旧密码
        newPasswordEdit = (EditText) findViewById(R.id.new_password_edit);   //新密码
        regNewPasswordEdit = (EditText) findViewById(R.id.reg_new_password_edit);  //再次新密码
        oldPasswordEdit.addTextChangedListener(new myTextWatcher());
        newPasswordEdit.addTextChangedListener(new myTextWatcher());
        regNewPasswordEdit.addTextChangedListener(new myTextWatcher());
        updateConfirmButton = (Button) findViewById(R.id.update_confirm_button);  //更新按钮
        titleCenterTextview.setText(R.string.updateloginpasswod_text);  //修改登陆密码标题

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
            String oldPwd = oldPasswordEdit.getText().toString().trim();
            String newPwd = newPasswordEdit.getText().toString().trim();
            String newPwdAgain = regNewPasswordEdit.getText().toString().trim();
            if (!TextUtils.isEmpty(oldPwd) && !TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(newPwdAgain))
            {
                // 当输入框都不为空时按钮变红色才能点
                updateConfirmButton.setBackgroundResource(R.drawable.button_bg_selector);
                updateConfirmButton.setOnClickListener(UpdateUserPwdActivity.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                updateConfirmButton.setBackgroundResource(R.drawable.btn_grey);
                updateConfirmButton.setOnClickListener(null);
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
            case R.id.update_confirm_button:  //提交按钮
                UpdatePassword();
                break;
            case R.id.forget_password_text:
                Intent login = new Intent(this, LoginForgetActivity.class);
                startActivity(login);
                break;
            default:
                break;
        }
    }


    /**
     * 点击更新按钮
     */
    private void UpdatePassword() {
        String oldPwd = oldPasswordEdit.getText().toString().trim();
        String newPwd = newPasswordEdit.getText().toString().trim();
        String regNewPwd = regNewPasswordEdit.getText().toString().trim();
        if (oldPwd.equals("")) {
            T.showShort(this, R.string.old_password_empty_error);
            return;
        }
        if (newPwd.equals("")) {
            T.showShort(this, R.string.new_password_empty_error);
            return;
        }
        if (!newPwd.equals(regNewPwd)) {
            T.showShort(this, R.string.reg_new_password_empty_error);
            return;
        }
        if(!ToolsUtils.isPassword(newPwd) || !ToolsUtils.isPassword(regNewPwd)){
            T.showShort(UpdateUserPwdActivity.this,"密码格式为数字，特殊字符或字母任意两种以上组成");
            return;
        }

        OkGo.post(Urls.URL_RESET_LOGIN_PWD)//
                .tag(this)
                .params("oldPwd",oldPwd)
                .params("pwd",newPwd)
                .params("confirmPwd",regNewPwd)
                .execute(new DialogCallback<LzyResponse<Null>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(UpdateUserPwdActivity.this,  "修改登录密码成功");
                                finish();
                                 }else{
                                     T.showShort(UpdateUserPwdActivity.this, lzyResponse.info);
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
