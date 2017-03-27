package com.mhl.shop.search.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-1-11 17:34
 * 描述：
 */
public class SupplierMain {
    /**
     * pkId : 794820743232884736
     * supplierName : 北京供应商中心
     * identificationId : 148edffc672846028ce97ca65fb7ff56
     * goodsQuantity : 12
     * goodsList : [{"pkId":"809233819642236928","shoppingPrice":65,"picId":"66b0d7dd4a4f496c88afc1325018cc10"},{"pkId":"809230949073162240","shoppingPrice":65,"picId":"66b0d7dd4a4f496c88afc1325018cc10"},{"pkId":"809229419112697856","shoppingPrice":6551,"picId":"66b0d7dd4a4f496c88afc1325018cc10"}]
     */

    private String pkId;
    private String supplierName;
    private String identificationId;
    private int goodsQuantity;
    /**
     * pkId : 809233819642236928
     * shoppingPrice : 65.0
     * picId : 66b0d7dd4a4f496c88afc1325018cc10
     */

    private List<GoodsListBean> goodsList;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        private String pkId;
        private double shoppingPrice;
        private String picId;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
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
