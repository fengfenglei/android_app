package com.mhl.shop.finance.been;

/**
 * 作者：Administrator
 * 时间；2017-1-6 17:35
 * 描述：
 */
public class Balance {

    /**
     * fundStatus : 1
     * availableBalance : 7548.0
     * freezeBalance : 0.0
     * gold : 655698254
     * giftBalance : 7548.0
     * activationBalance : 7548.0
     * activationBalanceDueIn : 0.0
     */

    private int fundStatus;
    private double availableBalance;
    private double freezeBalance;
    private int gold;
    private double giftBalance;
    private double activationBalance;
    private double activationBalanceDueIn;

    public int getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(int fundStatus) {
        this.fundStatus = fundStatus;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(double giftBalance) {
        this.giftBalance = giftBalance;
    }

    public double getActivationBalance() {
        return activationBalance;
    }

    public void setActivationBalance(double activationBalance) {
        this.activationBalance = activationBalance;
    }

    public double getActivationBalanceDueIn() {
        return activationBalanceDueIn;
    }

    public void setActivationBalanceDueIn(double activationBalanceDueIn) {
        this.activationBalanceDueIn = activationBalanceDueIn;
    }
}
