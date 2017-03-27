package com.mhl.shop.home.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-22 11:02
 * 描述：
 */
public class Feature {

    /**
     * pkId : 822375719811289088
     * title1 : 北京
     * img2 : 4c39ed9e39a14bd48a1ea09995a5ebab
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
        private String title1;
        private String img2;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }
    }
}
