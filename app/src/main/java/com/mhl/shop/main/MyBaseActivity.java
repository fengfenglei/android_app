package com.mhl.shop.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.SharedPreferencesUtils;
import com.mhl.shop.login.Token;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.SharePreferenceUtil;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-10 11:18
 * 描述：Activity的封装
 */
public class MyBaseActivity extends AppCompatActivity {

    protected <T> void handleResponse(T data, Call call, Response response) {
        StringBuilder sb;
        if (call != null) {

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
        } else {
        }
        if (data == null) {
        } else {
            if (data instanceof String) {
            } else if (data instanceof List) {
                sb = new StringBuilder();
                List list = (List) data;
                for (Object obj : list) {
                    sb.append(obj.toString()).append("\n");
                }
            } else if (data instanceof Set) {
                sb = new StringBuilder();
                Set set = (Set) data;
                for (Object obj : set) {
                    sb.append(obj.toString()).append("\n");
                }
            } else if (data instanceof Map) {
                sb = new StringBuilder();
                Map map = (Map) data;
                Set keySet = map.keySet();
                for (Object key : keySet) {
                    sb.append(key.toString()).append(" ： ").append(map.get(key)).append("\n");
                }
            } else if (data instanceof File) {
                File file = (File) data;
            } else if (data instanceof Bitmap) {
            } else {
            }
        }

        if (response != null) {
            Headers responseHeadersString = response.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("url ： ").append(response.request().url()).append("\n\n");
            sb.append("stateCode ： ").append(response.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
        } else {
        }
    }
    protected void handleError(Call call, Response response, Exception e) {
        if(response!=null){
            if(response.code()==401){  //刷新token
                OkGo.post(Urls.TOKEN)//
                        .tag(this)
                        .params("client_id", Constant.CLIENT_ID)
                        .params("client_secret",Constant.CLIENT_SECRET)
                        .params("grant_type","refresh_token")
                        .params("refresh_token", new  SharePreferenceUtil(MyApplication.getContext()).getRefresh_token())
                        .execute(new StringDialogCallback(this, true) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Token token=(Token) GsonUtils.fromJson(s,
                                        Token.class);
                                if(TextUtils.isEmpty(token.getAccess_token())){
                                    //刷新失败时跳转登陆界面重新登陆
                                    Intent login = new Intent(MyApplication.getContext(), LoginActivity.class);
                                    startActivity(login);
                                    return;
                                }
                                SharePreferenceUtil sp=   new  SharePreferenceUtil(MyApplication.getContext());
                                sp.setAccess_token(token.getAccess_token());
                                sp.setError(token.getError());
                                sp.setError_description(token.getError_description());
                                sp.setToken_type(token.getToken_type());
                                sp.setRefresh_token(token.getRefresh_token());
                                sp.setExpires_in((token.getExpires_in())+"");
                                sp.setScope(token.getScope());
                                //登录成功设为true.退出设为false
                                SharedPreferencesUtils.setParam(getApplicationContext(), SharedPreferencesUtils.isLogin, true);
                            }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                //刷新失败时跳转登陆界面重新登陆
                                Intent login = new Intent(MyApplication.getContext(), LoginActivity.class);
                                startActivity(login);
                            }
                        });
            }else  if(e.getMessage().equals("timeout")){
                T.showShort(this, "链接超时，请重试！");
            }else {
                if(!TextUtils.isEmpty(e.getMessage()+"")){
                    T.showShort(this,  e.getMessage()+"");
                }
            }
        }
        StringBuilder sb;
        if (call != null) {

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
        } else {
        }

        if (response != null) {
            Headers responseHeadersString = response.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(response.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
        } else {
        }
    }
}
