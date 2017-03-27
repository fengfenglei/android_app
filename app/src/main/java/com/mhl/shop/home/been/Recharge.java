package com.mhl.shop.home.been;

/**
 * 作者：Administrator
 * 时间；2017-2-7 18:20
 * 描述：
 */
public class Recharge {

    /**
     * payMoney : 30.0
     * orderVirtualId : 828911249537503232
     * couponMoney : 0.0
     * goldMoney : 0.0
     * activationMoney : 0.0
     * depositMoney : 0.0
     * totalPrice : 30.0
     */

    private double payMoney;
    private String orderVirtualId;
    private double couponMoney;
    private double goldMoney;
    private double activationMoney;
    private double depositMoney;
    private double totalPrice;

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderVirtualId() {
        return orderVirtualId;
    }

    public void setOrderVirtualId(String orderVirtualId) {
        this.orderVirtualId = orderVirtualId;
    }

    public double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public double getGoldMoney() {
        return goldMoney;
    }

    public void setGoldMoney(double goldMoney) {
        this.goldMoney = goldMoney;
    }

    public double getActivationMoney() {
        return activationMoney;
    }

    public void setActivationMoney(double activationMoney) {
        this.activationMoney = activationMoney;
    }

    public double getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(double depositMoney) {
        this.depositMoney = depositMoney;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
