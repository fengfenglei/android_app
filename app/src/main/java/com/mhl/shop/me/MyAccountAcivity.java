package com.mhl.shop.me;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.EditPayPwdActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.SettingPayPwdActivity;
import com.mhl.shop.login.been.Body;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.been.UrseInfo;
import com.mhl.shop.me.myview.ChoseSexDialog;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.JBitmapUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.mhl.shop.R.id.my_account_sex;

/**
 * 作者：lff
 * 时间；2016-11-14 17:50
 * 描述：我的账户界面
 */
public class MyAccountAcivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_parentlayout)
    RelativeLayout titleParentlayout;
    @Bind(R.id.my_account_user_icon)
    CircleImageView myAccountUserIcon;
    @Bind(R.id.my_account_setting_icon)
    RelativeLayout myAccountSettingIcon;
    @Bind(R.id.my_account_username)
    TextView myAccountUsername;
    @Bind(R.id.my_account_setting_username)
    RelativeLayout myAccountSettingUsername;
    @Bind(my_account_sex)
    TextView myAccountSex;
    @Bind(R.id.my_account_setting_sex)
    RelativeLayout myAccountSettingSex;
    @Bind(R.id.my_account_birthday)
    TextView myAccountBirthday;
    @Bind(R.id.my_account_setting_birthday)
    RelativeLayout myAccountSettingBirthday;
    @Bind(R.id.my_account_setting_address_manage)
    RelativeLayout myAccountSettingAddressManage;
    @Bind(R.id.my_account_setting_login_pwd)
    RelativeLayout myAccountSettingLoginPwd;
    @Bind(R.id.my_account_setting_ok)
    TextView myAccountSettingOk;
    @Bind(R.id.my_account_setting_pay_pwd)
    RelativeLayout myAccountSettingPayPwd;
    @Bind(R.id.my_account_exit_login)
    Button myAccountExitLogin;
    @Bind(R.id.my_account_renzheng)
    TextView myAccountRenzheng;
    private GetPictureFragment fragment;
    private ChoseSexDialog csd;
    private int m_year;
    private int m_month;
    private int m_day;
    private boolean isPayPassword = false;                        // 时候已设置支付密码
    private Calendar calendar;
    private String loginName = "";//用户名
    int sexInt;
    private UrseInfo data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        titleCenterTextview.setText("我的账户");

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initDatas();

    }

    private void initDatas() {
        {// 获取
            OkGo.post(Urls.URL_USER_INFO)//
                    .tag(this)
                    .execute(new DialogCallback<LzyResponse<UrseInfo>>(this, true) {
                                 @Override
                                 public void onSuccess(LzyResponse<UrseInfo> responseData, Call call, Response response) {
                                     handleResponse(responseData.data, call, response);
                                     if (responseData.data != null) {
                                         data=   responseData.data;
                                         setData(responseData.data);
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
    }

    private void setData(UrseInfo data) {

        Glide.with(MyApplication.getContext()).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getAvatar() + "-100-100.jpg").into(myAccountUserIcon);

        //显示图片的配置
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.icon_bg)
//                .showImageOnFail(R.drawable.icon_bg)
//                .cacheInMemory(true)
////                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();

//        ImageLoader.getInstance().displayImage(Urls.URL_BASE+"/file/v3/download-"+data.getAvatar()+"-50-50.jpg", myAccountUserIcon);

        myAccountUsername.setText(data.getLoginName() + "");
        loginName = data.getLoginName() + "";
        if (!TextUtils.isEmpty(data.getDateOfBirth() + "")) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(data.getDateOfBirth()))));
            myAccountBirthday.setText( ToolsUtils.dateToStamp((data.getDateOfBirth())));
        } else {
            myAccountBirthday.setText("");
        }
        if (data.getSex() == 1) {
            myAccountSex.setText("男");
            sexInt = 1;
        } else if (data.getSex() == 2) {
            myAccountSex.setText("女");
            sexInt = 2;
        } else if (data.getSex() == 3) {
            myAccountSex.setText("保密");
            sexInt = 3;
        } else {
            myAccountSex.setText("保密");
            sexInt = 3;
        }
        if (data.getPayPwd().equals("true")) {
            myAccountSettingOk.setVisibility(View.VISIBLE);
            myAccountSettingOk.setText("已设置");
            isPayPassword = true;
        } else {
            myAccountSettingOk.setVisibility(View.VISIBLE);
            myAccountSettingOk.setText("未设置");
            isPayPassword = false;
        }
        if (data.getAuthenticationFlag() == 1) {
            myAccountRenzheng.setVisibility(View.VISIBLE);
            myAccountRenzheng.setText("已认证");
        } else if (data.getAuthenticationFlag() == 2) {
            myAccountRenzheng.setVisibility(View.VISIBLE);
            myAccountRenzheng.setText("未认证");
        } else if (data.getAuthenticationFlag() == 3) {
            myAccountRenzheng.setVisibility(View.VISIBLE);
            myAccountRenzheng.setText("审核中");
        } else if (data.getAuthenticationFlag() == 4) {
            myAccountRenzheng.setVisibility(View.VISIBLE);
            myAccountRenzheng.setText("审核失败");
        }
    }

    @OnClick({R.id.title_left_imageview, R.id.title_parentlayout, R.id.my_account_user_icon, R.id.my_account_setting_icon, R.id.my_account_username, R.id.my_account_setting_username, my_account_sex, R.id.my_account_setting_sex, R.id.my_account_birthday, R.id.my_account_setting_birthday, R.id.my_account_setting_address_manage, R.id.my_account_setting_login_pwd, R.id.my_account_setting_ok, R.id.my_account_setting_pay_pwd, R.id.my_account_exit_login,R.id.my_account_setting_renzheng})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview://返回
                finish();
                break;
            case R.id.title_parentlayout:
                break;
            case R.id.my_account_user_icon:


                break;
            case R.id.my_account_setting_icon:
                getPic();
                break;
            case R.id.my_account_username:
                break;
            case R.id.my_account_setting_username:
                break;
            case my_account_sex:
                break;
            case R.id.my_account_setting_sex:
                choseSex(sexInt);
                break;
            case R.id.my_account_birthday:
                break;
            case R.id.my_account_setting_birthday:
                settingBirthday();
                break;
            case R.id.my_account_setting_address_manage:
                Intent adIntent = new Intent(this, ManageAdressActivity.class);
                adIntent.putExtra("adress_type", "userInfo");
                startActivity(adIntent);
                ;
                break;
            case R.id.my_account_setting_login_pwd:// 修改登陆密码
                IntentUtils.startActivity(this, UpdateUserPwdActivity.class);
                break;
            case R.id.my_account_setting_ok:
                break;
            case R.id.my_account_setting_pay_pwd:    // 设置支付密码
                // 设置支付密码
                if (isPayPassword)  //如果已经设置了支付密码则跳到修改密码
                {
                    Intent intent = new Intent();
                    intent.putExtra("loginName", loginName);
                    intent.setClass(this, EditPayPwdActivity.class);
                    startActivity(intent);
                } else
                // 如果没有设置支付密码则跳到设置密码
                {
                    Intent intent1 = new Intent();
                    intent1.putExtra("loginName", loginName);
                    intent1.setClass(this, SettingPayPwdActivity.class);
                    startActivity(intent1);
//                    startActivityForResult(intent1, 11);
                }
            case R.id.my_account_exit_login:
                break;
            case R.id.my_account_setting_renzheng:
//                IntentUtils.startActivity(this, CertificationActivity.class);
                if (data.getAuthenticationFlag() == 1) {
                    IntentUtils.startActivity( this, CertificationResultActivity.class);
                } else if (data.getAuthenticationFlag() == 2) {
                    IntentUtils.startActivity(this, CertificationActivity.class);
                } else if (data.getAuthenticationFlag() == 3) {
                    IntentUtils.startActivity( this, CertificationResultActivity.class);
                } else if (data.getAuthenticationFlag() == 4) {
                    IntentUtils.startActivity( this, CertificationResultActivity.class);
                }
                break;
        }
    }

    public void settingBirthday() {

        String str = myAccountBirthday.getText().toString();
        String[] child = str.split("-");
        if (str.equals("") || str == null) {
            calendar = Calendar.getInstance();
            m_year = 1990;
            m_month = 02;
            m_day = 01;
        } else {
            List<String> listData = new ArrayList<String>();
            for (int i = 0; i < child.length; i++) {
                listData.add(child[i]);
            }

            calendar = Calendar.getInstance();
            m_year = Integer.valueOf(listData.get(0));
            m_month = Integer.valueOf(listData.get(1));
            m_day = Integer.valueOf(listData.get(2));
        }

        //设置白色的主题，使得日期选址框显示白色主题
        DatePickerDialog datePicker = new DatePickerDialog(this, AlertDialog.BUTTON_POSITIVE, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String monthOfYearm;
                String dayOfMonthm;
                 if((monthOfYear + 1)>9 ){
                     monthOfYearm=(monthOfYear + 1)+"";
                 }else {
                     monthOfYearm="0"+(monthOfYear + 1)+"";
                 }
                if((dayOfMonth + 1)>9 ){
                    dayOfMonthm=dayOfMonth+"";
                }else {
                    dayOfMonthm="0"+dayOfMonth+"";
                }
                myAccountBirthday.setText(year + "-" + monthOfYearm + "-" + dayOfMonthm);

                String birthday = myAccountBirthday.getText().toString();
                post("dateOfBirth", birthday);
                Log.d("birthday",birthday);

            }

        }, m_year, m_month - 1, m_day);
        datePicker.setTitle(m_year + "年" + (m_month - 1) + "月" + m_day + "日");//设置标题
        datePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());//设置最大年份
        datePicker.show();

    }

    /**
     * 选择性别
     */

    private void choseSex(final int num) {
        csd = new ChoseSexDialog(this, R.style.dialog, num);
        Window window = csd.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogAnimation);
        csd.show();
        csd.setOnSetSexListener(new ChoseSexDialog.OnSetSexListener() {
            @Override
            public void onComplete(String sex, int index) {
                if (num != index)// 原来的性别和选择的不一样才去请求
                {
                    post("sex", String.valueOf(index));

                }

            }
        });

    }

    //上传个人信息资料（修改了那个就传那个字段）
    private void post(String key, String v) {

        OkGo.post(Urls.URL_INFO_EDIT)//
                .tag(this)
                .params(key, v)
                .execute(new DialogCallback<LzyResponse<Null>>(MyAccountAcivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     T.showShort(MyAccountAcivity.this, "修改成功");
                                     initDatas();
                                 } else {
                                     T.showShort(MyAccountAcivity.this, lzyResponse.info);
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

    /**
     * 获取头像
     */
    Body body;

    private void getPic() {
        if (null == fragment) {
            fragment = GetPictureFragment
                    .newInstance(GetPictureFragment.TYPE_REGIST);
            fragment.setOnGetPictureListener(new GetPictureFragment.OnGetPictureListener() {
                @Override
                public void onComplete(Uri result) {
                    if (result != null) {
                        String path = result.getPath();// /storage/emulated/0/Android/data/com.mhl.shop/cache/1471334271033.jpg
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        try {
                            file =new File(JBitmapUtils.saveBitmap2File(JBitmapUtils.revitionImageSize(String.valueOf(file)), String.valueOf(file)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        Environment.getExternalStorageDirectory().getPath();
//                        Environment.getExternalStorageDirectory().getName();
//                        String PA = Environment.getExternalStorageDirectory().getPath() + Environment.getExternalStorageDirectory().getName();
                        if (file != null) {

                            OkGo.post(Urls.URL_UPLOAD)//
                                    .tag(this)
//                                    .isMultipart(true)
                                    .params("file", file)   // 可以添加文件上传
                                    .params("docType", "30")   // 可以添加文件上传
                                    .execute(new StringDialogCallback(MyAccountAcivity.this, true) {
                                                 @Override
                                                 public void onSuccess(String s, Call call, Response response) {
                                                     body = (Body) GsonUtils.fromJson(s,
                                                             Body.class);
                                                     if (body.getCode() == 200) {
                                                         post("avatar", body.getData());
                                                     } else {
                                                         T.showShort(MyAccountAcivity.this, body.getMessage());
                                                     }
                                                 }

                                                 @Override
                                                 public void onError(Call call, Response response, Exception e) {
                                                     super.onError(call, response, e);
                                                     handleError(call, response, e);
                                                 }
                                             }
                                    );
//                            new StringDialogCallback(MyAccountAcivity.this) {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                    //上传成功
//                                }
//
//
//                                @Override
//                                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                                    //这里回调上传进度(该回调在主线程,可以直接更新ui)
//                                }
//                            });

                        }
                    } else {
                        T.showShort(MyAccountAcivity.this, "选择图片为空");
                    }

                }

                @Override
                public void onCancel() {
                }
            });
        }
        fragment.show(getSupportFragmentManager(), "getPic");
    }

}