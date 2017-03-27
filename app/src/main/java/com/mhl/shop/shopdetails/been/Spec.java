package com.mhl.shop.shopdetails.been;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-12-9 16:58
 * 描述：
 */
public class Spec {
    private String name;
    private List<NewText> value;

    public List<NewText> getValue() {
        return value;
    }

    public void setValue(List<NewText> value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
