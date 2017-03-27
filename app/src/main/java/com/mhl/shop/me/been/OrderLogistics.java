package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-22 17:53
 * 描述：
 */
public class OrderLogistics {


    /**
     * expressCompany : 中通快递
     * expressNo : 420510260186
     * logo : 6d0ab90785fe4438871a320ba681e686
     * state : 3
     * expressProgress : [{"context":"[新南山科技北] [深圳市] [新南山科技北]的派件已签收 感谢使用中通快递,期待再次为您服务!","time":"2016-12-02 13:05:23","ftime":"2016-12-02 13:05:23"},{"context":"[新南山科技北] [深圳市] 快件已到达[新南山科技北],业务员李剑豪正在第1次派件 电话:13727811186 请保持电话畅通、耐心等待","time":"2016-12-02 09:29:53","ftime":"2016-12-02 09:29:53"},{"context":"[深圳中心] [深圳市] 快件离开 [深圳中心]已发往[新南山科技北]","time":"2016-12-02 02:48:40","ftime":"2016-12-02 02:48:40"},{"context":"[深圳中心] [深圳市] 快件到达 [深圳中心]","time":"2016-12-02 01:43:53","ftime":"2016-12-02 01:43:53"},{"context":"[佛山中心] [佛山市] 快件离开 [佛山中心]已发往[深圳中心]","time":"2016-12-01 23:40:47","ftime":"2016-12-01 23:40:47"},{"context":"[佛山中心] [佛山市] 快件到达 [佛山中心]","time":"2016-12-01 22:24:27","ftime":"2016-12-01 22:24:27"},{"context":"[南海盐步] [佛山市] 快件离开 [南海盐步]已发往[深圳中心]","time":"2016-12-01 20:13:01","ftime":"2016-12-01 20:13:01"},{"context":"[南海盐步] [佛山市] [南海盐步]的谷海亭已收件 电话:18680043330","time":"2016-12-01 18:58:27","ftime":"2016-12-01 18:58:27"}]
     */

    private String expressCompany;
    private String expressNo;
    private String logo;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;
    /**
     * context : [新南山科技北] [深圳市] [新南山科技北]的派件已签收 感谢使用中通快递,期待再次为您服务!
     * time : 2016-12-02 13:05:23
     * ftime : 2016-12-02 13:05:23
     */

    private List<ExpressProgressBean> expressProgress;

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }



    public List<ExpressProgressBean> getExpressProgress() {
        return expressProgress;
    }

    public void setExpressProgress(List<ExpressProgressBean> expressProgress) {
        this.expressProgress = expressProgress;
    }

    public static class ExpressProgressBean {
        private String context;
        private String time;
        private String ftime;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
