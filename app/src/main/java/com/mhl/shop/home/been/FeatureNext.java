package com.mhl.shop.home.been;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-22 11:55
 * 描述：
 */
public class FeatureNext implements Serializable {
    /**
     * gsName : sz专属-计算器3（普通）
     * gsImg : 2aef0f5be9c140dda4137465a074b17f
     * gsId : 822006614738472960
     * marketPrice : 5
     * shoppingPrice : 3
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String gsName;
        private String gsImg;
        private String gsId;
        private double marketPrice;

        public double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            this.marketPrice = marketPrice;
        }

        public double getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(double shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }

        private double shoppingPrice;

        public String getGsName() {
            return gsName;
        }

        public void setGsName(String gsName) {
            this.gsName = gsName;
        }

        public String getGsImg() {
            return gsImg;
        }

        public void setGsImg(String gsImg) {
            this.gsImg = gsImg;
        }

        public String getGsId() {
            return gsId;
        }

        public void setGsId(String gsId) {
            this.gsId = gsId;
        }


    }
}
