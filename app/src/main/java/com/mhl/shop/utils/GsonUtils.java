package com.mhl.shop.utils;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import java.lang.reflect.Type;

/**
 * 作者：lff
 * 时间；2016-11-15 16:25
 * 描述：Gson的单例模式 饿汉式
 */
public class GsonUtils {
    //采取单例模式
    private static Gson gson=new Gson();
    private GsonUtils() {
    }
    /**
     * @MethodName : toJson
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     * @param src
     *            :将要被转化的对象
     * @return :转化后的JSON串
     */
    public static String toJson(Object src) {
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }

    /**
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, (Type) classOfT);
    }

    /**
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为
     *              new TypeToken<List<T>>(){}.getType()
     *              ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     * @param json
     * @param typeOfT
     * @return
     */
    public static Object fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

}
