package com.mhl.shop.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.main.been.LoginData;
import com.mhl.shop.utils.CountDownTimerUtils;
import com.mhl.shop.utils.MD5Util;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.UIUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 16:41
 * 描述：设置支付密码第一步
 */
public class SettingPayPwdOneFragment extends MyBaseRegistFragment implements View.OnClickListener {


    private Button my_account_setting_pay_pwd_next;		// 下一步
    private TextView			my_account_setting_pay_pwd_username;	// 用户名
    private Button				my_account_setting_pay_code;			// 获取验证码
    private EditText			my_account_setting_pay_input_code;		// 验证码
    private FragmentTransaction			ft;
    private SettingPayPwdTwoFragment	spptf;
    private String				m_mobile	= "";
    private String				m_type		= "3";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        m_mobile=LoginData.getLoginName();
//        m_mobile = getArguments().getString("loginName");
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting_pay_pwd_one, null);
        my_account_setting_pay_pwd_next = (Button) view.findViewById(R.id.my_account_setting_pay_pwd_next);
        my_account_setting_pay_pwd_username = (TextView) view.findViewById(R.id.my_account_setting_pay_pwd_username);

        my_account_setting_pay_code = (Button) view.findViewById(R.id.my_account_setting_pay_code);
        my_account_setting_pay_code.setOnClickListener(this);
        my_account_setting_pay_input_code = (EditText) view.findViewById(R.id.my_account_setting_pay_input_code);
        my_account_setting_pay_input_code.addTextChangedListener(new myTextWatcher());
//        if ((BaseApplication.getApplication().isLogin()))
//        {
            my_account_setting_pay_pwd_username.setText(m_mobile + ",您好");
//        }
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
            String code = my_account_setting_pay_input_code.getText().toString().trim();
            if (!TextUtils.isEmpty(code))
            {
                // 当输入框都不为空时按钮变红色才能点
                my_account_setting_pay_pwd_next.setBackgroundResource(R.drawable.button_bg_selector);
                my_account_setting_pay_pwd_next.setOnClickListener(SettingPayPwdOneFragment.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                my_account_setting_pay_pwd_next.setBackgroundResource(R.drawable.btn_grey);
                my_account_setting_pay_pwd_next.setOnClickListener(null);
            }


        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_account_setting_pay_pwd_next: // 点击下一步
                String code = my_account_setting_pay_input_code.getText().toString().trim();
                if (TextUtils.isEmpty(code))
                {
                    T.showShort(getActivity(), "请输入验证码");
                    return;
                }
                if (code.length()!=6)
                {
                    T.showShort(getActivity(), "手机验证码格式不对");
                    return;
                }
                codeIsOk(code);
				/*
				 * } else { ft.detach(spptf); ft.attach(spptf); }
				 */
                break;
            case R.id.my_account_setting_pay_code: // 获取验证码
                getCodeTxt();
                break;
        }
    }

    private void codeIsOk(final String code)
    {

        OkGo.post(Urls.URL_PWD_SETTING_NEXT)//
                .tag(this)
                .params("mobi",m_mobile)
                .params("vcode",code)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     ft = getFragmentManager().beginTransaction();
                                     spptf = new SettingPayPwdTwoFragment();
                                     Bundle bundle = new Bundle();
                                     bundle.putString("mobileValidateCode", code);
                                     spptf.setArguments(bundle);
                                     ft.replace(R.id.my_account_setting_pay_pwd, spptf);
                                     ft.addToBackStack(null);
                                     ft.commitAllowingStateLoss();
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

    /**
     * 获取验证码
     */
    private void getCodeTxt()
    {
        final CountDownTimerUtils timerUtils = new CountDownTimerUtils(60000, 1000, my_account_setting_pay_code, "重新获取验证码");
        timerUtils.start();
        String time= UIUtils.isTime();
        String  s= "mobi="+m_mobile+"&time="+time;
        String  signafter= MD5Util.encrypt(s);
        String  sign= signafter.substring(signafter.length()-6,signafter.length());


        OkGo.post(Urls.URL_PWD_SEND_LOGIN_CODE)//
                .tag(this)
                .params("mobi",m_mobile)
                .params("time",time)
                .params("sign",sign)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     T.showShort(getActivity(),  "已发送验证码");
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
