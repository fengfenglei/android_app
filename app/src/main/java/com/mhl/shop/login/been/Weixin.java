package com.mhl.shop.login.been;

/**
 * 作者：Administrator
 * 时间；2016-12-6 18:48
 * 描述：
 */
public class Weixin {


    /**
     * access_token : M4FNeLsDnfF-RWC1mbHf0xCpXh2UgI37W3Qedu3GZ4uUqU0XCxVvuglNFeO0fciHH-Jkr_4Kk59R98WwwdcFMc9K5ErCWHYITJQMr1A4dGI
     * expires_in : 7200
     * refresh_token : vEerQnVMSmwOS5p5C-72Gb7iivmx4Q_FYno5FcTBshDuvNHGf3YHKd409t_nGf3Y8qYWmxPB6GxaKQPmyBKR79vWG5ucDoq-h4Hiq-6iV_Y
     * openid : ooN_VwRFPHprybOnXhfejh9TtzaQ
     * scope : snsapi_userinfo
     * unionid : osN_Et0Wj2pI9xZCNOSIKY7OBvZo
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
