package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-29 11:30
 * 描述：
 */
public class Coupon {

    /**
     * pkId : 791160765796192256
     * couponType : 22
     * couponAmount : 20.0
     * couponOrderAmount : 10.0
     * startTime : 1454601600000
     * endTime : 1451404800000
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
        private int couponType;
        private double couponAmount;
        private double couponOrderAmount;
        private long startTime;
        private long endTime;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public double getCouponOrderAmount() {
            return couponOrderAmount;
        }

        public void setCouponOrderAmount(double couponOrderAmount) {
            this.couponOrderAmount = couponOrderAmount;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }
}
