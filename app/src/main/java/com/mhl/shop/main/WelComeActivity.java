package com.mhl.shop.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.homepage.bean.Welcome;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-23 10:48
 * 描述：欢迎页
 */
public class WelComeActivity extends Activity {

    private TextView mTextView;
    private RelativeLayout rl_activity;
    private ImageView mImageView;
    private static final int GO_GUIDE = 1001;
    private boolean isFirstIn = false;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    Runnable runnable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //隐藏标题栏以及状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mImageView = (ImageView) findViewById(R.id.activity_iv);
        rl_activity = (RelativeLayout) findViewById(R.id.rl_activity);
        mTextView = (TextView) findViewById(R.id.tv_skip);
        mTextView.getBackground().setAlpha(150);
        inData();
//        handler.sendEmptyMessageDelayed(0,700);
        //初始化友盟数据统计

        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig( this,  "58ae7b483eae254d03000175", "Umeng", MobclickAgent.EScenarioType. E_UM_NORMAL));

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void inData() {
        OkGo.post(Urls.APP_WELCOME)//
                .tag(this)
                .execute(new DialogCallback<com.mhl.shop.login.LzyResponse<Welcome>>(this,false) {
                    @Override
                    public void onSuccess(final com.mhl.shop.login.LzyResponse<Welcome> lzyResponse, Call call, Response response) {
//                        handleResponse(lzyResponse.data, call, response);
                               if(lzyResponse.code==200){
//                                   Message message = new Message();
//                                   message.what = 0;
//                                   WelComeActivity.this.handler.sendMessage(message);
//                                   try {
//                                       Thread.sleep(100);
//                                   } catch (InterruptedException e) {
//                                       Thread.currentThread().interrupt();
//                                   }

                                   SharedPreferences perPreferences = getSharedPreferences("jike", MODE_PRIVATE);
                                   isFirstIn = perPreferences.getBoolean("isFirstIn", true);
                                   if (!isFirstIn) {
//                                           handler.sendEmptyMessageDelayed(GO_HOME, TIME);
                                       runnable = new Runnable() {
                                           @Override
                                           public void run() {
                                               getHome();
                                           }
                                       };
                                       handler.postDelayed(runnable, 2000);
                                   }else{
                                       handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
                                       SharedPreferences.Editor editor = perPreferences.edit();
                                       editor.putBoolean("isFirstIn", false);
                                       editor.commit();
                                   }
                                   if(lzyResponse.data.getWellome().size()>0){


                                   if (!TextUtils.isEmpty(lzyResponse.data.getWellome().get(0).getImg())) {
                                       Glide.with(WelComeActivity.this).load(Urls.URL_PHOTO+"/file/v3/download-"+lzyResponse.data.getWellome().get(0).getImg()+"-720-1280.jpg").into(mImageView);
                                       rl_activity.setVisibility(View.VISIBLE);

                                       mTextView.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               getHome();
                                               handler.removeCallbacks(runnable);
                                           }
                                       });
                                       if (!isFirstIn) { //第一次开启程序的时候不让点击去活动链接，因为没有初始化
                                           mImageView.setOnClickListener(new View.OnClickListener() {

                                               @Override
                                               public void onClick(View v) {
                                                   if (!TextUtils.isEmpty(lzyResponse.data.getWellome().get(0).getImg())) {
                                                       Intent intent1 = new Intent(WelComeActivity.this,BargainActivity.class);
                                                       intent1.putExtra(Constant.LBONCLICKURL, lzyResponse.data.getWellome().get(0).getUrl());
                                                       intent1.putExtra(Constant.TITLE, lzyResponse.data.getWellome().get(0).getTitle());
                                                       startActivity(intent1);
//                                                   //取消runnable继续发消息
                                                       handler.removeCallbacks(runnable);
                                                       WelComeActivity.this.finish();
                                                   }
                                               }
                                           });
                                       }

                                   }else {
                                       getHome();

                                   }
                                   }else {



                                       getHome();
                                   }

                               }

                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
//                                 handleError(call, response, e);
                                 getHome();
                             }
                         }
                );
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GO_HOME:
                    getHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goGuide() {
        Intent i = new Intent(WelComeActivity.this,Guide.class);
        startActivity(i);
        finish();
    }

    private void getHome() {
//        if (!GApp.getApplication().isLogin())
//        {
//            Intent login = new Intent(WelComeActivity.this, LoginActivity.class);
//            startActivity(login);
//        }
//        else
//        {
            Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
            startActivity(intent);
//        };
        finish();
    }


}
