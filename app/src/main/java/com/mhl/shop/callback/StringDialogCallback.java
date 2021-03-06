package com.mhl.shop.callback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.lzy.okgo.request.BaseRequest;
import com.mhl.shop.main.LoadingDialog;
import com.mhl.shop.wheel.AlertDialog;

/**
 * ================================================
 * 作    者：lff
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 修订历史：
 * ================================================
 */
public abstract class StringDialogCallback extends MyStringCallback {
    private boolean b;//是否显示加载框

    private LoadingDialog dialog;

    public StringDialogCallback(final Activity activity, boolean b) {
        if(!isNetworkAvailable(activity)){ //检查有没有网络
            new AlertDialog(activity).builder().setTitle("温馨提示")
                    .setMsg("您的网络异常，稍后再试？")
                    .setPositiveButton("去设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent =   new Intent(Settings.ACTION_SETTINGS);
                            activity.startActivity(intent);
                        }
                    }).setNegativeButton("不了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }else {
            this.b=b;
            if (null == dialog) {
                dialog = new LoadingDialog(activity);
//            dialog.setOnCancelListener(this);
            }
            dialog.setTitle("请求网络中...");
            if (dialog != null && !dialog.isShowing()) {
                if(b){
                    dialog.show();
                }

            }
        }
      }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            if(b){
                dialog.show();
            }
        }
    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
