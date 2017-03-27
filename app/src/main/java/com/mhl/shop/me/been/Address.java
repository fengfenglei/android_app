package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：lff
 * 时间；2016-11-23 11:24
 * 描述：地址列表
 */
public class Address {
    /**
     * code : 200
     * info :
     * data : {"rows":[{"pkId":"793754907562812832","consignee":"陈桦","location":"","province":"广东省 深圳市 罗湖区","city":"","district":"","street":"","village":"","addressInfo":"桂园街道名仁阁A栋A2203号","zip":"","telephone":"","mobile":"18959717089","userId":"789035142067916800","defaultFlag":1}]}
     */

//    private int code;
//    private String info;
//    private DataBean data;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
        /**
         * pkId : 793754907562812832
         * consignee : 陈桦
         * location :
         * province : 广东省 深圳市 罗湖区
         * city :
         * district :
         * street :
         * village :
         * addressInfo : 桂园街道名仁阁A栋A2203号
         * zip :
         * telephone :
         * mobile : 18959717089
         * userId : 789035142067916800
         * defaultFlag : 1
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
            private String consignee;
            private String location;
            private String province;
            private String city;
            private String district;
            private String street;
            private String village;
            private String addressInfo;
            private String zip;
            private String telephone;
            private String mobile;
            private String userId;
            private int defaultFlag;

            public String getPkId() {
                return pkId;
            }

            public void setPkId(String pkId) {
                this.pkId = pkId;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getVillage() {
                return village;
            }

            public void setVillage(String village) {
                this.village = village;
            }

            public String getAddressInfo() {
                return addressInfo;
            }

            public void setAddressInfo(String addressInfo) {
                this.addressInfo = addressInfo;
            }

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getDefaultFlag() {
                return defaultFlag;
            }

            public void setDefaultFlag(int defaultFlag) {
                this.defaultFlag = defaultFlag;
            }
        }
//    }
}
