package com.mhl.shop.finance.been;

/**
 * 作者：lff
 * 时间；2017-1-4 14:31
 * 描述：待激活
 */
public class ActivatedNo {

    /**
     * pkId : 814707265876660224
     * orderNo : 814707265763414016
     * totalPrice : 10.125
     * expectedActivationMoney : 2.85
     * expectedActivationDate : 1484496000000
     */

    private String pkId;
    private String orderNo;
    private double totalPrice;
    private String expectedActivationMoney;
    private long expectedActivationDate;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getExpectedActivationMoney() {
        return expectedActivationMoney;
    }

    public void setExpectedActivationMoney(String expectedActivationMoney) {
        this.expectedActivationMoney = expectedActivationMoney;
    }

    public long getExpectedActivationDate() {
        return expectedActivationDate;
    }

    public void setExpectedActivationDate(long expectedActivationDate) {
        this.expectedActivationDate = expectedActivationDate;
    }
}
