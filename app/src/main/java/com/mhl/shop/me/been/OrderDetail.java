package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-17 16:33
 * 描述：
 */
public class OrderDetail {


    /**
     * pkId : 823800381694939136
     * addTime : 1485244167000
     * orderExpressId : 824102730242068480
     * orderVirtualId : 823800381585887232
     * orderNo : 823800381640413184
     * orderStatus : 13
     * payType : 99
     * totalPrice : 80.0
     * couponMoney : 0.0
     * goldMoney : 1.0
     * activationMoney : 79.0
     * depositMoney : 0.0
     * payMoney : 0.0
     * freightMoney : 0.0
     * goodsCount : 1
     * goodsId : 822002010420285440
     * goodsIdPrice : 80.0
     * goodsIdName : sz专属-保健酒1（普通）
     * goodsIdPic : 51f08729d19d4458b12564f848855ec5
     * goodsSpecDetailId : 822002010541920256
     * goodsSpec : 默认规格:默认
     * supplierId : 813566388076679168
     * supplierIdName : sz专属-供应商01
     * consignee : sz专属-test27
     * consigneeTel : 15013623827
     * addressName : 广东省深圳市南山区粤海街道办事处科技园社区居委会大冲商务中心
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * track : {"expressProgress":[{"context":"[新南山科技北] [深圳市] [新南山科技北]的派件已签收 感谢使用中通快递,期待再次为您服务!","time":"2016-12-02 13:05:23","ftime":"2016-12-02 13:05:23"}]}
     */

    private String pkId;
    private long addTime;
    private String orderExpressId;
    private String orderVirtualId;
    private String orderNo;
    private int orderStatus;
    private int payType;
    private double totalPrice;
    private double couponMoney;
    private double goldMoney;
    private double activationMoney;
    private double depositMoney;
    private double payMoney;
    private double freightMoney;
    private int goodsCount;
    private String goodsId;
    private double goodsIdPrice;
    private String goodsIdName;
    private String goodsIdPic;
    private String goodsSpecDetailId;
    private String goodsSpec;
    private String supplierId;
    private String supplierIdName;
    private String consignee;
    private String consigneeTel;
    private String addressName;
    private String identificationId;
    private TrackBean track;

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    private String  leaveMessage;
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

    public String getOrderExpressId() {
        return orderExpressId;
    }

    public void setOrderExpressId(String orderExpressId) {
        this.orderExpressId = orderExpressId;
    }

    public String getOrderVirtualId() {
        return orderVirtualId;
    }

    public void setOrderVirtualId(String orderVirtualId) {
        this.orderVirtualId = orderVirtualId;
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

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(double freightMoney) {
        this.freightMoney = freightMoney;
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

    public String getGoodsSpecDetailId() {
        return goodsSpecDetailId;
    }

    public void setGoodsSpecDetailId(String goodsSpecDetailId) {
        this.goodsSpecDetailId = goodsSpecDetailId;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierIdName() {
        return supplierIdName;
    }

    public void setSupplierIdName(String supplierIdName) {
        this.supplierIdName = supplierIdName;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public TrackBean getTrack() {
        return track;
    }

    public void setTrack(TrackBean track) {
        this.track = track;
    }

    public static class TrackBean {
        /**
         * context : [新南山科技北] [深圳市] [新南山科技北]的派件已签收 感谢使用中通快递,期待再次为您服务!
         * time : 2016-12-02 13:05:23
         * ftime : 2016-12-02 13:05:23
         */

        private List<ExpressProgressBean> expressProgress;

        public List<ExpressProgressBean> getExpressProgress() {
            return expressProgress;
        }

        public void setExpressProgress(List<ExpressProgressBean> expressProgress) {
            this.expressProgress = expressProgress;
        }

        public static class ExpressProgressBean {
            private String context;
            private String time;
            private String ftime;

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getFtime() {
                return ftime;
            }

            public void setFtime(String ftime) {
                this.ftime = ftime;
            }
        }
    }
}
