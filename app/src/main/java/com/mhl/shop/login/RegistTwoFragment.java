package com.mhl.shop.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 11:29
 * 描述：注册第二步 :设置登陆密码
 */
public class RegistTwoFragment  extends MyBaseRegistFragment implements View.OnClickListener
{
    private Button			btn_regist_two;
    private EditText		regist_et_pwd;				// 登陆密码
    private EditText		regist_et_pwd_again;		// 再次输入登录密码
    private EditText		regist_et_referrer_phone;	// 推荐人手机号
    private TextView		tv_regist_setting;			// 两次密码是否一致
    public static boolean	ISNEXTSTEP	= false;		// 判断是否下一步

    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ISNEXTSTEP = getArguments().getBoolean("nextstep");
        RegistActivity ra = (RegistActivity) getActivity();
        ra.textColorChange();
        ISNEXTSTEP = false;
        View view = inflater.inflate(R.layout.fragmetn_regist_two, null);
        btn_regist_two = (Button) view.findViewById(R.id.btn_regist_two);
        regist_et_pwd = (EditText) view.findViewById(R.id.regist_et_pwd);
        regist_et_pwd_again = (EditText) view.findViewById(R.id.regist_et_pwd_again);
        regist_et_referrer_phone = (EditText) view.findViewById(R.id.regist_et_referrer_phone);
        tv_regist_setting = (TextView) view.findViewById(R.id.tv_regist_setting);
        regist_et_pwd.addTextChangedListener(new myTextWatcher());
        regist_et_pwd_again.addTextChangedListener(new myTextWatcher());
        return view;

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
            String pwd = regist_et_pwd.getText().toString().trim();
            String newPwd = regist_et_pwd_again.getText().toString().trim();
            if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(newPwd))
            {
                // 当输入框都不为空时按钮变红色才能点
                btn_regist_two.setBackgroundResource(R.drawable.button_bg_selector);
                btn_regist_two.setOnClickListener(RegistTwoFragment.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                btn_regist_two.setBackgroundResource(R.drawable.btn_grey);
                btn_regist_two.setOnClickListener(null);
            }

            if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(newPwd))
            {
                tv_regist_setting.setVisibility(View.INVISIBLE);
            }
        }

    }
    private Data data;
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_regist_two:
                String pwd = regist_et_pwd.getText().toString().trim();
                String newPwd = regist_et_pwd_again.getText().toString().trim();
                String phone = regist_et_referrer_phone.getText().toString().trim();
                if (TextUtils.isEmpty(pwd))
                {
                    T.showShort(getActivity(), R.string.toast_login_psw_isempty);
                    return;
                }
                if (pwd.length() < 6)
                {
                    T.showShort(getActivity(), R.string.toast_setting_pwd_6);
                    return;
                }
                if (pwd.length() > 18)
                {
                    T.showShort(getActivity(), R.string.toast_setting_pwd_18);
                    return;
                }
                if (!pwd.equals(newPwd))
                {
                    tv_regist_setting.setVisibility(View.VISIBLE);
                    return;
                }
                if (!TextUtils.isEmpty(phone))
                {

                    if (!ToolsUtils.isMobileNO(phone))
                    {
                        T.showShort(getActivity(), R.string.toast_phone_error);
                        return;
                    }
                }
                if(!ToolsUtils.isPassword(pwd)){
                    T.showShort(getActivity(),"密码格式为数字，特殊字符或字母任意两种以上组成");
                    return;
                }
                if(!ToolsUtils.isPassword(newPwd)){
                    T.showShort(getActivity(),"密码格式为数字，特殊字符或字母任意两种以上组成");
                    return;
                }
                if (!newPwd.equals(pwd))
                {
                    T.showShort(getActivity(), "两次输入密码不一致");
                    return;
                }
                // 获取短信验证码
                OkGo.post(Urls.REGISTER)//
                        .tag(this)
                        .params("mobi",getArguments().getString("mobile"))
                        .params("pwd",pwd)
                        .params("confirmPwd",pwd)
                        .params("recommended",phone)
                        .execute(new StringDialogCallback(getActivity(), true) {
                                     @Override
                                     public void onSuccess(String s, Call call, Response response) {
                                         handleResponse(s, call, response);
                                         data=(Data) GsonUtils.fromJson(s,
                                                 Data.class);
                                         if(data!=null){
                                             if(data.getCode()==200){
                                                 T.showShort(getActivity(),"注册成功");
                                                 IntentUtils.startActivity(getActivity(),LoginActivity.class);
                                             }else {
                                                 T.showShort(getActivity(),data.getInfo());
                                             }
                                         }else {
                                             T.showShort(getActivity(),"请重试！");
                                         }
                                     }
                                     @Override
                                     public void onError(Call call, Response response, Exception e) {
                                         super.onError(call, response, e);
                                         handleError(call, response, e);

                                     }
                                 }
                        );

                break;

        }

    }

}

