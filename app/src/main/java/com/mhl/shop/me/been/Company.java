package com.mhl.shop.me.been;

/**
 * 作者：lff
 * 时间；2016-12-23 10:11
 * 描述：物流公司
 */
public class Company {

    /**
     * pkId : 35
     * companyName : 其他物流
     * expressWay : 2
     */

    private String pkId;
    private String companyName;
    private int expressWay;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getExpressWay() {
        return expressWay;
    }

    public void setExpressWay(int expressWay) {
        this.expressWay = expressWay;
    }
}
