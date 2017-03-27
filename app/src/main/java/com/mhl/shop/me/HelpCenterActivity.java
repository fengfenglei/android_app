package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.utils.Constant;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2016-11-18 17:04
 * 描述：帮助中心
 */
public class HelpCenterActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.rl_donate_money)
    RelativeLayout rlDonateMoney;
    @Bind(R.id.rl_active_money)
    RelativeLayout rlActiveMoney;
    @Bind(R.id.rl_withdraw_money)
    RelativeLayout rlWithdrawMoney;
    @Bind(R.id.rl_gold)
    RelativeLayout rlGold;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_help_center);
        ButterKnife.bind(this);

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
        titleCenterTextview.setText("帮助中心");
    }

    @OnClick({R.id.title_left_imageview, R.id.rl_donate_money, R.id.rl_active_money, R.id.rl_withdraw_money, R.id.rl_gold})
    public void onClick(View view) {
        Intent intent = new Intent(HelpCenterActivity.this,WebActivity.class);
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.rl_donate_money:
                intent.putExtra(Constant.TITLE, "赠送金额");
                intent.putExtra(Constant.LBONCLICKURL, "http://uat.51mdx.net/view/h5/donate_money.shtml");
                startActivity(intent);
                break;
            case R.id.rl_active_money:
                intent.putExtra(Constant.TITLE, "激活金额");
                intent.putExtra(Constant.LBONCLICKURL, "http://uat.51mdx.net/view/h5/active_money.shtml");
                startActivity(intent);
                break;
            case R.id.rl_withdraw_money:
                intent.putExtra(Constant.TITLE, "可提现金额");
                intent.putExtra(Constant.LBONCLICKURL, "http://uat.51mdx.net/view/h5/withdraw_money.shtml");
                startActivity(intent);
                break;
            case R.id.rl_gold:
                intent.putExtra(Constant.TITLE, "货郎豆");
                intent.putExtra(Constant.LBONCLICKURL, "http://uat.51mdx.net/view/h5/mhl_gold.shtml");
                startActivity(intent);
                break;
        }
    }
}
