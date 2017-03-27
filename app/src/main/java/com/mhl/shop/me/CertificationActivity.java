package com.mhl.shop.me;

import android.support.v4.app.FragmentTransaction;

import com.mhl.shop.R;
import com.mhl.shop.main.CropBaseActivity;

/**
 * 作者：lff
 * 时间；2017-1-20 10:16
 * 描述：实名认证
 */
public class CertificationActivity extends CropBaseActivity {

    @Override
    protected void initContentView() {
        setContentView(R.layout.certification_main);
    }

    @Override
    protected void initViews() {
        initMainFragment();
    }

    /**
     * 初始化内容Fragment
     *
     * @return void
     */
    public void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CertificationFragment mFragment = CertificationFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        transaction.commit();
    }

    @Override
    protected void initEvents() {

    }
}


