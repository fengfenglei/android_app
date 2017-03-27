package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-5 15:06
 * 描述：
 */
public class CollectSupplier {
    /**
     * flag : 1
     * collectionSuppliers : [{"pkId":"1","supplierIdName":"随州卖货郎","collectionTime":1456934400000,"supplierPicId":"85c781c67c9841c6bee007a221d7855c"},{"pkId":"2","supplierIdName":"北京供应商中心","collectionTime":1456934400000,"supplierPicId":"85c781c67c9841c6bee007a221d7855c"}]
     */

    private int flag;
    /**
     * pkId : 1
     * supplierIdName : 随州卖货郎
     * collectionTime : 1456934400000
     * supplierPicId : 85c781c67c9841c6bee007a221d7855c
     */

    private List<CollectionSuppliersBean> collectionSuppliers;
    private List<RecommendSuppliers> recommendSuppliers;

    public List<RecommendSuppliers> getRecommendSuppliers() {
        return recommendSuppliers;
    }

    public void setRecommendSuppliers(List<RecommendSuppliers> recommendSuppliers) {
        this.recommendSuppliers = recommendSuppliers;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<CollectionSuppliersBean> getCollectionSuppliers() {
        return collectionSuppliers;
    }

    public void setCollectionSuppliers(List<CollectionSuppliersBean> collectionSuppliers) {
        this.collectionSuppliers = collectionSuppliers;
    }
    public static class RecommendSuppliers {
        private String pkId;
        private String supplierName;
        private String province;
        private String city;
        private String district;
        private String street;
        private String village;
        private String supplierAddress;

        public String getIdentificationId() {
            return identificationId;
        }

        public void setIdentificationId(String identificationId) {
            this.identificationId = identificationId;
        }

        private String identificationId;
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

        public String getSupplierAddress() {
            return supplierAddress;
        }

        public void setSupplierAddress(String supplierAddress) {
            this.supplierAddress = supplierAddress;
        }
    }
    public static class CollectionSuppliersBean {
        private String pkId;
        private String supplierIdName;
        private long collectionTime;
        private String supplierPicId;

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        private String supplierId;
        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
        }

        public String getSupplierIdName() {
            return supplierIdName;
        }

        public void setSupplierIdName(String supplierIdName) {
            this.supplierIdName = supplierIdName;
        }

        public long getCollectionTime() {
            return collectionTime;
        }

        public void setCollectionTime(long collectionTime) {
            this.collectionTime = collectionTime;
        }

        public String getSupplierPicId() {
            return supplierPicId;
        }

        public void setSupplierPicId(String supplierPicId) {
            this.supplierPicId = supplierPicId;
        }
    }
}
