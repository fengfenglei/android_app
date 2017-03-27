package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-12-12 15:25
 * 描述：评价列表
 */
public class Evaluation {


    /**
     * addTime : 1481253994000
     * userEvalContent : dd
     * evalPicture : 66197f4973b049cdaeedef9402c32944,66197f4973b049cdaeedef9402c32944,66197f4973b049cdaeedef9402c32944,66197f4973b049cdaeedef9402c32944
     * goodsScore : 3
     * userIdName : 135****3932
     * userIdPic : d2b4ddcb928241f5a1a3bc9ba9c1319b
     * goodsSpec : 颜色:红色 尺寸:36
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
        private String evalPicture;
        private int goodsScore;
        private String userIdName;
        private String userIdPic;
        private String goodsSpec;
        private int isHiddenUsername;

        public String getStoreEvalContent() {
            return storeEvalContent;
        }

        public void setStoreEvalContent(String storeEvalContent) {
            this.storeEvalContent = storeEvalContent;
        }

        private String storeEvalContent;

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

        public String getEvalPicture() {
            return evalPicture;
        }

        public void setEvalPicture(String evalPicture) {
            this.evalPicture = evalPicture;
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

        public String getUserIdPic() {
            return userIdPic;
        }

        public void setUserIdPic(String userIdPic) {
            this.userIdPic = userIdPic;
        }

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
        }

        public int getIsHiddenUsername() {
            return isHiddenUsername;
        }

        public void setIsHiddenUsername(int isHiddenUsername) {
            this.isHiddenUsername = isHiddenUsername;
        }
    }
}
