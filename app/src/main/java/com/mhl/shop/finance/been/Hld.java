package com.mhl.shop.finance.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-3 15:27
 * 描述：
 */
public class Hld {

    /**
     * pkId : 23029
     * remark : 砸金蛋活动消费：50
     * recordTime : 1451714634000
     * changeAmountStr : -50
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
        private String remark;
        private long recordTime;
        private String changeAmountStr;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(long recordTime) {
            this.recordTime = recordTime;
        }

        public String getChangeAmountStr() {
            return changeAmountStr;
        }

        public void setChangeAmountStr(String changeAmountStr) {
            this.changeAmountStr = changeAmountStr;
        }
    }
}
