package com.mhl.shop.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AndroidVerion;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.UpdateManager;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.DataCleanManager;
import com.mhl.shop.utils.FileUtils;
import com.mhl.shop.utils.SharePreferenceUtil;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.mhl.shop.utils.ToolsUtils.getVersionName;

/**
 * 作者：LFF
 * 时间；2016-11-16 18:03
 * 描述：设置界面
 */
public class SettingActivity extends MyBaseActivity {

    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_parentlayout)
    RelativeLayout titleParentlayout;
    @Bind(R.id.setting_about_mhl)
    RelativeLayout settingAboutMhl;
    @Bind(R.id.huancun)
    TextView huancun;
    @Bind(R.id.setting_idea_feedback)
    RelativeLayout settingIdeaFeedback;
    @Bind(R.id.versions_tv)
    TextView versionsTv;
    @Bind(R.id.setting_versions)
    RelativeLayout settingVersions;
    @Bind(R.id.setting_service_phone)
    RelativeLayout settingServicePhone;
    @Bind(R.id.setting_exit_logout)
    Button settingExitLogout;
    AndroidVerion  appData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        titleCenterTextview.setText("设置中心");
        initData();
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
    private void initData() {
        versionsTv.setText("	V" + getVersionName(this));
        try {
            huancun.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
        }
        if (!MyApplication.getApplication().isLogin())
        { // 如果没有登陆 隐藏退出登陆按钮
            settingExitLogout.setVisibility(View.GONE);
        }else {
            settingExitLogout.setVisibility(View.VISIBLE);

        }
    }


    @OnClick({R.id.title_left_imageview, R.id.title_parentlayout, R.id.setting_about_mhl, R.id.setting_idea_feedback, R.id.setting_versions, R.id.setting_service_phone, R.id.setting_exit_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_parentlayout:
                break;
            case R.id.setting_about_mhl:
                Intent intent3 = new Intent(SettingActivity.this, WebActivity.class);
                intent3.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/app-about.html");
                intent3.putExtra(Constant.TITLE, "关于我们");
                startActivity(intent3);
                break;
            case R.id.setting_idea_feedback://清除缓存

                new AlertDialog(this).builder().setTitle("温馨提示")
                        .setMsg("确定清除缓存吗？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //清楚缓存设置路径,缓存路径
                                DataCleanManager.cleanApplicationData(SettingActivity.this, FileUtils.getDir("json/app_002"));
                                DataCleanManager.cleanApplicationData(SettingActivity.this, FileUtils.getDir("temp/"));
                                DataCleanManager.cleanApplicationData(SettingActivity.this, FileUtils.getDir("temp.htm/"));
                                DataCleanManager.cleanApplicationData(SettingActivity.this, FileUtils.getDir("imageloader"));//imageloader加载工具的图片缓存路径
                                DataCleanManager.clearAllCache(SettingActivity.this);
                                try {
                                    huancun.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }				}
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.setting_versions:

                getAndroidVerion();

                break;
            case R.id.setting_service_phone:
                // 拨打客服电话号码
                callPhone();
                break;
            case R.id.setting_exit_logout:
                // 退出登录
                exit();
                break;
        }
    }
    //获取版本信息 （版本更新）
    private void getAndroidVerion() {
        OkGo.post(Urls.URL_APP_VERSION)
                .tag(this)
                .params("clientType","2")//客户端(1iOS--2Android)
                .params("clientVersion",getPackageInfo(SettingActivity.this).versionCode)//版本号
                .execute(new DialogCallback<LzyResponse<AndroidVerion>>(SettingActivity.this, true) {
                             @Override
                             public void onSuccess(com.mhl.shop.login.LzyResponse<AndroidVerion> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if(lzyResponse.code==200){
                                     appData=   lzyResponse.data;
                                     if(appData==null){

                                     }else {
                                         UpdateManager manager = new UpdateManager(lzyResponse.data.getClientVersion(),lzyResponse.data.getUpgradeDownloadUrl(),
                                                 lzyResponse.data.getUpgradeContent(), lzyResponse.data.getUpgradeFocus(), SettingActivity.this);
                                         manager.isShowToast = true;
                                         // 检查软件更新
                                         manager.checkUpdate();}
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );
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
     * 退出登录
     */
    private void exit() {
        new AlertDialog(this).builder().setTitle("温馨提示")
                .setMsg("确定退出登录？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getApplication().logout();
                        SharePreferenceUtil sp=   new  SharePreferenceUtil(MyApplication.getContext());
                        sp.setAccess_token("");
                        sp.setError("");
                        sp.setError_description("");
                        sp.setToken_type("");
                        sp.setRefresh_token("");
                        sp.setExpires_in("");
                        sp.setScope("");

                        finish();}
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }



    /**
     * 拨打客服电话
     */
    private void callPhone()
    {


        new AlertDialog(this).builder().setTitle("温馨提示")
                .setMsg("拨打4000333566？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:4000333566"));
                          startActivity(intent);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }


}
