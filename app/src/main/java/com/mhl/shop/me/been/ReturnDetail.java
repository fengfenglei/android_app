package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-20 18:32
 * 描述：
 */
public class ReturnDetail {

    /**
     * orderStatus : 14
     * goldMoney : 2.0
     * activationMoney : 3.0
     * depositMoney : 4.0
     * payMoney : 73.095
     * returnLogList : [{"pkId":"810789197412700161","addTime":1482228375000,"returnStatus":62,"operationInfo":"平台已通过审核，资金已原路退回付款账户","returnExplain":"Uiouo","returnPic":"","addressIdName":"","addressIdZip":"","consignee":"","consigneeTel":"","expressNo":"","expressCompany":""},{"pkId":"810789197412700160","addTime":1482141975000,"returnStatus":44,"operationInfo":"供应商已收货(等待平台审核)","returnExplain":"Uiouo","returnPic":""},{"pkId":"809205754375573504","addTime":1481764518000,"returnStatus":43,"operationInfo":"会员已发货，等待供应商确认收货","returnExplain":"bbb","returnPic":"efbb634e68d348f0b7e0ffbb26f488f4","expressNo":"xxxxx","expressCompany":"亿翔快递"},{"pkId":"809204727983247361","addTime":1481764333000,"returnStatus":42,"operationInfo":"供应商同意退货","returnExplain":"测试退款","returnPic":"5d87102a944a4d5393731f296c58d56c","addressIdName":"广东省南山区","addressIdZip":"10086","consignee":"测试人","consigneeTel":"","expressNo":"","expressCompany":""},{"pkId":"809204727983247360","addTime":1481764273000,"returnStatus":41,"operationInfo":"会员申请退货退款(已收货)，等待供应商审核","returnExplain":"测试退款","returnPic":"5d87102a944a4d5393731f296c58d56c"}]
     * orderReturnInfo : {"pkId":"810789197324619776","returnNo":"R810789197324619776","returnStatus":41,"returnMoney":78.22,"returnType":2,"returnReasonType":2,"returnExplain":"Uiouo","goodsId":"798001123146469376","goodsIdName":"测试商品名称都是很长很长的啊啊啊啊啊啊啊啊","goodsCount":7,"goodsIdPrice":1.35,"goodsIdPic":"66b0d7dd4a4f496c88afc1325018cc10","goodsSpecDetailId":"798001123180023808","goodsSpec":"颜色:红色 尺寸:36","supplierId":"794820743232884736","supplierIdName":"北京供应商中心"}
     * returnPayMoney : 3.094999999999999
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     */

    private int orderStatus;
    private double goldMoney;
    private double activationMoney;
    private double depositMoney;
    private double payMoney;
    private String pkId;
    private int payType;


    /**
     * pkId : 810789197324619776
     * returnNo : R810789197324619776
     * returnStatus : 41
     * returnMoney : 78.22
     * returnType : 2
     * returnReasonType : 2
     * returnExplain : Uiouo
     * goodsId : 798001123146469376
     * goodsIdName : 测试商品名称都是很长很长的啊啊啊啊啊啊啊啊
     * goodsCount : 7
     * goodsIdPrice : 1.35
     * goodsIdPic : 66b0d7dd4a4f496c88afc1325018cc10
     * goodsSpecDetailId : 798001123180023808
     * goodsSpec : 颜色:红色 尺寸:36
     * supplierId : 794820743232884736
     * supplierIdName : 北京供应商中心
     */

    private OrderReturnInfoBean orderReturnInfo;
    private double returnPayMoney;
    private String identificationId;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    /**
     * pkId : 810789197412700161
     * addTime : 1482228375000
     * returnStatus : 62
     * operationInfo : 平台已通过审核，资金已原路退回付款账户
     * returnExplain : Uiouo
     * returnPic :
     * addressIdName :
     * addressIdZip :
     * consignee :
     * consigneeTel :
     * expressNo :
     * expressCompany :
     */

    private List<ReturnLogListBean> returnLogList;

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getGoldMoney() {
        return goldMoney;
    }

    public void setGoldMoney(double goldMoney) {
        this.goldMoney = goldMoney;
    }

    public double getActivationMoney() {
        return activationMoney;
    }

    public void setActivationMoney(double activationMoney) {
        this.activationMoney = activationMoney;
    }

    public double getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(double depositMoney) {
        this.depositMoney = depositMoney;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public OrderReturnInfoBean getOrderReturnInfo() {
        return orderReturnInfo;
    }

    public void setOrderReturnInfo(OrderReturnInfoBean orderReturnInfo) {
        this.orderReturnInfo = orderReturnInfo;
    }

    public double getReturnPayMoney() {
        return returnPayMoney;
    }

    public void setReturnPayMoney(double returnPayMoney) {
        this.returnPayMoney = returnPayMoney;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public List<ReturnLogListBean> getReturnLogList() {
        return returnLogList;
    }

    public void setReturnLogList(List<ReturnLogListBean> returnLogList) {
        this.returnLogList = returnLogList;
    }

    public static class OrderReturnInfoBean {
        private String pkId;
        private String returnNo;
        private int returnStatus;
        private double returnMoney;
        private int returnType;
        private int returnReasonType;
        private String returnExplain;
        private String goodsId;
        private String goodsIdName;
        private int goodsCount;
        private double goodsIdPrice;
        private String goodsIdPic;
        private String goodsSpecDetailId;
        private String goodsSpec;
        private String supplierId;
        private String supplierIdName;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getReturnNo() {
            return returnNo;
        }

        public void setReturnNo(String returnNo) {
            this.returnNo = returnNo;
        }

        public int getReturnStatus() {
            return returnStatus;
        }

        public void setReturnStatus(int returnStatus) {
            this.returnStatus = returnStatus;
        }

        public double getReturnMoney() {
            return returnMoney;
        }

        public void setReturnMoney(double returnMoney) {
            this.returnMoney = returnMoney;
        }

        public int getReturnType() {
            return returnType;
        }

        public void setReturnType(int returnType) {
            this.returnType = returnType;
        }

        public int getReturnReasonType() {
            return returnReasonType;
        }

        public void setReturnReasonType(int returnReasonType) {
            this.returnReasonType = returnReasonType;
        }

        public String getReturnExplain() {
            return returnExplain;
        }

        public void setReturnExplain(String returnExplain) {
            this.returnExplain = returnExplain;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public double getGoodsIdPrice() {
            return goodsIdPrice;
        }

        public void setGoodsIdPrice(double goodsIdPrice) {
            this.goodsIdPrice = goodsIdPrice;
        }

        public String getGoodsIdPic() {
            return goodsIdPic;
        }

        public void setGoodsIdPic(String goodsIdPic) {
            this.goodsIdPic = goodsIdPic;
        }

        public String getGoodsSpecDetailId() {
            return goodsSpecDetailId;
        }

        public void setGoodsSpecDetailId(String goodsSpecDetailId) {
            this.goodsSpecDetailId = goodsSpecDetailId;
        }

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
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
    }

    public static class ReturnLogListBean {
        private String pkId;
        private long addTime;
        private int returnStatus;
        private String operationInfo;
        private String returnExplain;
        private String returnPic;
        private String addressIdName;
        private String addressIdZip;
        private String consignee;
        private String consigneeTel;
        private String expressNo;
        private String expressCompany;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public int getReturnStatus() {
            return returnStatus;
        }

        public void setReturnStatus(int returnStatus) {
            this.returnStatus = returnStatus;
        }

        public String getOperationInfo() {
            return operationInfo;
        }

        public void setOperationInfo(String operationInfo) {
            this.operationInfo = operationInfo;
        }

        public String getReturnExplain() {
            return returnExplain;
        }

        public void setReturnExplain(String returnExplain) {
            this.returnExplain = returnExplain;
        }

        public String getReturnPic() {
            return returnPic;
        }

        public void setReturnPic(String returnPic) {
            this.returnPic = returnPic;
        }

        public String getAddressIdName() {
            return addressIdName;
        }

        public void setAddressIdName(String addressIdName) {
            this.addressIdName = addressIdName;
        }

        public String getAddressIdZip() {
            return addressIdZip;
        }

        public void setAddressIdZip(String addressIdZip) {
            this.addressIdZip = addressIdZip;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getConsigneeTel() {
            return consigneeTel;
        }

        public void setConsigneeTel(String consigneeTel) {
            this.consigneeTel = consigneeTel;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }
    }
}
