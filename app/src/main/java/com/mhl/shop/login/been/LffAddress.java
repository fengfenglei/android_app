package com.mhl.shop.login.been;

import com.mhl.shop.me.been.Address;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-15 15:43
 * 描述：
 */
public class LffAddress<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String info;
    public List<Address> data;
}