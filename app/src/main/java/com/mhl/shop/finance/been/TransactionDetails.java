package com.mhl.shop.finance.been;

/**
 * 作者：Administrator
 * 时间；2017-1-3 21:14
 * 描述：
 */
public class TransactionDetails {


    /**
     * runningNo : 170103AB0190639024
     * remark : 为测试人员添加测试金额, ￥222.0
     * recordTime : 1483436221000
     * changeAmountStr : +222
     * orderTypeStr : 其它增加
     */

    private String runningNo;
    private String remark;
    private long recordTime;
    private String changeAmountStr;
    private String orderTypeStr;
    private String fundTypeStr;
    public String getFundTypeStr() {
        return fundTypeStr;
    }

    public void setFundTypeStr(String fundTypeStr) {
        this.fundTypeStr = fundTypeStr;
    }



    public String getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(String runningNo) {
        this.runningNo = runningNo;
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

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }
}
