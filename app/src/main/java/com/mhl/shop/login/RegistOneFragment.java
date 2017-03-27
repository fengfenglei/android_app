package com.mhl.shop.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.CountDownTimerUtils;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.MD5Util;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.UIUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 11:17
 * 描述：注册第一步 :验证手机号
 */
public class RegistOneFragment extends MyBaseRegistFragment implements View.OnClickListener {
    public static final String	TAG			= "RegistOneFragment";
    private Button				btn_regist_one;
    private EditText			regist_et_user_phone;				// 手机号
    private CheckBox regist_check;
    private EditText et_regist_phone_code;				// 手机验证码
    private Button btn_regist_phone_code;
    private TextView			regist_agreement;
    private TextView			retist_tip;
    private TextView regist_hint;
    public static boolean		ISNEXTSTEP	= false;				// 判断是否下一步
    private RegistTwoFragment	rwf;
    private String				type		= 1 + "";
    private String				mobile;
    private String				code;
    private CountDownTimerUtils timerUtils;						// 倒计时
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ISNEXTSTEP = getArguments().getBoolean("nextstep");
        RegistActivity ra = (RegistActivity) getActivity();
        ra.textColorChange();
        View view = inflater.inflate(R.layout.fragmetn_regist_one, null);
        btn_regist_one = (Button) view.findViewById(R.id.btn_regist_one);
        btn_regist_phone_code = (Button) view.findViewById(R.id.btn_regist_phone_code);
        btn_regist_phone_code.setOnClickListener(this);
        et_regist_phone_code = (EditText) view.findViewById(R.id.et_regist_phone_code);
        regist_et_user_phone = (EditText) view.findViewById(R.id.regist_et_user_phone);
        regist_check = (CheckBox) view.findViewById(R.id.regist_check);
        regist_agreement = (TextView) view.findViewById(R.id.regist_agreement);
        retist_tip = (TextView) view.findViewById(R.id.retist_tip);
        regist_hint=(TextView) view.findViewById(R.id.regist_hint);
        regist_agreement.setOnClickListener(this);
        et_regist_phone_code.addTextChangedListener(new myTextWatcher());
        regist_et_user_phone.addTextChangedListener(new myTextWatcher());
        btn_regist_one.setOnClickListener(RegistOneFragment.this);
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
            String phone = regist_et_user_phone.getText().toString();
            String code = et_regist_phone_code.getText().toString();
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code))
            {
                // 当输入框都不为空时按钮变红色才能点
                btn_regist_one.setBackgroundResource(R.drawable.button_bg_selector);
                btn_regist_one.setOnClickListener(RegistOneFragment.this);
            }
            else
            {
                // 当有输入框为空是按钮变灰并且无点击效果
                btn_regist_one.setBackgroundResource(R.drawable.btn_grey);
                btn_regist_one.setOnClickListener(null);
            }

            retist_tip.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_regist_one:

                nextStep();
                break;
            case R.id.btn_regist_phone_code:
                getCode();
                break;
            case R.id.regist_agreement:

                Intent intent1 = new Intent(getActivity(),WebActivity.class);
                intent1.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/cus-service-detail.html");
                intent1.putExtra(Constant.TITLE, "卖货郎商城用户注册协议");
                startActivity(intent1);
                break;
        }

    }

    /**
     * 获取短信
     */
    private Data data;
    private void getCode()
    {
        String phone = regist_et_user_phone.getText().toString().trim();

        if (TextUtils.isEmpty(phone))
        {
            T.showShort(getActivity(),
                    R.string.regist_phone);
            return;
        }
        if (!ToolsUtils.isMobileNO(phone))
        {
            T.showShort(getActivity(), R.string.toast_phone_error);
            return;
        }

        String time=UIUtils.isTime();
        String  s= "mobi="+phone+"&time="+time;
        String  signafter=MD5Util.encrypt(s);
        String  sign= signafter.substring(signafter.length()-6,signafter.length());
        // 获取短信验证码
        OkGo.post(Urls.SEND_CODE)
                .tag(this)
                .params("mobi",phone)
                .params("time",time)
                .params("sign",sign)
                .execute(new StringDialogCallback(getActivity(), true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 handleResponse(s, call, response);
                                 data=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(data!=null){
                                     if(data.getCode()==200){
                                         T.showShort(getActivity(),"短信已发送，请注意查收");
                                         regist_hint.setVisibility(View.VISIBLE);
                                         timerUtils = new CountDownTimerUtils(60000, 1000, btn_regist_phone_code, "重新获取");
                                         timerUtils.start();
                                     }else {
                                         if(!TextUtils.isEmpty(data.getInfo())){
                                             T.showShort(getActivity(), data.getInfo());
                                         }
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
    }

    /**
     * 下一步
     */

    public void nextStep()
    {
        mobile = regist_et_user_phone.getText().toString().trim();
        code = et_regist_phone_code.getText().toString().trim();
        if (TextUtils.isEmpty(mobile))
        {
            T.showShort(getActivity(),
                    "请输入手机号");
            return;
        }
        if (!ToolsUtils.isMobileNO(mobile))
        {
            T.showShort(getActivity(), "手机号格式错误");
            return;
        }
        if (TextUtils.isEmpty(code))
        {
            T.showShort(getActivity(), "请输入手机验证码");
            return;

        }
        if (code.length()!=6)
        {
            T.showShort(getActivity(), "手机验证码格式不对");
            return;
        }
        if (!regist_check.isChecked())
        {
            T.showShort(getActivity(),
                    "请同意注册协议");
            return;
        }
        // 获取短信验证码
        OkGo.post(Urls.SEND_NEXT)//
                .tag(this)
                .params("mobi",mobile)
                .params("vcode",code)
                .execute(
                        new StringDialogCallback(getActivity(), true) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                handleResponse(s, call, response);
                                data=(Data) GsonUtils.fromJson(s,
                                        Data.class);
                                if(data!=null){
                                    if(data.getCode()==200){
                                        rwf = new RegistTwoFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("nextstep", true);
                            bundle.putString("mobile", mobile);
                            bundle.putString("code", code);
                            ft.replace(android.R.id.tabcontent, rwf);
                            rwf.setArguments(bundle);
                            ft.addToBackStack(null);
                            ft.commitAllowingStateLoss();
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
                        });
    }

}

