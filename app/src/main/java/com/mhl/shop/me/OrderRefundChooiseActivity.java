package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：LFF
 * 时间；2016-12-19 17:38
 * 描述：选择退款类型
 */
public class OrderRefundChooiseActivity extends MyBaseActivity {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    private String pkId,allmoney,supplierId,goodsId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refung_choise);
        ButterKnife.bind(this);
        titleCenterTextview.setText("选择服务类型");

        Intent intent = getIntent();
        pkId = intent.getStringExtra("pkId");
        allmoney= intent.getStringExtra("money");
        supplierId = intent.getStringExtra("supplierId");
        goodsId= intent.getStringExtra("goodsId");
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
    @OnClick({R.id.title_left_imageview, R.id.tuikuan_tuihuo, R.id.tuikuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.tuikuan_tuihuo:
                Intent intent = new Intent();
                intent.putExtra("pkId", pkId);
                intent.putExtra("money", allmoney);
                intent.putExtra("type", "2");
                intent.putExtra("supplierId", supplierId);
                intent.putExtra("goodsId", goodsId);
                intent.setClass(OrderRefundChooiseActivity.this, OrderRefundActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tuikuan:
                Intent intent1 = new Intent();
                intent1.putExtra("pkId", pkId);
                intent1.putExtra("money", allmoney);
                intent1.putExtra("type", "1");
                intent1.putExtra("supplierId", supplierId);
                intent1.putExtra("goodsId",goodsId);
                intent1.setClass(OrderRefundChooiseActivity.this, OrderRefundActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
