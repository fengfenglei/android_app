package com.mhl.shop.shopdetails.been;

import java.util.Set;

/**
 * Created by kapokcloud on 2016/12/8.
 */

public class WantInfo {

    private String attrName;
    private Set<String> attrList;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Set<String> getAttrList() {
        return attrList;
    }

    public void setAttrList(Set<String> attrList) {
        this.attrList = attrList;
    }

    @Override
    public String toString() {
        return "WantInfo{" +
                "attrName='" + attrName + '\'' +
                ", attrList=" + attrList +
                '}';
    }
}
