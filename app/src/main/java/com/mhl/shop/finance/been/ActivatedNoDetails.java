package com.mhl.shop.finance.been;

/**
 * 作者：LFF
 * 时间；2017-1-5 20:22
 * 描述：待激活
 */
public class ActivatedNoDetails {


    /**
     * pkId : 814707265876660224
     * addTime : 1483421750000
     * confirmTime : 1483612836000
     * orderNo : 814707265763414016
     * orderStatus : 14
     * totalPrice : 10.125
     * goodsCount : 3
     * goodsId : 813960410666504192
     * goodsIdPrice : 2.53
     * goodsIdName : sz专属-精油（普通）
     * goodsIdPic : 99cbb960bca64bf0b0961eb08892c69c
     * goodsSpec : 精油分类:单方精油 精油功效:舒缓精神 适用部位:手部 精油心理疗效:改善失眠
     * expectedActivationMoney : 2.85
     * expectedActivationDate : 1484496000000
     */

    private String pkId;
    private long addTime;
    private long confirmTime;
    private String orderNo;
    private int orderStatus;
    private double totalPrice;
    private int goodsCount;
    private String goodsId;
    private double goodsIdPrice;
    private String goodsIdName;
    private String goodsIdPic;
    private String goodsSpec;
    private String expectedActivationMoney;
    private long expectedActivationDate;

    public String getCanActivationDate() {
        return canActivationDate;
    }

    public void setCanActivationDate(String canActivationDate) {
        this.canActivationDate = canActivationDate;
    }

    private String canActivationDate;



    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getGoodsIdPrice() {
        return goodsIdPrice;
    }

    public void setGoodsIdPrice(double goodsIdPrice) {
        this.goodsIdPrice = goodsIdPrice;
    }

    public String getGoodsIdName() {
        return goodsIdName;
    }

    public void setGoodsIdName(String goodsIdName) {
        this.goodsIdName = goodsIdName;
    }

    public String getGoodsIdPic() {
        return goodsIdPic;
    }

    public void setGoodsIdPic(String goodsIdPic) {
        this.goodsIdPic = goodsIdPic;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
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
