package com.mhl.shop.search;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * 作者：Administrator
 * 时间；2017-1-10 21:26
 * 描述：
 */
public class MyShareprefences {
    private Context context;
    Set<String> set = new HashSet<>();
                 //构造方法
                 public MyShareprefences(Context context) {
                 this.context=context;

             }
        //创建存放信息的方法
                 public boolean saveShareprefences(String name){
                boolean flag=false;
                 //实例化SharedPreferences
                 SharedPreferences share=context.getSharedPreferences("info", Activity.MODE_PRIVATE);
                 //调用SharedPreferences下的接口，进行写入
                      SharedPreferences.Editor editor=share.edit();
                 //写入信息

                     set.add(name);
                     editor.putStringSet("content", set);
                 //提交信息，让数据持久化
                 editor.commit();
                 flag=true;
                 return flag;
             }
        public Set<String> readShareprefences(){
                 //实例化map用于接收信息
            Set<String> set = new HashSet<>();

                 //实例化SharedPreferences
                 SharedPreferences share=context.getSharedPreferences("info",Activity.MODE_PRIVATE);
                 //根据KEY值取得信息
                 //把取得的信息存入map
                 set=share.getStringSet("content",set);
                 return set;
             }
}
