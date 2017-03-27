package com.mhl.shop.login;

import java.io.Serializable;

/**
 * 作者：Administrator
 * 时间；2016-11-15 15:43
 * 描述：
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String info;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = code;
        lzyResponse.info = info;
        return lzyResponse;
    }
}
