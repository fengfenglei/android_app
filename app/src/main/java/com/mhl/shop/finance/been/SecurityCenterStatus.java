package com.mhl.shop.finance.been;

/**
 * 作者：lff
 * 时间；2017-1-6 17:20
 * 描述：安全中心
 */
public class SecurityCenterStatus {
    /**
     * loginPwdStatus : 1
     * bindMobileStatus : 1
     * payPwdStatus : 1
     * authenticationStatus : 1
     */

    private int loginPwdStatus;
    private int bindMobileStatus;
    private int payPwdStatus;
    private int authenticationStatus;

    public int getLoginPwdStatus() {
        return loginPwdStatus;
    }

    public void setLoginPwdStatus(int loginPwdStatus) {
        this.loginPwdStatus = loginPwdStatus;
    }

    public int getBindMobileStatus() {
        return bindMobileStatus;
    }

    public void setBindMobileStatus(int bindMobileStatus) {
        this.bindMobileStatus = bindMobileStatus;
    }

    public int getPayPwdStatus() {
        return payPwdStatus;
    }

    public void setPayPwdStatus(int payPwdStatus) {
        this.payPwdStatus = payPwdStatus;
    }

    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
