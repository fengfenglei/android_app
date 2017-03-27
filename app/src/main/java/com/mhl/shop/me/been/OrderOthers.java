package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-19 14:43
 * 描述：
 */
public class OrderOthers {

    /**
     * pkId : 808857387644620800
     * addTime : 1481681453000
     * virtualOrderNo : M808857387644620800
     * orderStatus : 12
     * totalPrice : 182.0
     * couponMoney : 0.0
     * goldMoney : 0.0
     * activationMoney : 0.0
     * depositMoney : 0.0
     * payMoney : 182.0
     * freightMoney : 128.0
     * consignee : 曾祥喜
     * consigneeTel : 13510273932
     * addressIdName : 广东省深圳市福田区竹子林
     * addressIdZip : 518040
     * orderDetail : [{"pkId":"808857387854336000","orderVirtualId":"808857387644620800","orderNo":"808857387715923968","orderStatus":44,"totalPrice":45.5,"freightMoney":32,"goodsCount":10,"goodsId":"800644864403443712","goodsIdPrice":1.35,"goodsIdName":"测试2","goodsIdPic":"66b0d7dd4a4f496c88afc1325018cc10","goodsSpec":"精油分类:复方精油 精油功效:振奋精神 适用部位:手部","supplierId":"794820743232884736","supplierIdName":"北京供应商中心","identificationId":"148edffc672846028ce97ca65fb7ff56","leaveMessage":"test"},{"pkId":"808857387778838528","orderVirtualId":"808857387644620800","orderNo":"808857387715923968","orderStatus":43,"totalPrice":45.5,"freightMoney":32,"goodsCount":10,"goodsId":"800644864403443712","goodsIdPrice":1.35,"goodsIdName":"测试2商品名称很长很长的测试2商品名称很长很长的测试2商品名称很长很长的","goodsIdPic":"66b0d7dd4a4f496c88afc1325018cc10","goodsSpec":"精油分类:单方精油 精油功效:振奋精神","supplierId":"794820743232884736","supplierIdName":"北京供应商中心","identificationId":"148edffc672846028ce97ca65fb7ff56","leaveMessage":"test"},{"pkId":"808857388034691072","orderVirtualId":"808857387644620800","orderNo":"808857387963387904","orderStatus":11,"totalPrice":45.5,"freightMoney":32,"goodsCount":10,"goodsId":"798005474975944704","goodsIdPrice":1.35,"goodsIdName":"测试9","goodsIdPic":"66b0d7dd4a4f496c88afc1325018cc10","goodsSpec":"颜色:红色 尺寸:36","supplierId":"794820743232884735","supplierIdName":"城市","leaveMessage":"test"},{"pkId":"808857388101799936","orderVirtualId":"808857387644620800","orderNo":"808857387963387904","orderStatus":11,"totalPrice":45.5,"freightMoney":32,"goodsCount":10,"goodsId":"798004991188144128","goodsIdPrice":1.35,"goodsIdName":"测试7","goodsIdPic":"66b0d7dd4a4f496c88afc1325018cc10","goodsSpec":"颜色:红色 尺寸:36","supplierId":"794820743232884735","supplierIdName":"城市","leaveMessage":"test"}]
     */

    private String pkId;
    private long addTime;
    private String virtualOrderNo;
    private int orderStatus;
    private double totalPrice;
    private double couponMoney;
    private double goldMoney;
    private double activationMoney;
    private double depositMoney;
    private double payMoney;
    private double freightMoney;
    private String consignee;
    private String consigneeTel;
    private String addressIdName;
    private String addressIdZip;
    /**
     * pkId : 808857387854336000
     * orderVirtualId : 808857387644620800
     * orderNo : 808857387715923968
     * orderStatus : 44
     * totalPrice : 45.5
     * freightMoney : 32.0
     * goodsCount : 10
     * goodsId : 800644864403443712
     * goodsIdPrice : 1.35
     * goodsIdName : 测试2
     * goodsIdPic : 66b0d7dd4a4f496c88afc1325018cc10
     * goodsSpec : 精油分类:复方精油 精油功效:振奋精神 适用部位:手部
     * supplierId : 794820743232884736
     * supplierIdName : 北京供应商中心
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * leaveMessage : test
     */

    private List<OrderDetailBean> orderDetail;

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

    public String getVirtualOrderNo() {
        return virtualOrderNo;
    }

    public void setVirtualOrderNo(String virtualOrderNo) {
        this.virtualOrderNo = virtualOrderNo;
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

    public String getAddressIdName() {
        return addressIdName;
    }

    public void setAddressIdName(String addressIdName) {
        this.addressIdName = addressIdName;
    }

    public String getAddressIdZip() {
        return addressIdZip;
    }

    public void setAddressIdZip(String addressIdZip) {
        this.addressIdZip = addressIdZip;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderDetailBean {
        private String pkId;
        private String orderVirtualId;
        private String orderNo;
        private int orderStatus;
        private double totalPrice;
        private double freightMoney;
        private int goodsCount;
        private String goodsId;
        private double goodsIdPrice;
        private String goodsIdName;
        private String goodsIdPic;
        private String goodsSpec;
        private String supplierId;
        private String supplierIdName;
        private String identificationId;
        private String leaveMessage;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
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

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
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

        public String getIdentificationId() {
            return identificationId;
        }

        public void setIdentificationId(String identificationId) {
            this.identificationId = identificationId;
        }

        public String getLeaveMessage() {
            return leaveMessage;
        }

        public void setLeaveMessage(String leaveMessage) {
            this.leaveMessage = leaveMessage;
        }
    }
}
