package com.mhl.shop.find;

/**
 * 作者：lff
 * 时间；2017-1-10 18:13
 * 描述：发现详情
 */
public class FindDetail {


    /**
     * pkId : 818724225094389760
     * discTitle : 斗图
     * author : 老司机
     * avator : fb47f341c4a64f29a487d74e114ab149
     * discTags : 别说话快上车
     * discCovers : 186580bb112e4de3b7585dfa548527f7,0a7367fa4a564e99b74d97236e328418,dcb7c8ec90ee4293b99fbace68c77ef6
     * favors : 3
     * editTime : 1484033738000
     * favored : 2
     * collectioned : 2
     */

    private String pkId;
    private String discTitle;
    private String author;
    private String avator;
    private String discTags;
    private String discCovers;
    private int favors;
    private long editTime;
    private int favored;
    private int collectioned;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getDiscTitle() {
        return discTitle;
    }

    public void setDiscTitle(String discTitle) {
        this.discTitle = discTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getDiscTags() {
        return discTags;
    }

    public void setDiscTags(String discTags) {
        this.discTags = discTags;
    }

    public String getDiscCovers() {
        return discCovers;
    }

    public void setDiscCovers(String discCovers) {
        this.discCovers = discCovers;
    }

    public int getFavors() {
        return favors;
    }

    public void setFavors(int favors) {
        this.favors = favors;
    }

    public long getEditTime() {
        return editTime;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public int getFavored() {
        return favored;
    }

    public void setFavored(int favored) {
        this.favored = favored;
    }

    public int getCollectioned() {
        return collectioned;
    }

    public void setCollectioned(int collectioned) {
        this.collectioned = collectioned;
    }
}
