package com.mhl.shop.me;

import android.support.v4.app.FragmentTransaction;

import com.mhl.shop.R;
import com.mhl.shop.main.CropBaseActivity;
import com.mhl.shop.main.CropBaseFragment;

/**
 * 作者：Administrator
 * 时间；2017-2-28 11:29
 * 描述：
 */
public class MyAccountMainAcivity extends CropBaseActivity {

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
        CropBaseFragment mFragment = MyAccountFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        transaction.commit();
    }

    @Override
    protected void initEvents() {

    }
}
