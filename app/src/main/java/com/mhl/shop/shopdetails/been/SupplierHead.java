package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-9 15:05
 * 描述：供应商评分
 */
public class SupplierHead {


    /**
     * supplierName : 北京供应商中心
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * satisfactionDegree : 2.0
     * supplierGoodsScore : 2.0
     * supplierServiceScore : 2.0
     * logisticsServiceScore : 2.0
     * supplierGoodsNum : 12
     * supplierIsCollect : 1
     * supplierSlide : [{"picId":"3b8c55c1db0d431ca57c1db5d072f7d3","goodsLink":"http://www.51mhl.com/goods/233322.htm"},{"picId":"8540c707acec4d37906b7aadadbe57a3","goodsLink":"sfgwf"},{"picId":"843db0b0700946e5aafaa68eed80fd98","goodsLink":"ddddd"}]
     */

    private String supplierName;
    private String identificationId;
    private double satisfactionDegree;
    private double supplierGoodsScore;
    private double supplierServiceScore;
    private double logisticsServiceScore;
    private int supplierGoodsNum;
    private int supplierIsCollect;
    /**
     * picId : 3b8c55c1db0d431ca57c1db5d072f7d3
     * goodsLink : http://www.51mhl.com/goods/233322.htm
     */

    private List<SupplierSlideBean> supplierSlide;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public int getSupplierIsCollect() {
        return supplierIsCollect;
    }

    public void setSupplierIsCollect(int supplierIsCollect) {
        this.supplierIsCollect = supplierIsCollect;
    }

    public List<SupplierSlideBean> getSupplierSlide() {
        return supplierSlide;
    }

    public void setSupplierSlide(List<SupplierSlideBean> supplierSlide) {
        this.supplierSlide = supplierSlide;
    }

    public static class SupplierSlideBean {
        private String picId;
        private String goodsLink;

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }

        public String getGoodsLink() {
            return goodsLink;
        }

        public void setGoodsLink(String goodsLink) {
            this.goodsLink = goodsLink;
        }
    }
}
