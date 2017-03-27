package com.mhl.shop.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.myview.ClearEditText;
import com.mhl.shop.login.myview.LastInputEditText;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.Des;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.SharePreferenceUtil;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-16 16:19
 * 描述：登录界面
 */
public class LoginActivity extends MyBaseActivity implements View.OnClickListener {

@Bind(R.id.title_center_textview)
TextView titleCenterTextview;
@Bind(R.id.et_account)
ClearEditText login_et_username;
@Bind(R.id.et_password)
LastInputEditText login_et_password;
@Bind(R.id.bt_login)
Button btLogin;
@Bind(R.id.tv_forget_password)
TextView tvForgetPassword;
    @Bind(R.id.login_tv_reg)
    TextView login_tv_reg;
@Bind(R.id.title_left_imageview)
ImageView titleLeftImageview;
@Bind(R.id.toggle_btn)
ToggleButton mToggleButton;
@Bind(R.id.login_remember_pwd)
CheckBox login_remember_pwd;
    @Bind(R.id.login_weiixn)
    ImageView login_weiixn;
    @Bind(R.id.login_qq)
    ImageView login_qq;
private String						userName;
private String						passWord;
private static Handler				handler;
private SharedPreferences			spf;
private SharedPreferences.Editor	editor;
private String						key				= "MIsICdwIBADANsBgkqhkiaG9w0BAQEFAASCAmEwggJdAgEAAoGBAKB8GunT65dDzvV4898";
    private IWXAPI api;
    private Tencent mTencent; //qq主操作对象
    private IUiListener loginListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope ="all"; //获取信息的范围参数
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        login_et_username.addTextChangedListener(new EditChangedListener());
        login_et_password.addTextChangedListener(new EditChangedListener());
        //初始化IWXAPI
//        api = WXAPIFactory.createWXAPI(LoginActivity.this,Constant .WEIXIN_APP_ID, true);
//        api.registerApp(Constant .WEIXIN_APP_ID);
    //qq
//    mTencent = Tencent.createInstance(Constant .WEIXIN_APP_ID, this.getApplicationContext());

        initViews();
//      initDataqq();
        handler = new Handler() {
@Override
public void handleMessage(Message msg)
        {
        super.handleMessage(msg);
        switch (msg.what)
        {
        case 200:
        login_et_password.setText("");
        //当手机号为空时，不能输出密码，将密码清除，防止回显
        login_et_password.setEnabled(false);
        if(null == editor) {
        editor = spf.edit();
        }
        editor.putString("password", "");
        editor.commit();
        break;
        case 300:
        login_et_password.setText("");
        if(null == editor) {
        editor = spf.edit();
        }
        editor.putString("password", "");
        editor.commit();
        break;
        }
        }
        };
        }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
//    private void initDataqq() {
//
//
//        mTencent = Tencent.createInstance(Constant .QQ_APP_ID, LoginActivity.this);
//        //要所有权限，不用再次申请增量权限，这里不要设置成get_user_info,add_t
//        scope = "all";
//        loginListener = new IUiListener() {
//
//            @Override
//            public void onError(UiError arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            /**
//             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
//             * "pf":"desktop_m_qq-10000144-android-2002-",
//             * "query_authority_cost":448,
//             * "authority_cost":-136792089,
//             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
//             * "expires_in":7776000,
//             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
//             * "msg":"",
//             * "access_token":"A2455F491478233529D0106D2CE6EB45",
//             * "login_cost":499}
//             */
//            @Override
//            public void onComplete(Object value) {
//                // TODO Auto-generated method stub
//
//                System.out.println("有数据返回..");
//                if (value == null) {
//                    return;
//                }
//
//                try {
//                    JSONObject jo = (JSONObject) value;
//
//                    String msg = jo.getString("msg");
//
//                    System.out.println("json=" + String.valueOf(jo));
//
//                    System.out.println("msg="+msg);
//                    Log.d("QQ", "msg==========================="+msg);
//
//                    if ("sucess".equals(msg)) {
//                        Toast.makeText(LoginActivity.this, "登录成功",
//                                Toast.LENGTH_LONG).show();
//
//                        String openID = jo.getString("openid");
//                        String accessToken = jo.getString("access_token");
//                        String expires = jo.getString("expires_in");
//                        Log.d("QQ", "openID==========================="+openID);
//
//                        mTencent.setOpenId(openID);
//                        mTencent.setAccessToken(accessToken, expires);
//                    }
//
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//
//            }
//
//            @Override
//            public void onCancel() {
//                // TODO Auto-generated method stub
//
//            }
//        };
//
//        userInfoListener = new IUiListener() {
//
//            @Override
//            public void onError(UiError arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            /**
//             * {"is_yellow_year_vip":"0","ret":0,
//             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
//             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
//             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
//             * "city":"黄冈","
//             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
//             * "vip":"0","level":"0",
//             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
//             * "province":"湖北",
//             * "is_yellow_vip":"0","gender":"男",
//             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
//             */
//            @Override
//            public void onComplete(Object arg0) {
//                // TODO Auto-generated method stub
//                if(arg0 == null){
//                    return;
//                }
//                try {
//                    JSONObject jo = (JSONObject) arg0;
//                    int ret = jo.getInt("ret");
//                    System.out.println("json=" + String.valueOf(jo));
//                    if(ret == 100030){
//                        //权限不够，需要增量授权
//                        Runnable r = new Runnable() {
//                            public void run() {
//                                mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {
//
//                                    @Override
//                                    public void onError(UiError arg0) {
//                                        // TODO Auto-generated method stub
//
//                                    }
//
//                                    @Override
//                                    public void onComplete(Object arg0) {
//                                        // TODO Auto-generated method stub
//                                    }
//
//                                    @Override
//                                    public void onCancel() {
//                                        // TODO Auto-generated method stub
//
//                                    }
//                                });
//                            }
//                        };
//
//                        LoginActivity.this.runOnUiThread(r);
//                    }else{
//                        String nickName = jo.getString("nickname");
//                        String gender = jo.getString("gender");
//                        Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//
//
//            }
//
//            @Override
//            public void onCancel() {
//                // TODO Auto-generated method stub
//
//            }
//        };
//
//
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_API) {
//            if (resultCode == Constants.RESULT_LOGIN) {
//                Tencent.handleResultData(data, loginListener);
//            }
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

/**
 * 初始化view
 */
private void initViews() {
        titleCenterTextview.setText(R.string.login);
        titleLeftImageview.setVisibility(View.GONE);
        // 记住密码被勾选
        spf = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = spf.getBoolean("remember_password", false);
        if (isRemember)
        {
        String spf_1 = spf.getString("account", "");
        String spf_2 = spf.getString("password", "");
        String rusult_1 = null;
        String rusult_2 = null;
        try
        {
        rusult_1 = Des.decrypt(spf_1, key);
        rusult_2 = Des.decrypt(spf_2, key);
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }

        login_et_username.setText(rusult_1);
        login_et_password.setText("");
        login_remember_pwd.setChecked(true);

        }

        boolean isLoginOut = getIntent().getBooleanExtra("loginout", false);
        if(isLoginOut) {
        Message message = new Message();
        message.what = 300;
        handler.sendMessage(message);
        login_et_password.setText("");
        }
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
        //选中
        login_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
        //未选中
        login_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        }
        });


//        login_et_username.setText(login_et_username.getText());//获取文本框中的内容(content)

        login_et_username.setSelection(login_et_username.getText().length());//将光标追踪到内容的最后
        }

@OnClick({R.id.title_left_imageview, R.id.bt_login, R.id.tv_forget_password,R.id.login_tv_reg,R.id.login_weiixn,R.id.login_qq})
public void onClick(View view) {
        switch (view.getId()) {
        case R.id.title_left_imageview:
        this.finish();
        break;
        case R.id.bt_login:
        //登录
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));//跳转到主页
        login();
            break;
       case R.id.login_weiixn:
           //微信登录
//           if (!api.isWXAppInstalled()) {
//               //提醒用户没有按照微信
//               Toast.makeText(LoginActivity.this, "没有安装微信,请先安装微信!", Toast.LENGTH_SHORT).show();
//               return;
//           }
//           SendAuth.Req req = new SendAuth.Req();
//           req.scope = "snsapi_userinfo";
//           req.state = "wechat_sdk_demo_test";
//           api.sendReq(req);
           //以上代码就是发起微信登录的方法
        break;
        case R.id.login_qq:
//            //QQ登录
//            mTencent = Tencent.createInstance(Constant.QQ_APP_ID,getApplicationContext());
//            //如果session无效，就开始登录
//            if (!mTencent.isSessionValid()) {
//                //开始qq授权登录
//                mTencent.login(LoginActivity.this, scope, loginListener);
//                Log.d("QQ", "GO===========================");
//            }
            break;
        case R.id.tv_forget_password:
            // 跳转到忘记密码界面
            Intent login = new Intent(this, LoginForgetActivity.class);
            startActivity(login);
        break;
            case R.id.login_tv_reg:
                //注册账户
                // 跳转到注册界面
                Intent regist = new Intent(this, RegistActivity.class);
                startActivity(regist);
                break;
        }
        }
        private Token token;
private void login() {
        userName = login_et_username.getText().toString().trim();
        passWord = login_et_password.getText().toString().trim();
        if (TextUtils.isEmpty(userName))
        {
        ToolsUtils.show(this, R.string.toast_login_account_isempty);
        return;
        }
        if (!ToolsUtils.isMobileNO(userName))
        {
                T.showShort(this, R.string.toast_account_error);
        return;
        }
        if (TextUtils.isEmpty(passWord))
        {
                T.showShort(this, R.string.toast_login_psw_isempty);
        return;
        }
    if(passWord.length()<6){
        T.showShort(this, "密码是最少6个字符");
        return;
    }
    //为了适应新的数据库数据 数字字母的验证先去掉
//    if (!ToolsUtils.isPassword(passWord))
//    {
//        T.showShort(this, R.string.regist_hint);
//        return;
//    }
        editor = spf.edit();
        // 把账号密码设为全局变量,当没有选择记住密码时可以在后台静默登录
        MyApplication.getApplication().setUserName(userName);
        MyApplication.getApplication().setPassWord(passWord);
        // 加入记住密码被选中,则把账号和密码存在SharedPreferences中
        // 加入记住密码被选中,则把账号和密码存在SharedPreferences中
        if (login_remember_pwd.isChecked())
        {
        String enrusult_1 = null;
        String enrusult_2 = null;
        editor.putBoolean("remember_password", true);
        try
        {
        enrusult_1 = Des.encrypt(userName, key);
        enrusult_2 = Des.encrypt(passWord, key);
        }
        catch (Exception e)
        {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        editor.putString("account", enrusult_1);
        editor.putString("password", enrusult_2);
        }
        else
        {
        editor.clear();
        }
        editor.commit();
        OkGo.post(Urls.TOKEN)//
                .tag(this)
                .params("client_id", Constant.CLIENT_ID)
                .params("grant_type",Constant.GRANT_TYPE)
                .params("scope",Constant.SCOPE)
                .params("client_secret",Constant.CLIENT_SECRET)
                .params("username",userName)
                .params("password",passWord)
                .execute(new StringDialogCallback(this, true) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                                token=(Token) GsonUtils.fromJson(s,
                                        Token.class);
                                if(TextUtils.isEmpty(token.getAccess_token())){
                                        T.showShort(LoginActivity.this, "用户或密码错误");
                                        return;
                                }else{//登录成功
                                        SharePreferenceUtil sp=   new  SharePreferenceUtil(MyApplication.getContext());
                                        sp.setAccess_token(token.getAccess_token());
                                        sp.setError(token.getError());
                                        sp.setError_description(token.getError_description());
                                        sp.setToken_type(token.getToken_type());
                                        sp.setRefresh_token(token.getRefresh_token());
                                        sp.setExpires_in((token.getExpires_in())+"");
                                        sp.setScope(token.getScope());
                                        T.showShort(LoginActivity.this, "登录成功");
                                        //登录成功设为true.退出设为false
                                        SharedPreferencesUtils.setParam(getApplicationContext(), SharedPreferencesUtils.isLogin, true);
                                    //刷新购物车
                                    if (MeInterface.onMyCartListener!=null) {
                                        MeInterface.onMyCartListener.OnMyCartRefresh("",0);
                                    }
                                    //刷新我的界面
                                    if (MeInterface.onMyMeListener!=null) {
                                        MeInterface.onMyMeListener.OnMyMeRefresh("",0);
                                    }
                                        finish();

                                }
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                T.showShort(LoginActivity.this, "用户或密码错误");
                        }
                });
        }
//        private void otestyu() {
//                OkGo.post("http://192.168.0.46:38001/api/ucenter/mem-address-list.json")
//                        .tag(this)
//                        .execute(new DialogCallback<LzyResponse<Token>>(this) {
//                                @Override
//                                public void onSuccess(LzyResponse<Token> responseData, Call call, Response response) {
//                                        handleResponse(responseData.data, call, response);
//                                }
//                                @Override
//                                public void onError(Call call, Response response, Exception e) {
//                                        super.onError(call, response, e);
//                                        handleError(call, response, e);
//                                }
//                        });
//
//        }
class EditChangedListener implements TextWatcher {
    private CharSequence temp;//监听前的文本
    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 3;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
        String phone = login_et_username.getText().toString();
        String password = login_et_password.getText().toString();
        if (TextUtils.isEmpty(phone))// 如果账号为空就把密码清空
        {
            Message message = new Message();
            message.what = 200;
            handler.sendMessage(message);
        } else {
            login_et_password.setEnabled(true);
        }
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password))
        {
            // 当输入框都不为空时按钮变红色才能点
            btLogin.setBackgroundResource(R.drawable.btn_red_pressed);
            btLogin.setOnClickListener(LoginActivity.this);
        }
        else
        {
            // 当有输入框为空是按钮变灰并且无点击效果
            btLogin.setBackgroundResource(R.drawable.btn_grey);
            btLogin.setOnClickListener(null);
        }

    }
}

@Override
protected void onDestroy() {
//    if (mTencent != null) {
//        //注销登录
//        mTencent.logout(LoginActivity.this);
//    }
    super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);

        }

//public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        // 过滤按键动作
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//
//        moveTaskToBack(true);
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//        }


        }
