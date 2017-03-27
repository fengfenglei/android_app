package com.mhl.shop.main;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.mhl.shop.login.SharedPreferencesUtils;
import com.mhl.shop.login.Token;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * 作者：lff
 * 时间；2016-11-10 11:17
 * 描述：初始化app
 */
public class MyApplication extends Application {
    private Token loginUser;
    private static Context mContext;
    private static long					mMainThreadId;
    private static Handler mMainHandler;

    private static ImageLoader			mImageLoader	= ImageLoader.getInstance();
    private static MyApplication myApp			= null;
    private String						userName;										// 账号
    private String						passWord;										// 密码
    private static int					tag				= 0;

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        MyApplication.type = type;
    }

    public static int getTag() {
        return tag;
    }

    public static void setTag(int tag) {
        MyApplication.tag = tag;
    }

    private static int					type			= 0;

    public static Context getContext()
    {
        return mContext;
    }
    public static long getMainThreadId()
    {
        return mMainThreadId;
    }
    public static Handler getMainHandler()
    {
        return mMainHandler;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("deviceToken",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        UMShareAPI.get(this);
        //在application文件中配置三方平台的appkey
        PlatformConfig.setWeixin("wx1c24b0898474d4c6", "a48c5602ef213ba63bba870ed88d453x");
        PlatformConfig.setSinaWeibo("3503723034", "d27a2e77fcb24d50e04e422e574e4ab5");
        PlatformConfig.setQQZone("1105293192", "cRT2Me77iK2NO1J1");
//        Config.DEBUG = true;
        Config.REDIRECT_URL = "http://sns.whalecloud.com";
        Fresco.initialize(this);
        myApp = this;
        mMainThreadId = android.os.Process.myTid();
        //创建主线程的handler
        mMainHandler = new Handler();
        if (mImageLoader == null)
        {
            mImageLoader = ImageLoader.getInstance();
        }

        // 上下文
        mContext = getApplicationContext();
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy0216/
//                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
//                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie,以下不需要
//                    .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
//                    .setCookieStore(new PersistentCookieStore())          //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                      .setCertificates()                                  //方法一：信任所有证书（选一种即可）
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书（选一种即可）
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密

                    //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

                    //这两行同上,不需要就不要传
//                    .addCommonHeaders(headers)                                         //设置全局公共头
                    .addCommonParams(params);                                          //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 分割 Dex 支持
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static MyApplication getApplication() {
        return myApp;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
//判断是否登录
    public boolean isLogin()
    {
        //添加字段判断是否登录，用于完全退出
        boolean isLogin = (boolean) SharedPreferencesUtils.getParam(getApplicationContext(), SharedPreferencesUtils.isLogin, false);

        return  isLogin;
    }
    //退出登录
    public void logout()
    {
        SharedPreferencesUtils.setParam(getApplicationContext(), SharedPreferencesUtils.isLogin, false);
    }
    public Token getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Token loginUser) {
        this.loginUser = loginUser;
    }
}
