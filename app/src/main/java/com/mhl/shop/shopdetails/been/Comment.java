package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-8 15:24
 * 描述：评价
 */
public class Comment {

    /**
     * addTime : 1480319439000
     * userEvalContent : 测试评价
     * storeEvalContent : 供应商回复
     * goodsScore : 3
     * userIdName : 135****3932
     * isHiddenUsername : 2
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private long addTime;
        private String userEvalContent;
        private String storeEvalContent;
        private int goodsScore;
        private String userIdName;
        private int isHiddenUsername;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getUserEvalContent() {
            return userEvalContent;
        }

        public void setUserEvalContent(String userEvalContent) {
            this.userEvalContent = userEvalContent;
        }

        public String getStoreEvalContent() {
            return storeEvalContent;
        }

        public void setStoreEvalContent(String storeEvalContent) {
            this.storeEvalContent = storeEvalContent;
        }

        public int getGoodsScore() {
            return goodsScore;
        }

        public void setGoodsScore(int goodsScore) {
            this.goodsScore = goodsScore;
        }

        public String getUserIdName() {
            return userIdName;
        }

        public void setUserIdName(String userIdName) {
            this.userIdName = userIdName;
        }

        public int getIsHiddenUsername() {
            return isHiddenUsername;
        }

        public void setIsHiddenUsername(int isHiddenUsername) {
            this.isHiddenUsername = isHiddenUsername;
        }
    }
}
