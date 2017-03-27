package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * Created by kapokcloud on 2016/12/8.
 */

public class OldInfo {

    /**
     * pkId : 800651876449259520
     * specName : 精油分类:基础油_精油功效:舒缓精神_适用部位:臀部_精油心理疗效:改善失眠
     * specNum : 100
     * specPrice : 1.35
     */

    private List<RowsBean> rows;

//

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
