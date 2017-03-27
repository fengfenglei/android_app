package com.mhl.shop.homepage.bean;

/**
 * 作者：Administrator
 * 时间；2017-1-19 16:18
 * 描述：
 */
public class Message {
    /**
     * pkId : 818733220748201984
     * noticeTitle : 订单已发货
     * noticeMsg : 印度神油
     * msg1 : 顺丰快递
     * msg2 : 123456716547
     * msg3 : 814667152115240960
     * msg4 : 99cbb960bca64bf0b0961eb08892c69c
     * sendTime : 1484035987000
     * msgType : 1
     * msgStatus : 1
     * detailsUrl :
     */

    private String pkId;
    private String noticeTitle;
    private String noticeMsg;
    private String msg1;
    private String msg2;
    private String msg3;
    private String msg4;
    private long sendTime;
    private int msgType;
    private int msgStatus;
    private String detailsUrl;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeMsg() {
        return noticeMsg;
    }

    public void setNoticeMsg(String noticeMsg) {
        this.noticeMsg = noticeMsg;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }

    public String getMsg4() {
        return msg4;
    }

    public void setMsg4(String msg4) {
        this.msg4 = msg4;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
