package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-29 17:30
 * 描述：
 */
public class History {

    /**
     * pkId : 1
     * goodsId : 798002192081620992
     * goodsName : 测试商品1
     * goodsPicId : f12918fd82e8444e95965aec851043f2
     * shoppingPrice : 888.0
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
        private String goodsPicId;
        private double shoppingPrice;

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

        public String getGoodsPicId() {
            return goodsPicId;
        }

        public void setGoodsPicId(String goodsPicId) {
            this.goodsPicId = goodsPicId;
        }

        public double getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(double shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }
    }
}
