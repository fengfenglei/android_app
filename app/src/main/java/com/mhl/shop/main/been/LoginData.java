package com.mhl.shop.main.been;

/**
 * 作者：Administrator
 * 时间；2017-2-19 18:11
 * 描述：
 */
public class LoginData {
    private static  String loginName;

    public static String getLoginName() {
        return loginName;
    }

    public static void setLoginName(String loginName) {
        LoginData.loginName = loginName;
    }
}
