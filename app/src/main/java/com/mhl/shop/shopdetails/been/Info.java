package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-8 18:02
 * 描述：规格
 */
public class Info {



    /**
     * pkId : 798001123180023808
     * specName : 颜色:红色_尺寸:36
     * specNum : 86
     * specPrice : 1.35
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
        private String specName;
        private int specNum;
        private double specPrice;

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

        public int getSpecNum() {
            return specNum;
        }

        public void setSpecNum(int specNum) {
            this.specNum = specNum;
        }

        public double getSpecPrice() {
            return specPrice;
        }

        public void setSpecPrice(double specPrice) {
            this.specPrice = specPrice;
        }
    }
}
