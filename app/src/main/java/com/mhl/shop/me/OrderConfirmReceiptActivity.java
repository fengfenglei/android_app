package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2016-12-20 16:28
 * 描述：确认收回界面
 */
public class OrderConfirmReceiptActivity extends MyBaseActivity {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.item_image)
    ImageView itemImage;
    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.guige)
    TextView guiGe;
    @Bind(R.id.big_money)
    TextView bigMoney;
    @Bind(R.id.lit_money)
    TextView litMoney;
    @Bind(R.id.shop_count)
    TextView shopCount;

    private String item_image, shop_name, shop_count, guige,money,orderDetailId;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm_receiptz_ctivity);
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
        Intent intent = getIntent();
        item_image = intent.getStringExtra("item_image");
        shop_name = intent.getStringExtra("shop_name");
        shop_count = intent.getStringExtra("shop_count");
        guige = intent.getStringExtra("guige");
        money= intent.getStringExtra("money");
        orderDetailId= intent.getStringExtra("orderDetailId");
        titleCenterTextview.setText("确认收货成功");
        Glide.with(this).load(Urls.URL_PHOTO+"/file/v3/download-"+item_image+"-200-200.jpg"
        ).into(itemImage);
        shopName.setText(shop_name);
        shopCount.setText("x"+shop_count);
        guiGe.setText(guige);
        bigMoney.setText("¥"+money);
    }

    @OnClick({R.id.title_left_imageview, R.id.order_detail, R.id.liji_pingjia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.order_detail:
                //跳转到商品详情
                Intent intent = new Intent();
                intent.putExtra("PkId", orderDetailId);//PkId
                intent.setClass(OrderConfirmReceiptActivity.this, OrderDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.liji_pingjia:
                Intent intent1 = new Intent(OrderConfirmReceiptActivity.this,OrderEvaluateActivity.class);
                intent1.putExtra("item_image", item_image);
                intent1.putExtra("shop_name", shop_name);
                intent1.putExtra("shop_count", shop_count+"");
                intent1.putExtra("guige", guige);
                intent1.putExtra("money", money+"");
                intent1.putExtra("orderDetailId", orderDetailId+"");
                startActivity(intent1);
                finish();
                break;
        }
    }
}
