package com.mhl.shop.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.PayType;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2017-2-8 11:23
 * 描述：充值成功页面
 */
public class RechargeSuccessActivity extends Activity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.address)
    TextView address;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_success);
        ButterKnife.bind(this);
        titleCenterTextview.setText("充值成功");
        phone.setText("充值手机号："+ PayType.getPhone());
        money.setText("支付金额："+PayType.getMoney());
        address.setText("归属地："+PayType.getAddress());
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @OnClick({R.id.title_left_imageview, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.go:
                finish();
                break;
        }
    }
}
