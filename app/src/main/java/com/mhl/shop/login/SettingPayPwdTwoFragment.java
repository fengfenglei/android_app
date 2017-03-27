package com.mhl.shop.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 16:53
 * 描述：设置支付密码-第二步
 */
public class SettingPayPwdTwoFragment extends MyBaseRegistFragment implements OnClickListener{

    private Button		my_account_setting_pay_pwd_submit;	// 提交
    private EditText	my_account_setting_pay_pwd_new_pwd; // 新的支付密码
    private EditText	pay_pwd_new_pwd_again;				// 再次输入支付密码
    private String		s_mobileValidateCode	= "";		// 填写的验证码

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        s_mobileValidateCode = getArguments().getString("mobileValidateCode");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting_pay_pwd_two, null);
        my_account_setting_pay_pwd_submit = (Button) view.findViewById(R.id.my_account_setting_pay_pwd_submit);
        my_account_setting_pay_pwd_new_pwd = (EditText) view.findViewById(R.id.pay_pwd_new_pwd);
        pay_pwd_new_pwd_again = (EditText) view.findViewById(R.id.pay_pwd_new_pwd_again);
        my_account_setting_pay_pwd_new_pwd.addTextChangedListener(new myTextWatcher());
        pay_pwd_new_pwd_again.addTextChangedListener(new myTextWatcher());
        return view;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
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
            String newPwd = my_account_setting_pay_pwd_new_pwd.getText().toString().trim();
            String newPwdAgain = pay_pwd_new_pwd_again.getText().toString().trim();
            if (!TextUtils.isEmpty(newPwd) && !TextUtils.isEmpty(newPwdAgain))
            {
                // 当输入框都不为空时按钮变红色才能点
                my_account_setting_pay_pwd_submit.setBackgroundResource(R.drawable.button_bg_selector);
                my_account_setting_pay_pwd_submit.setOnClickListener(SettingPayPwdTwoFragment.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                my_account_setting_pay_pwd_submit.setBackgroundResource(R.drawable.btn_grey);
                my_account_setting_pay_pwd_submit.setOnClickListener(null);
            }

        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_account_setting_pay_pwd_submit:
                String newPwd = my_account_setting_pay_pwd_new_pwd.getText().toString().trim();
                String newPwdAgain = pay_pwd_new_pwd_again.getText().toString().trim();
                if (TextUtils.isEmpty(newPwd))
                {
                    T.showShort(getActivity(), "新密码不能为空");
                    return;
                }
                if (newPwd.length() != 6)
                {
                    T.showShort(getActivity(), "请设置6位数字的支付密码");
                    return;
                }

                if (!newPwd.equals(newPwdAgain))
                {
                    T.showShort(getActivity(), "两次输入密码不一致");
                    return;
                }
                confirmNewPwd(newPwd,newPwdAgain);
                break;
        }
    }

    /**
     * 提交支付新密码
     *
     * @param newPwd
     */
    private void confirmNewPwd(String newPwd,String newPwdAgain)
    {
        OkGo.post(Urls.URL_PWD_SETTING)//
                .tag(this)
                .params("payPwd",newPwd)
                .params("payPwdConfirm",newPwdAgain)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(getActivity(),  "设置支付密码成功");
                                     getActivity().finish();
                                     //通知提交订单页面密码设置成功
                                     if (MeInterface.onMyPasswordListener!=null) {
                                         MeInterface.onMyPasswordListener.OnMyPasswordRefresh("",0);
                                     }
                                 }else{
                                     T.showShort(getActivity(), lzyResponse.info);
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
