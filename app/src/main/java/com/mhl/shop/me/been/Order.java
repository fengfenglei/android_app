package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-14 15:19
 * 描述：订单
 */
public class Order {


    /**
     * pkId : 808853433279975424
     * orderVirtualId : 808853433120591872
     * orderNo : 808853433191895040
     * orderStatus : 11
     * totalPrice : 306.5
     * freightMoney : 212.0
     * goodsCount : 70
     * goodsId : 800644864403443712
     * goodsIdPrice : 1.35
     * goodsIdName : 测试2
     * goodsIdPic : 66b0d7dd4a4f496c88afc1325018cc10
     * goodsSpec : 精油分类:单方精油 精油功效:振奋精神 适用部位:手部
     * supplierId : 794820743232884736
     * supplierIdName : 城市
     * virtualOrderNo : M808853433120591872
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * convergeTotalPrice : 306.5
     * convergeFreightMoney : 212.0
     * convergeGoodsCount : 70
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
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
        private String virtualOrderNo;
        private String identificationId;
        private double convergeTotalPrice;
        private double convergeFreightMoney;
        private int convergeGoodsCount;
        private String goodsSpecDetailId;
        private String orderExpressId;

        public String getOrderExpressId() {
            return orderExpressId;
        }

        public void setOrderExpressId(String orderExpressId) {
            this.orderExpressId = orderExpressId;
        }

        public String getGoodsSpecDetailId() {
            return goodsSpecDetailId;
        }

        public void setGoodsSpecDetailId(String goodsSpecDetailId) {
            this.goodsSpecDetailId = goodsSpecDetailId;
        }

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

        public String getVirtualOrderNo() {
            return virtualOrderNo;
        }

        public void setVirtualOrderNo(String virtualOrderNo) {
            this.virtualOrderNo = virtualOrderNo;
        }

        public String getIdentificationId() {
            return identificationId;
        }

        public void setIdentificationId(String identificationId) {
            this.identificationId = identificationId;
        }

        public double getConvergeTotalPrice() {
            return convergeTotalPrice;
        }

        public void setConvergeTotalPrice(double convergeTotalPrice) {
            this.convergeTotalPrice = convergeTotalPrice;
        }

        public double getConvergeFreightMoney() {
            return convergeFreightMoney;
        }

        public void setConvergeFreightMoney(double convergeFreightMoney) {
            this.convergeFreightMoney = convergeFreightMoney;
        }

        public int getConvergeGoodsCount() {
            return convergeGoodsCount;
        }

        public void setConvergeGoodsCount(int convergeGoodsCount) {
            this.convergeGoodsCount = convergeGoodsCount;
        }
    }
}
