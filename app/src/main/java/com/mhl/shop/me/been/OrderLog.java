package com.mhl.shop.me.been;

/**
 * 作者：Administrator
 * 时间；2016-12-19 18:26
 * 描述：
 */
public class OrderLog {

    /**
     * addTime : 1482141975000
     * operationInfo : 会员申请退货退款(已发货)，等待供应商审核
     */

    private long addTime;
    private String operationInfo;

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }
}
