package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-28 14:30
 * 描述：
 */
public class Collect {

    /**
     * pkId : 1
     * goodsId : 798002192081620992
     * goodsName : 测试商品1
     * goodsPrice : 1.0
     * currentPrice : 888.0
     * goodsPhotoId : 0
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
        private String goodsId;
        private String goodsName;
        private double goodsPrice;
        private double currentPrice;
        private String goodsPhotoId;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getGoodsPhotoId() {
            return goodsPhotoId;
        }

        public void setGoodsPhotoId(String goodsPhotoId) {
            this.goodsPhotoId = goodsPhotoId;
        }
    }
}
