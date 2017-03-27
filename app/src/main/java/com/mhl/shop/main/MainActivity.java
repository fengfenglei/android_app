package com.mhl.shop.main;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.find.FindFragment;
import com.mhl.shop.home.FirstPageFragment;
import com.mhl.shop.me.MeFragment;
import com.mhl.shop.shopcart.CartFragment;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-10 14:03
 * 描述：首页
 */
public class MainActivity extends MyBaseActivity implements View.OnClickListener {

    private AndroidVerion  appData;
    // 定位四个Fragment
    private FirstPageFragment homeFragment = new FirstPageFragment();
    private FindFragment findFragment = new FindFragment();
    private CartFragment cartFragment = new CartFragment();
    private MeFragment meFragment = new MeFragment();

    // tab中的四个帧布局
    private FrameLayout findFrameLayout, meFrameLayout, cartFrameLayout,
            homeFrameLayout;

    // tab中的四个帧布局中的四个图片组件
    private ImageView findImageView, meImageView, cartImageView,
            homeImageView;

    // tab中的四个帧布局中的四个图片对应文字
    private TextView findTextView, meTextView, cartTextView,
            homeTextView;
    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);
     Log.d("onAttachFragment","1");
        if (homeFragment == null && fragment instanceof FirstPageFragment) {
            homeFragment = (FirstPageFragment)fragment;
            Log.d("onAttachFragment","2");

        }else if (findFragment == null && fragment instanceof FindFragment) {
            findFragment = (FindFragment)fragment;
            Log.d("onAttachFragment","3");

        }else if (cartFragment == null && fragment instanceof CartFragment) {
            cartFragment = (CartFragment)fragment;
            Log.d("onAttachFragment","4");
        }else if (meFragment == null && fragment instanceof MeFragment) {
            meFragment = (MeFragment)fragment;
            Log.d("onAttachFragment","5");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/** 设置是否对日志信息进行加密, 默认false(不加密). */
//        AnalyticsConfig.enableEncrypt( enable);//6.0.0版本以前
//        MobclickAgent.enableEncrypt(false);//6.0.0版本及以后
//        如果enable为true，SDK会对日志进行加密。加密模式可以有效防止网络攻击，提高数据安全性。
//        如果enable为false，SDK将按照非加密的方式来传输日志。
//        如果您没有设置加密模式，SDK的加密模式为false（不加密）。
        // 初始化组件
        initView();

        // 初始化按钮单击事件
        initClickEvent();

        // 初始化所有fragment
        initFragment();
        getAndroidVerion();
        //初始化相机权限
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }
    //获取版本信息 （版本更新）
    private void getAndroidVerion() {
        if(appData!=null){
            appData=null;
        }
        OkGo.post(Urls.URL_APP_VERSION)
                .tag(this)
                .params("clientType","2")//客户端(1iOS--2Android)
                .params("clientVersion",getPackageInfo(MainActivity.this).versionCode)//版本号
                .execute(new DialogCallback<com.mhl.shop.login.LzyResponse<AndroidVerion>>(MainActivity.this, false) {
                             @Override
                             public void onSuccess(com.mhl.shop.login.LzyResponse<AndroidVerion> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if(lzyResponse.code==200){
                                       appData=   lzyResponse.data;
                                     if(appData==null){

                                     }else {
                                 UpdateManager manager = new UpdateManager(appData.getClientVersion(),appData.getUpgradeDownloadUrl(),
                                         appData.getUpgradeContent(),appData.getUpgradeFocus(), MainActivity.this);
                                 manager.isShowToast = false;
                                 // 检查软件更新
                                 manager.checkUpdate();
                                     }
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );
        Log.d("clientVersion",getPackageInfo(MainActivity.this).versionCode+"");
    }
    //当前版本号
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    /**
     * 初始化所有fragment
     */
    private void initFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();

        if (!homeFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, homeFragment);
            fragmentTransaction.hide(homeFragment);
        }
        if (!cartFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, cartFragment);
            fragmentTransaction.hide(cartFragment);
        }
        if (!findFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, findFragment);
            fragmentTransaction.hide(findFragment);
        }
        if (!meFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, meFragment);
            fragmentTransaction.hide(meFragment);
        }
        hideAllFragment(fragmentTransaction);
        // 默认显示第一个fragment
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param fragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.hide(cartFragment);
        fragmentTransaction.hide(findFragment);
        fragmentTransaction.hide(meFragment);
    }

    /**
     * 初始化按钮单击事件
     */
    private void initClickEvent() {
        findFrameLayout.setOnClickListener(this);
        meFrameLayout.setOnClickListener(this);
        cartFrameLayout.setOnClickListener(this);
        homeFrameLayout.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        findFrameLayout = (FrameLayout) findViewById(R.id.findLayout);
        meFrameLayout = (FrameLayout) findViewById(R.id.meLayout);
        cartFrameLayout = (FrameLayout) findViewById(R.id.cartLayout);
        homeFrameLayout = (FrameLayout) findViewById(R.id.homeLayout);

        findImageView = (ImageView) findViewById(R.id.findImageView);
        meImageView = (ImageView) findViewById(R.id.meImageView);
        cartImageView = (ImageView) findViewById(R.id.cartImageView);
        homeImageView = (ImageView) findViewById(R.id.homeImageView);

        findTextView = (TextView) findViewById(R.id.findTextView);
        meTextView = (TextView) findViewById(R.id.meTextView);
        cartTextView = (TextView) findViewById(R.id.cartTextView);
        homeTextView = (TextView) findViewById(R.id.homeTextView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeLayout:
                // 点击首页tab
                clickTab(homeFragment);
                break;

            case R.id.cartLayout:
                // 点击购物车tab
                clickTab(cartFragment);
                break;

            case R.id.findLayout:
                // 点击发现tab
                clickTab(findFragment);
                break;

            case R.id.meLayout:
                // 点击我tab
                clickTab(meFragment);
                break;

            default:
                break;
        }
    }

    /**
     * 点击下面的Tab按钮
     *
     * @param tabFragment
     */
    private void clickTab(Fragment tabFragment) {

        // 清除上次选中状态
        clearSeleted();

        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();

        // 隐藏所有fragment
        hideAllFragment(fragmentTransaction);

        // 显示该Fragment
        fragmentTransaction.show(tabFragment);

        // 提交事务
        fragmentTransaction.commit();

        // 改变tab的样式,设置为选中状态
        changeTabStyle(tabFragment);

    }

    /**
     * 清除上次选中状态
     */
    private void clearSeleted() {
        if (!homeFragment.isHidden()) {
            homeImageView.setImageResource(R.drawable.tab_home_button_black);
            homeTextView.setTextColor(Color.parseColor("#333333"));
        }

        if (!cartFragment.isHidden()) {
            cartImageView.setImageResource(R.drawable.tab_shop_button_black);
            cartTextView.setTextColor(Color.parseColor("#333333"));
        }

        if (!findFragment.isHidden()) {
            findImageView.setImageResource(R.drawable.tab_found_button_black);
            findTextView.setTextColor(Color.parseColor("#333333"));
        }

        if (!meFragment.isHidden()) {
            meImageView.setImageResource(R.drawable.tab_my_button_black);
            meTextView.setTextColor(Color.parseColor("#333333"));
        }
    }

    /**
     * 根据Fragment的状态改变样式
     */
    private void changeTabStyle(Fragment tabFragment) {
        if (tabFragment instanceof FirstPageFragment) {
            homeImageView.setImageResource(R.drawable.tab_home_button_red);
            homeTextView.setTextColor(Color.parseColor("#F44444"));
        }
        if (tabFragment instanceof CartFragment) {
            cartImageView.setImageResource(R.drawable.tab_shop_button_red);
            cartTextView.setTextColor(Color.parseColor("#F44444"));
        }

        if (tabFragment instanceof FindFragment) {
            findImageView.setImageResource(R.drawable.tab_found_button_red);
            findTextView.setTextColor(Color.parseColor("#F44444"));
        }

        if (tabFragment instanceof MeFragment) {
            meImageView.setImageResource(R.drawable.tab_my_button_red);
            meTextView.setTextColor(Color.parseColor("#F44444"));
        }
    }
    @Override
    protected void onResume() {
        int type = MyApplication.getType();
        MyApplication.setType(0);
        if (type == 1) {
            int tag = MyApplication.getTag();

            super.onResume();

            if (tag == 0) {
                clickTab(homeFragment);
            }

            else if (tag == 2) {
                // 点击发现tab
                clickTab(findFragment);
            } else if (tag == 3) {
                // 点击购物车tab
                clickTab(cartFragment);
            } else {
//                int tag2 = intent.getIntExtra("CategoryGoodsDetailAcitivity", 0);
            }
        } else {
            super.onResume();

        }
        MobclickAgent.onResume(this);
        Log.d("abbott", "result==onResume");

    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if(Integer.parseInt(appData.getClientVersion())>getPackageInfo(MainActivity.this).versionCode && appData.getUpgradeFocus()==1){
//                Log.d("abbott", "result==非常抱歉，您需要更新应用才能继续使用");
//                Toast.makeText(MainActivity.this,"非常抱歉，您需要更新应用才能继续使用", Toast.LENGTH_LONG).show();
//                System.gc();
//                finish();
//            }
//            return false;
//        }else {
//            return super.onKeyDown(keyCode, event);
//        }
//
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if(Integer.parseInt(appData.getClientVersion())>getPackageInfo(MainActivity.this).versionCode && appData.getUpgradeFocus()==1){
//            Log.d("abbott", "result==非常抱歉，您需要更新应用才能继续使用");
//            Toast.makeText(MainActivity.this,"非常抱歉，您需要更新应用才能继续使用", Toast.LENGTH_LONG).show();
//            System.gc();
//            finish();
//        }
//    }
    //@Override
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//    // TODO Auto-generated method stub
//    if(keyCode == KeyEvent.KEYCODE_BACK){
////        if(IsUpdate.getIsUpdate()==1){
////                Log.d("abbott", "result==非常抱歉，您需要更新应用才能继续使用");
////                Toast.makeText(MainActivity.this,"非常抱歉，您需要更新应用才能继续使用", Toast.LENGTH_LONG).show();
////                System.gc();
////                finish();
////        }
//        return false;
//    }
//    return super.onKeyDown(keyCode, event);
//}
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Log.d("abbott", "result==onPause");

    }


}
