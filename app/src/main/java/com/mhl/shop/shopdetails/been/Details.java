package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-8 10:23
 * 描述：商品详情
 */
public class Details {


    /**
     * pkId : 798001123146469376
     * goodsName : 测试商品2
     * goodsTitle : 测试副标题2
     * supplierId : 788987748924133376
     * supplierName : lbl1
     * statusFlag : 1
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * satisfactionDegree : 1.0
     * supplierGoodsScore : 5.0
     * supplierServiceScore : 5.0
     * logisticsServiceScore : 5.0
     * supplierGoodsNum : 7
     * goodsEvaluateNum : 1
     * goodsPics : [{"pictureId":"737b27ab8fa449279fdce6c50db258e6"},{"pictureId":"176fbd2a95134604beff286f4664a9ce"}]
     * goodsIsCollect : 2
     * supplierIsCollect : 2
     * minimumAmount : 0.0
     * minimumQuantity : 0
     * goodsSpecDetail : {"pkId":"798001123180023808","specName":"颜色:红色_尺寸:36","specPrice":1.35,"marketPrice":1}
     */

    private String pkId;
    private String goodsName;
    private String goodsTitle;
    private String supplierId;
    private String supplierName;
    private int statusFlag;
    private String identificationId;
    private double satisfactionDegree;
    private double supplierGoodsScore;
    private double supplierServiceScore;
    private double logisticsServiceScore;
    private int supplierGoodsNum;
    private int goodsEvaluateNum;
    private int goodsIsCollect;
    private int supplierIsCollect;
    private double minimumAmount;
    private int minimumQuantity;
    /**
     * pkId : 798001123180023808
     * specName : 颜色:红色_尺寸:36
     * specPrice : 1.35
     * marketPrice : 1.0
     */

    private GoodsSpecDetailBean goodsSpecDetail;
    /**
     * pictureId : 737b27ab8fa449279fdce6c50db258e6
     */

    private List<GoodsPicsBean> goodsPics;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(int statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public double getSatisfactionDegree() {
        return satisfactionDegree;
    }

    public void setSatisfactionDegree(double satisfactionDegree) {
        this.satisfactionDegree = satisfactionDegree;
    }

    public double getSupplierGoodsScore() {
        return supplierGoodsScore;
    }

    public void setSupplierGoodsScore(double supplierGoodsScore) {
        this.supplierGoodsScore = supplierGoodsScore;
    }

    public double getSupplierServiceScore() {
        return supplierServiceScore;
    }

    public void setSupplierServiceScore(double supplierServiceScore) {
        this.supplierServiceScore = supplierServiceScore;
    }

    public double getLogisticsServiceScore() {
        return logisticsServiceScore;
    }

    public void setLogisticsServiceScore(double logisticsServiceScore) {
        this.logisticsServiceScore = logisticsServiceScore;
    }

    public int getSupplierGoodsNum() {
        return supplierGoodsNum;
    }

    public void setSupplierGoodsNum(int supplierGoodsNum) {
        this.supplierGoodsNum = supplierGoodsNum;
    }

    public int getGoodsEvaluateNum() {
        return goodsEvaluateNum;
    }

    public void setGoodsEvaluateNum(int goodsEvaluateNum) {
        this.goodsEvaluateNum = goodsEvaluateNum;
    }

    public int getGoodsIsCollect() {
        return goodsIsCollect;
    }

    public void setGoodsIsCollect(int goodsIsCollect) {
        this.goodsIsCollect = goodsIsCollect;
    }

    public int getSupplierIsCollect() {
        return supplierIsCollect;
    }

    public void setSupplierIsCollect(int supplierIsCollect) {
        this.supplierIsCollect = supplierIsCollect;
    }

    public double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public GoodsSpecDetailBean getGoodsSpecDetail() {
        return goodsSpecDetail;
    }

    public void setGoodsSpecDetail(GoodsSpecDetailBean goodsSpecDetail) {
        this.goodsSpecDetail = goodsSpecDetail;
    }

    public List<GoodsPicsBean> getGoodsPics() {
        return goodsPics;
    }

    public void setGoodsPics(List<GoodsPicsBean> goodsPics) {
        this.goodsPics = goodsPics;
    }

    public static class GoodsSpecDetailBean {
        private String pkId;
        private String specName;
        private double specPrice;
        private double marketPrice;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public double getSpecPrice() {
            return specPrice;
        }

        public void setSpecPrice(double specPrice) {
            this.specPrice = specPrice;
        }

        public double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            this.marketPrice = marketPrice;
        }
    }

    public static class GoodsPicsBean {
        private String pictureId;

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }
    }
}
