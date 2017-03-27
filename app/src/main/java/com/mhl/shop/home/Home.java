package com.mhl.shop.home;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-6 18:04
 * 描述：首页
 */
public class Home implements Serializable {


    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * title : 测试
     * keyType : 1
     * url : 0
     */

    private List<ShortcutBean> shortcut;
    /**
     * pkId : 798001123146469376
     * marketPrice : 100.00
     * goodsTitle : 测试副标题2
     * shoppingPrice : 65.00
     * picId : f12918fd82e8444e95965aec851043f2
     */

    private List<RecommendBean> recommend;
    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * classId : 156
     * title : 手机
     */

    private List<ClassifiedBean> classified;
    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * keyValue : 0
     * title : 测试
     * keyType : 2
     */

    private List<CarouselBean> carousel;
    /**
     * pkId : 798001123146469376
     * marketPrice : 100.00
     * shoppingPrice : 65.00
     * picId : 167e8eb91dd74d2dab4efd626fcfc024
     */

    private List<LangBean> lang;
    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * keyValue : 0
     * title : 测试
     * keyType : 1
     */

    private List<HotBean> hot;
    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * keyValue : 0
     * title : 测试
     * keyType : 1
     */

    private List<BrandBean> brand;

    public List<ShortcutBean> getShortcut() {
        return shortcut;
    }

    public void setShortcut(List<ShortcutBean> shortcut) {
        this.shortcut = shortcut;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public List<ClassifiedBean> getClassified() {
        return classified;
    }

    public void setClassified(List<ClassifiedBean> classified) {
        this.classified = classified;
    }

    public List<CarouselBean> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<CarouselBean> carousel) {
        this.carousel = carousel;
    }

    public List<LangBean> getLang() {
        return lang;
    }

    public void setLang(List<LangBean> lang) {
        this.lang = lang;
    }

    public List<HotBean> getHot() {
        return hot;
    }

    public void setHot(List<HotBean> hot) {
        this.hot = hot;
    }

    public List<BrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandBean> brand) {
        this.brand = brand;
    }

    public static class ShortcutBean {
        private String img;
        private String title;
        private String keyType;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class RecommendBean {
        private String pkId;
        private String marketPrice;
        private String goodsTitle;
        private String shoppingPrice;
        private String picId;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public String getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(String shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }
    }

    public static class ClassifiedBean {
        private String img;
        private String classId;
        private String title;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class CarouselBean {
        private String img;
        private String keyValue;
        private String title;
        private String keyType;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }
    }

    public static class LangBean {
        private String pkId;
        private String marketPrice;
        private String shoppingPrice;
        private String picId;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getShoppingPrice() {
            return shoppingPrice;
        }

        public void setShoppingPrice(String shoppingPrice) {
            this.shoppingPrice = shoppingPrice;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }
    }

    public static class HotBean {
        private String img;
        private String keyValue;
        private String title;
        private String keyType;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }
    }

    public static class BrandBean {
        private String img;
        private String keyValue;
        private String title;
        private String keyType;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }
    }
}
