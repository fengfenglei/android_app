package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-13 18:25
 * 描述：
 */
public class Submit {

    /**
     * availableBalance : 3.33
     * gold : 30
     * activationBalance : 1.13
     */

    private UserBalanceBean userBalance;
    /**
     * pkId : 799144184341729280
     * consignee : 测试收货人1
     * location :
     * province : 广东省
     * city : 深圳市
     * district : 南山区
     * street : 铜鼓路
     * village : 华润城大冲商务中心
     * addressInfo : 大冲商务中卖货浪信息技术有限公司
     * zip :
     * telephone :
     * mobile : 13510273932
     * defaultFlag : 1
     */

    private UserAddressBean userAddress;
    /**
     * userGoodsCart : [{"pkId":"813574316443701248","goodsId":"800644864403443712","goodsPicId":"4afd6cb0c24249cc88718bbd429948ae","goodsIdName":"测试2","goodsCount":70,"goodsSpecInfo":"精油分类:单方精油 精油功效:振奋精神 适用部位:手部","supplierId":"794820743232884736","supplierIdName":"城市","currentPrice":1.35,"freightMoney":212,"expressWay":1},{"pkId":"813574414040961024","goodsId":"800651876407316480","goodsPicId":"66b0d7dd4a4f496c88afc1325018cc10","goodsIdName":"印度神油","goodsCount":2,"goodsSpecInfo":"精油分类:基础油_精油功效:舒缓精神_适用部位:臀部_精油心理疗效:改善失眠","supplierId":"794820743232884736","supplierIdName":"北京供应商中心","currentPrice":1.35,"freightMoney":10,"expressWay":1},{"pkId":"813578750674472960","goodsId":"800644864403443712","goodsPicId":"4afd6cb0c24249cc88718bbd429948ae","goodsIdName":"测试2","goodsCount":1,"goodsSpecInfo":"精油分类:复方精油_精油功效:舒缓精神_适用部位:足部","supplierId":"794820743232884736","supplierIdName":"北京供应商中心","currentPrice":1.35,"freightMoney":5,"expressWay":1},{"pkId":"813578774351319040","goodsId":"800636614668849152","goodsPicId":"66b0d7dd4a4f496c88afc1325018cc10","goodsIdName":"测试1","goodsCount":1,"goodsSpecInfo":"精油分类:单方精油_精油功效:振奋精神_适用部位:足部","supplierId":"794820743232884736","supplierIdName":"·北京供应商中心","currentPrice":1.35,"freightMoney":5,"expressWay":1},{"pkId":"813578793804500992","goodsId":"800538375986745344","goodsPicId":"f12918fd82e8444e95965aec851043f2","goodsIdName":"测试","goodsCount":1,"goodsSpecInfo":"精油分类:复方精油_精油功效:舒缓精神","supplierId":"788987748924133376","supplierIdName":"lbl1","currentPrice":1.35,"freightMoney":5,"expressWay":1},{"pkId":"813578814453059584","goodsId":"800538375986745344","goodsPicId":"f12918fd82e8444e95965aec851043f2","goodsIdName":"测试","goodsCount":1,"goodsSpecInfo":"精油分类:单方精油_精油功效:振奋精神","supplierId":"788987748924133376","supplierIdName":"lbl1","currentPrice":1.35,"freightMoney":5,"expressWay":1}]
     * userGetCoupon : [{"pkId":"791163425148506112","couponType":22,"couponAmount":14,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791163424280285184","couponType":22,"couponAmount":15,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791163422707421184","couponType":22,"couponAmount":16,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791163421965029376","couponType":22,"couponAmount":17,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791163421096808448","couponType":22,"couponAmount":18,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791163418840272896","couponType":22,"couponAmount":19,"couponOrderAmount":10,"endTime":1487088000000},{"pkId":"791160765796192256","couponType":22,"couponAmount":20,"couponOrderAmount":10,"endTime":1487088000000}]
     * userBalance : {"availableBalance":3.33,"gold":30,"activationBalance":1.13}
     * userAddress : {"pkId":"799144184341729280","consignee":"测试收货人1","location":"","province":"广东省","city":"深圳市","district":"南山区","street":"铜鼓路","village":"华润城大冲商务中心","addressInfo":"大冲商务中卖货浪信息技术有限公司","zip":"","telephone":"","mobile":"13510273932","defaultFlag":1}
     * payPwdFlag : 1
     */

    private int payPwdFlag;
    private Double activationCeiling;
    private Double availableCeiling;

    public Double getAvailableCeiling() {
        return availableCeiling;
    }

    public void setAvailableCeiling(Double availableCeiling) {
        this.availableCeiling = availableCeiling;
    }

    public Double getActivationCeiling() {
        return activationCeiling;
    }

    public void setActivationCeiling(Double activationCeiling) {
        this.activationCeiling = activationCeiling;
    }

    /**
     * pkId : 813574316443701248
     * goodsId : 800644864403443712
     * goodsPicId : 4afd6cb0c24249cc88718bbd429948ae
     * goodsIdName : 测试2
     * goodsCount : 70
     * goodsSpecInfo : 精油分类:单方精油 精油功效:振奋精神 适用部位:手部
     * supplierId : 794820743232884736
     * supplierIdName : 城市
     * currentPrice : 1.35
     * freightMoney : 212.0
     * expressWay : 1
     */

    private List<UserGoodsCartBean> userGoodsCart;
    /**
     * pkId : 791163425148506112
     * couponType : 22
     * couponAmount : 14.0
     * couponOrderAmount : 10.0
     * endTime : 1487088000000
     */

    private List<UserGetCouponBean> userGetCoupon;

    public UserBalanceBean getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(UserBalanceBean userBalance) {
        this.userBalance = userBalance;
    }

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

    public int getPayPwdFlag() {
        return payPwdFlag;
    }

    public void setPayPwdFlag(int payPwdFlag) {
        this.payPwdFlag = payPwdFlag;
    }

    public List<UserGoodsCartBean> getUserGoodsCart() {
        return userGoodsCart;
    }

    public void setUserGoodsCart(List<UserGoodsCartBean> userGoodsCart) {
        this.userGoodsCart = userGoodsCart;
    }

    public List<UserGetCouponBean> getUserGetCoupon() {
        return userGetCoupon;
    }

    public void setUserGetCoupon(List<UserGetCouponBean> userGetCoupon) {
        this.userGetCoupon = userGetCoupon;
    }

    public static class UserBalanceBean {
        private double availableBalance;
        private int gold;
        private double activationBalance;

        public double getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(double availableBalance) {
            this.availableBalance = availableBalance;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public double getActivationBalance() {
            return activationBalance;
        }

        public void setActivationBalance(double activationBalance) {
            this.activationBalance = activationBalance;
        }
    }

    public static class UserAddressBean {
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

        public int getDefaultFlag() {
            return defaultFlag;
        }

        public void setDefaultFlag(int defaultFlag) {
            this.defaultFlag = defaultFlag;
        }
    }

    public static class UserGoodsCartBean {
        private String pkId;
        private String goodsId;
        private String goodsPicId;
        private String goodsIdName;
        private int goodsCount;
        private String goodsSpecInfo;
        private String supplierId;
        private String supplierIdName;
        private double currentPrice;
        private double freightMoney;
        private int expressWay;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsPicId() {
            return goodsPicId;
        }

        public void setGoodsPicId(String goodsPicId) {
            this.goodsPicId = goodsPicId;
        }

        public String getGoodsIdName() {
            return goodsIdName;
        }

        public void setGoodsIdName(String goodsIdName) {
            this.goodsIdName = goodsIdName;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getGoodsSpecInfo() {
            return goodsSpecInfo;
        }

        public void setGoodsSpecInfo(String goodsSpecInfo) {
            this.goodsSpecInfo = goodsSpecInfo;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierIdName() {
            return supplierIdName;
        }

        public void setSupplierIdName(String supplierIdName) {
            this.supplierIdName = supplierIdName;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public double getFreightMoney() {
            return freightMoney;
        }
        public void setFreightMoney(double freightMoney) {
            this.freightMoney = freightMoney;
        }

        public int getExpressWay() {
            return expressWay;
        }

        public void setExpressWay(int expressWay) {
            this.expressWay = expressWay;
        }
    }

    public static class UserGetCouponBean {
        private String pkId;
        private int couponType;
        private double couponAmount;
        private double couponOrderAmount;
        private long endTime;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public double getCouponOrderAmount() {
            return couponOrderAmount;
        }

        public void setCouponOrderAmount(double couponOrderAmount) {
            this.couponOrderAmount = couponOrderAmount;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }
}
