package com.mhl.shop.search.wight;

import android.content.Intent;
import android.os.Bundle;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * 作者：lff
 * 时间；2017-1-11 16:48
 * 描述：商品搜索结果页面
 */

public class ShopSearchResultActivity extends MyBaseActivity{

    private String supplierkey="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
        //从Intent当中根据key取得value
        supplierkey = intent.getStringExtra("supplierkey");
        
    }


}
