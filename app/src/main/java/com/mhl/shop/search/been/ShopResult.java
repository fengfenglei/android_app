package com.mhl.shop.search.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-13 18:35
 * 描述：
 */
public class ShopResult {
    private List<GoodsInfosBean> goodsInfos;

    public List<GoodsInfosBean> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<GoodsInfosBean> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }

    /**
     * pkId : 798002365373485056
     * goodsName : 测试商品3
     * marketPrice : 100.0
     * shoppingPrice : 60.0
     * picId : 0
     */

    private List<ChoicenesssBean> choicenesss;



    public List<ChoicenesssBean> getChoicenesss() {
        return choicenesss;
    }

    public void setChoicenesss(List<ChoicenesssBean> choicenesss) {
        this.choicenesss = choicenesss;
    }

    public static class ChoicenesssBean {
        private String pkId;
        private String goodsName;
        private double marketPrice;
        private double shoppingPrice;
        private String picId;




        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            this.marketPrice = marketPrice;
        }

        public double getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(double shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }
    }
    public static class GoodsInfosBean {
        private String pkId;
        private String goodsName;
        private double marketPrice;
        private double shoppingPrice;
        private String picId;
        private String fixedFreightFlag;
        private String visitTimes;
        private String purchaseTimes;
        public String getFixedFreightFlag() {
            return fixedFreightFlag;
        }

        public void setFixedFreightFlag(String fixedFreightFlag) {
            this.fixedFreightFlag = fixedFreightFlag;
        }

        public String getVisitTimes() {
            return visitTimes;
        }

        public void setVisitTimes(String visitTimes) {
            this.visitTimes = visitTimes;
        }

        public String getPurchaseTimes() {
            return purchaseTimes;
        }

        public void setPurchaseTimes(String purchaseTimes) {
            this.purchaseTimes = purchaseTimes;
        }
        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            this.marketPrice = marketPrice;
        }

        public double getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(double shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }
    }
}
