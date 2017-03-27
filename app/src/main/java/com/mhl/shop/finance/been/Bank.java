package com.mhl.shop.finance.been;

/**
 * 作者：lff
 * 时间；2017-1-5 11:53
 * 描述：银行卡信息
 */
public class Bank {

    /**
     * upperLimit : 2000.0
     * lowerLimit : 100.0
     * percent : 0.2
     * commissionCharge : 1.0
     * availableBalance : 4814.380000000001
     * pkId : 841910299111067648
     * bankUser : 郑少卿
     * bankName : 测啊
     * bankCardCode : **4555
     * bankAddress : 测啊
     */

    private double upperLimit;//提现额度上限
    private double lowerLimit;//提现额度下限
    private double percent;//提现百分比
    private double commissionCharge;//提现手续费
    private double availableBalance;//可提现余额
    private String pkId;
    private String bankUser;
    private String bankName;
    private String bankCardCode;
    private String bankAddress;

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getCommissionCharge() {
        return commissionCharge;
    }

    public void setCommissionCharge(double commissionCharge) {
        this.commissionCharge = commissionCharge;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardCode() {
        return bankCardCode;
    }

    public void setBankCardCode(String bankCardCode) {
        this.bankCardCode = bankCardCode;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
}
