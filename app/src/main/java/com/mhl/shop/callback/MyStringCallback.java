package com.mhl.shop.callback;

import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.utils.SharePreferenceUtil;

import okhttp3.Response;

/**
 * 作者：LFF
 * 时间；2016-11-24 16:30
 * 描述：
 */
public abstract class MyStringCallback extends AbsCallback<String> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //主要用于在所有请求之前添加公共的请求头或请求参数，例如登录授权的 token,使用的设备信息等,可以随意添加,也可以什么都不传
        if(TextUtils.isEmpty(new SharePreferenceUtil(MyApplication.getContext()).getAccess_token())){//Access_token为空时不传Access_token
        }else {
            request .params("access_token", new SharePreferenceUtil(MyApplication.getContext()).getAccess_token() );
        }
    }
    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        return s;
    }
}