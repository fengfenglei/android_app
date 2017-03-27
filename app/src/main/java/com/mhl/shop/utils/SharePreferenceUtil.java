package com.mhl.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：lff
 * 时间；2016-11-16 15:01
 * 描述：保存token的值
 */
public class SharePreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharePreferenceUtil(Context context) {
        sp = context.getSharedPreferences("token", context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setAccess_token(String access_token) {
        editor.putString("access_token", access_token);
        editor.commit();
    }

    public String getAccess_token() {
        return sp.getString("access_token", "");
    }
    public  String getError_description() {
        return sp.getString("error_description", "");
    }

    public void setError_description(String error_description) {
        editor.putString("error_description", error_description);
        editor.commit();
    }

    public String getError() {
        return sp.getString("error", "");
    }

    public void setError(String error) {
        editor.putString("error", error);
        editor.commit();
    }


    public  String getToken_type() {
        return sp.getString("token_type", "");
    }

    public void setToken_type(String token_type) {
        editor.putString("token_type", token_type);
        editor.commit();
    }

    public  String getRefresh_token() {
        return sp.getString("refresh_token", "");
    }

    public void setRefresh_token(String refresh_token) {
        editor.putString("refresh_token", refresh_token);
        editor.commit();
    }

    public  String getExpires_in() {
        return sp.getString("expires_in", "");
    }

    public void setExpires_in(String expires_in) {
        editor.putString("expires_in", expires_in);
        editor.commit();
    }

    public  String getScope() {

        return sp.getString("scope", "");
    }

    public void setScope(String scope) {
        editor.putString("scope", scope);
        editor.commit();
    }
}
