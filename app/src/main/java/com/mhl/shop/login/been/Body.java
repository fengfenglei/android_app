package com.mhl.shop.login.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-28 18:38
 * 描述：
 */
public class Body {

    /**
     * code : 200
     * data : b3b58d9b23a24db18201b98ae49d70ef
     * datas : ["685f9d815cee4947a891b17444885a6a","1447a2a65b0649d7b0726e7fa696f1ea","b3b58d9b23a24db18201b98ae49d70ef"]
     * error : 0
     * message : 200
     * url : /file/v2/download-b3b58d9b23a24db18201b98ae49d70ef.jpg
     */

    private int code;
    private String data;
    private int error;
    private int message;
    private String url;
    private List<String> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
