package com.mhl.shop.shopdetails.been;

/**
 * 作者：Administrator
 * 时间；2016-12-29 16:49
 * 描述：
 */
public class pay {


    /**
     * packageValue : Sign=WXPay
     * appid : wx8108e73b6dd5fb68
     * sign : EE7A85EFB16B18ABBA33A1022075A187
     * partnerid : 1252048201
     * prepayid : wx201612291855133614591ad20659801721
     * noncestr : d40264f45e5d49eebfaa0aeca5f2e2e1
     * timestamp : 1483008850
     */

    private String packageValue;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}