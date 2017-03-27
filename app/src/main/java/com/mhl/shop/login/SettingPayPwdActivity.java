package com.mhl.shop.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2016-11-17 16:35
 * 描述：设置支付密码
 */
public class SettingPayPwdActivity extends MyBaseActivity {

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    SettingPayPwdOneFragment sppf;   //设置支付密码第一步
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pay_pwd);  //URL_SEND_SETTINGCODE
        ButterKnife.bind(this);
        titleCenterTextview.setText("设置支付密码");
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
        if (sppf == null) {
           Intent intent = getIntent();

              String  mobile = intent.getStringExtra("loginName");

            sppf = new SettingPayPwdOneFragment();
            Bundle bundle = new Bundle();
            bundle.putString("loginName", mobile);
            sppf.setArguments(bundle);
            ft.replace(R.id.my_account_setting_pay_pwd, sppf);
        } else {
            ft.attach(sppf);
        }
        ft.commitAllowingStateLoss();

    }





    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
