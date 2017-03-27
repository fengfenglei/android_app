package com.mhl.shop.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
 * 时间；2016-11-17 11:10
 * 描述：注册
 */
public class RegistActivity extends MyBaseActivity {
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.regist_btn_one)
    TextView registBtnOne;
    @Bind(R.id.regist_btn_two)
    TextView registBtnTwo;
    private RegistOneFragment	rof;												// 第一步验证手机号码

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        init();

        initItem();
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
        titleCenterTextview.setText("注册账号");
        textColorChange();
    }
    /**
     * 字体颜色改变
     */
    public void textColorChange()
    {
        if (RegistOneFragment.ISNEXTSTEP)
        {
            registBtnOne.setTextColor(Color.RED);
            registBtnTwo.setTextColor(Color.GRAY);
        }
        if (RegistTwoFragment.ISNEXTSTEP)
        {

            registBtnOne.setTextColor(Color.GRAY);
            registBtnTwo.setTextColor(Color.RED);
        }

    }
    /**
     * 初始化选项
     */
    private void initItem()
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        rof = new
                RegistOneFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("nextstep", true);
        ft = ft.replace(android.R.id.tabcontent, rof);
        rof.setArguments(bundle);
        ft.commitAllowingStateLoss();

    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
