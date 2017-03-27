package com.mhl.shop.finance.been;

import java.util.List;

/**
 * 作者：LFF
 * 时间；2017-1-3 17:39
 * 描述：交易记录
 */
public class TransactionRecord {

    /**
     * pkId : 816217555172724736
     * remark : 为测试人员添加测试金额, ￥222.0
     * recordTime : 1483436221000
     * changeAmountStr : +222.0
     * fundTypeStr : 激活金额
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
        private String fundTypeStr;

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

        public String getFundTypeStr() {
            return fundTypeStr;
        }

        public void setFundTypeStr(String fundTypeStr) {
            this.fundTypeStr = fundTypeStr;
        }
    }
}
