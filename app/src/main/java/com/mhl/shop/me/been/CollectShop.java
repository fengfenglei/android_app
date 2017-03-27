package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-5 14:14
 * 描述：
 */
public class CollectShop {


    /**
     * flag : 1
     * collectionGoods : [{"pkId":"1","goodsId":"798002192081620992","goodsName":"测试商品1","goodsPrice":1,"currentPrice":888,"goodsPhotoId":"0"},{"pkId":"2","goodsId":"1","goodsName":"第二個收藏","goodsPrice":2,"goodsPhotoId":"0"},{"pkId":"3","goodsId":"798004362868822016","goodsName":"测试商品3","goodsPrice":3,"currentPrice":40,"goodsPhotoId":"0"},{"pkId":"4","goodsId":"1","goodsName":"第四個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"5","goodsId":"1","goodsName":"第五個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"6","goodsId":"1","goodsName":"第六個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"7","goodsId":"1","goodsName":"第七個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"8","goodsId":"1","goodsName":"第八個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"9","goodsId":"1","goodsName":"第九個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"10","goodsId":"1","goodsName":"第十個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"11","goodsId":"1","goodsName":"第十一個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"12","goodsId":"1","goodsName":"第十二個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"13","goodsId":"1","goodsName":"第十三個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"14","goodsId":"1","goodsName":"第十四個收藏","goodsPrice":1,"goodsPhotoId":"0"},{"pkId":"15","goodsId":"1","goodsName":"第十五個收藏","goodsPrice":1,"goodsPhotoId":"0"}]
     */

    private int flag;
    /**
     * pkId : 1
     * goodsId : 798002192081620992
     * goodsName : 测试商品1
     * goodsPrice : 1.0
     * currentPrice : 888.0
     * goodsPhotoId : 0
     */

    private List<CollectionGoodsBean> collectionGoods;
    private List<ChoicenessGoods> choicenessGoods;

    public List<ChoicenessGoods> getChoicenessGoods() {
        return choicenessGoods;
    }

    public void setChoicenessGoods(List<ChoicenessGoods> choicenessGoods) {
        this.choicenessGoods = choicenessGoods;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<CollectionGoodsBean> getCollectionGoods() {
        return collectionGoods;
    }

    public void setCollectionGoods(List<CollectionGoodsBean> collectionGoods) {
        this.collectionGoods = collectionGoods;
    }
    public static class ChoicenessGoods {
        private String goodsId;
        private String picId;
        private String goodsName;
        private String goodsTitle;
        private double marketPrice;
        private double shoppingPrice;
        private int sorting;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
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

        public int getSorting() {
            return sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }
    public static class CollectionGoodsBean {
        private String pkId;
        private String goodsId;
        private String goodsName;
        private double goodsPrice;
        private double currentPrice;
        private String goodsPhotoId;

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

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getGoodsPhotoId() {
            return goodsPhotoId;
        }

        public void setGoodsPhotoId(String goodsPhotoId) {
            this.goodsPhotoId = goodsPhotoId;
        }
    }
}
