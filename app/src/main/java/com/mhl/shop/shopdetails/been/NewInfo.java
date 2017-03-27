package com.mhl.shop.shopdetails.been;

/**
 * Created by kapokcloud on 2016/12/8.
 */

public class NewInfo {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        String in = key + value;
        return in.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        NewInfo info = (NewInfo) obj;
        return key.equals(info.key) && value.equals(info.value);
    }

    @Override
    public String toString() {
        return "NewInfo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}

