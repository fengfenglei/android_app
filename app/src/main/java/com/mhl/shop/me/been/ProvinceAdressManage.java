package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-24 14:30
 * 描述：
 */
public class ProvinceAdressManage {


    /**
     * areaLevel : 0
     * areaName : string
     * code : string
     * createTime : 2016-11-24T06:24:03.841Z
     * createUser : 0
     * delMark : 0
     * parentId : 0
     * pkId : 0
     * updateTime : 2016-11-24T06:24:03.841Z
     * updateUser : 0
     */

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int areaLevel;
        private String areaName;
        private String code;
        private String createTime;
        private int createUser;
        private int delMark;
        private int parentId;
        private int pkId;
        private String updateTime;
        private int updateUser;

        public int getAreaLevel() {
            return areaLevel;
        }

        public void setAreaLevel(int areaLevel) {
            this.areaLevel = areaLevel;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getDelMark() {
            return delMark;
        }

        public void setDelMark(int delMark) {
            this.delMark = delMark;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getPkId() {
            return pkId;
        }

        public void setPkId(int pkId) {
            this.pkId = pkId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(int updateUser) {
            this.updateUser = updateUser;
        }
    }
}