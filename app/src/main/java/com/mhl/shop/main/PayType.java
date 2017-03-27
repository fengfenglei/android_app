package com.mhl.shop.main;

/**
 * 作者：lff
 * 时间；2017-2-8 10:36
 * 描述：所支付的类型
 */
public class PayType {
    private static String t ="";
    private static String phone ="";
    private static String address ="";
    private static String money ="";

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        PayType.phone = phone;
    }

    public static String getMoney() {
        return money;
    }

    public static void setMoney(String money) {
        PayType.money = money;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        PayType.address = address;
    }

    public static String getT() {
        return t;
    }

    public static void setT(String t) {
        PayType.t = t;
    }
}
