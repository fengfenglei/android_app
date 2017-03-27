package com.mhl.shop.me.been;

/**
 * 作者：Administrator
 * 时间；2017-1-20 18:43
 * 描述：
 */
public class CertificationResult {


    /**
     * pkId : 822373708873207808
     * applicant : **h
     * idCardCode : 440301***********6
     * applicationTime : 1484904029000
     * auditStatus : 3
     * auditOpinion :
     */

    private String pkId;
    private String applicant;
    private String idCardCode;
    private long applicationTime;
    private int auditStatus;
    private String auditOpinion;
    private long auditTime;
    public long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(long auditTime) {
        this.auditTime = auditTime;
    }


    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getIdCardCode() {
        return idCardCode;
    }

    public void setIdCardCode(String idCardCode) {
        this.idCardCode = idCardCode;
    }

    public long getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(long applicationTime) {
        this.applicationTime = applicationTime;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
