package com.mhl.shop.utils;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
/**
 * 作者：lff
 * 时间；2016-11-15 17:27
 * 描述：
 */
public class MySharedpreference {
    private Context context;
    public MySharedpreference(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }



    public boolean saveMessage(String access_token, String token_type
    ,String refresh_token, String expires_in
            ,String scope, String error, String error_description) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        // 对数据进行编辑
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", access_token);
        editor.putString("token_type", token_type);
        editor.putString("refresh_token", refresh_token);
        editor.putString("expires_in", expires_in);
        editor.putString("scope", scope);
        editor.putString("error", error);
        editor.putString("error_description", error_description);
        flag = editor.commit();// 将数据持久化到存储介质中
        return flag;
    }
    public Map<String, Object> getMessage() {
        Map<String, Object> map = new HashMap<String, Object>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        String access_token = sharedPreferences.getString("access_token", "");
        String refresh_token = sharedPreferences.getString("refresh_token", "");
        String token_type = sharedPreferences.getString("token_type", "");
        String expires_in = sharedPreferences.getString("expires_in", "");
        String scope = sharedPreferences.getString("scope", "");
        String error = sharedPreferences.getString("error", "");
        String error_description = sharedPreferences.getString("error_description", "");
        map.put("access_token", access_token);
        map.put("token_type", token_type);
        map.put("refresh_token", refresh_token);
        map.put("expires_in", expires_in);
        map.put("scope", scope);
        map.put("error", error);
        map.put("error_description", error_description);
        return map;
    }
}
