package com.mhl.shop.login;

import java.io.Serializable;

/**
 * 作者：lff
 * 时间；2016-11-15 15:43
 * 描述：主要数据结构
 */
public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String info;
    public T data;
}