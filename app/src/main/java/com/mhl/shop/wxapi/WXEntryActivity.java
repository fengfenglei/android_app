package com.mhl.shop.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.been.Weixin;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：Administrator
 * 时间；2016-12-6 15:44
 * 描述：
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private IWXAPI api;
    private Weixin wxuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(WXEntryActivity.this, "wx1c24b0898474d4c6", false);
        api.handleIntent(getIntent(), WXEntryActivity.this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "errCode===" + baseResp.errCode);

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.d(TAG, "ERR_OK===" + baseResp.errCode);

                String code = ((SendAuth.Resp) baseResp).code;
                T.showShort(this, "code=="+code);
                if (!TextUtils.isEmpty(code)) {
                    getOpenId(code);
                } else {
                    T.showShort(this, "失败");
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                T.showShort(this, "失败");
                finish();
                break;
            default:
                break;
        }
    }

    private void getOpenId(final String code) {
        String wxUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="
                + Constant.WEIXIN_APP_ID
                + "&secret="
                + Constant.WEIXIN_APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        OkGo.post(wxUrl)//
                .tag(this)
                .execute(new StringDialogCallback(this, true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        wxuser=(Weixin) GsonUtils.fromJson(s,
                                Weixin.class);
                        Log.d(TAG, "wxuser===========================" + s);
                        wxLogin(wxuser.getAccess_token(),wxuser.getOpenid());
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        T.showShort(WXEntryActivity.this, "失败");
                    }
                });

    }

    private void wxLogin(final String access_token, final String openid) {
        String wxaginUrl = "https://api.weixin.qq.com/sns/auth";

        OkGo.post(wxaginUrl)//
                .tag(this)
                .params("access_token",access_token)
                .params("openid",openid)
                .execute(new StringDialogCallback(this, true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        lastLogin(access_token,openid);
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        T.showShort(WXEntryActivity.this, "失败");
                    }
                });

    }

    private void lastLogin(String access_token, String openid) {

        String wxaginUrl = "https://api.weixin.qq.com/sns/userinfo";

        OkGo.post(wxaginUrl)//
                .tag(this)
                .params("access_token",access_token)
                .params("openid",openid)
                .execute(new StringDialogCallback(this, true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "wxuser===========================" + s);
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        T.showShort(WXEntryActivity.this, "失败");
                    }
                });

    }


    /**
     * 获取标准 Cookie
     */
//    private String getCookieText() {
//        PersistentCookieStore myCookieStore = new PersistentCookieStore(WXEntryActivity.this);
//        List<Cookie> cookies = myCookieStore.getCookies();
//        Plan101Client.setCookieStore(myCookieStore);
//        String session = null;
//        for (int i = 0; i < cookies.size(); i++) {
//            Cookie cookie = cookies.get(i);
//            String cookieName = cookie.getName();
//            String cookieValue = cookie.getValue();
//
//            if (cookieName.equals("session")) {
//                session = cookieValue;
//            }
//        }
//        return session;
//    }

}
