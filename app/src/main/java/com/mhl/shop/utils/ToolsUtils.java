package com.mhl.shop.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：lff
 * 时间；2016-11-16 17:11
 * 描述：工具类
 */
public class ToolsUtils {
    /**
     * @Description:把数组转换为一个用逗号分隔的字符串 ，以便于用in+String 查询
     */
    public static String converToString(String[] ig) {
        String str = "";
        if (ig != null && ig.length > 0) {
            for (int i = 0; i < ig.length; i++) {
                str += ig[i] + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * @Description:把list转换为一个用逗号分隔的字符串
     */
    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + " ,");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }
    public static List<String> match(String s) {
        List<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile(":([\\w/\\.]*)_");
        Matcher m = p.matcher(s);
        while (!m.hitEnd() && m.find()) {
            results.add(m.group(1));
        }
        return results;
    }

    public static void show(Context context, int contentId)
    {
        Toast.makeText(context, contentId, Toast.LENGTH_SHORT).show();
    }
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }
    /*
       * 将时间转换为时间戳
       */
    public static String dateToStamp(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }
        /*
       * 将时间转换为时间戳
       */

    public static String dateToStampLit(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }
    public static String dateToStampMonth(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }
    /*
   * 两位小数
   */
    public static String Tow(String s){
        BigDecimal bd = new BigDecimal(s);
        bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        String str = bd.toString();
        return str;
    }
    /*
* 两位小数
*/
    public static String TowZeor(String s){
        String str = "";
        BigDecimal bd = new BigDecimal(s);
        int r=bd.compareTo(BigDecimal.ZERO); //和0，Zero比较
        if(r==0){
            str="0.00" ;
        }
        if(r==1){
            bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
            str = bd.toString();
        }
        if(r==-1){
            str="0.00" ;
        }
        return str;
    }
    /**
     * md5 32位小写加密
     * @param sSecret
     * @return
     */
    public static String getMd5Value(String sSecret)
    {
        if (sSecret == null) { return null; }
        try
        {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();
            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 判断字符串是否是有效密码
     */

    public static  boolean isPassword(String paw) {
//        String str = "^(?![\\\\d]+$)(?![a-zA-Z]+$)(?![!@#$%\\\\^&*\\\\(\\\\)]+$)[\\\\da-zA-Z!@#$%\\\\^&*\\\\(\\\\)]{6,18}$";
//        Pattern p = Pattern.compile(str);
//        Matcher m = p.matcher(paw);
//        return m.matches();
//
//        boolean flag = false;
//        try{
//            Pattern regex = Pattern.compile("^(?![\\d]+$)(?![a-zA-Z]+$)(?![!@#$%\\^&*\\(\\)]+$)[\\da-zA-Z!@#$%\\^&*\\(\\)]{6,18}$");
//            Matcher matcher = regex.matcher(paw);
//            flag = matcher.matches();
//        }catch(Exception e){
//            flag = false;
//        }
//        return flag;
                  Pattern p = Pattern.compile("^(?![\\d]+$)(?![a-zA-Z]+$)(?![!@#$%\\^&*\\(\\)]+$)[\\da-zA-Z!@#$%\\^&*\\(\\)]{6,18}$");
        Matcher m = p.matcher(paw);
        return m.matches();
    }
    /**
     * 判断字符串是否是有效邮箱
     */

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     *  手机号验证
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles)
    {

        //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[3|4|5|8|7][0-9]\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 判断字符串是否全部是数字
     */

    public static boolean isNum(String num) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(num);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断银行卡号
     */
    public static boolean isAccountNumber(String number){
        Pattern p = Pattern.compile("^[0-9]{16,19}$");
        Matcher matcher = p.matcher(number);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    // 版本名
    public static String getVersionName(Context context)
    {
        return getPackageInfo(context).versionName;
    }

    // 版本号
    public static int getVersionCode(Context context)
    {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context)
    {
        PackageInfo pi = null;

        try
        {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return pi;
    }


}

