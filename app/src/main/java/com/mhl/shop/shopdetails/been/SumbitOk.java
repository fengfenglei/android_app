package com.mhl.shop.shopdetails.been;

import java.io.Serializable;

/**
 * 作者：Administrator
 * 时间；2016-12-28 15:24
 * 描述：
 */
public class SumbitOk implements Serializable {


    /**
     * orderNo : M814023510295973888
     * payMoney : 6.35
     * orderVirtualId : 814023510295973888
     * isPaySuccess : 2
     * orderStatus : 11
     * couponMoney : 0.0
     * goldMoney : 0.0
     * activationMoney : 0.0
     * depositMoney : 0.0
     * totalPrice : 6.35
     */

    private String orderNo;
    private double payMoney;
    private String orderVirtualId;
    private int isPaySuccess;
    private int orderStatus;
    private double couponMoney;
    private double goldMoney;
    private double activationMoney;
    private double depositMoney;
    private double totalPrice;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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

    public int getIsPaySuccess() {
        return isPaySuccess;
    }

    public void setIsPaySuccess(int isPaySuccess) {
        this.isPaySuccess = isPaySuccess;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
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
