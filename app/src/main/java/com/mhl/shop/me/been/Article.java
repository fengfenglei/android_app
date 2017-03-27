package com.mhl.shop.me.been;

import java.util.List;

/**
 * 作者：LFF
 * 时间；2017-1-18 18:37
 * 描述：文章
 */
public class Article {


    /**
     * flag : 1
     * discoverList : [{"pkId":"818403770735136767","author":"1","avator":"fd6e9fb9bbbd4804b382f3e89fea44b7","discTags":"1","discCovers":"be063e35c97b40598886b13d44d1ec28,60986d50c98f409b993ac4c3d5668fff,87fed5c7da164c7897eef639938abec4","discSummary":"1","editTime":1483930165000,"favored":2},{"pkId":"818403770735136768","author":"eqweqweqwe","avator":"486472e2d787407c94e8c1c5350f03aa","discTags":"werwer","discCovers":"845fba56978f47dab39151ed80f456d2,3492ad2904da438a884adc9f1816598c","discSummary":"werwer","editTime":1483957445000,"favored":2},{"pkId":"818723501497257984","author":"网友","avator":"8dfd3245924c49ea97a74a079e726a42","discTags":"泡泡堂网友","discCovers":"eab145c13d64405a949e48ed1e010fdc,80d1f54e38974d138662b606109d3b07,9679cf10af5a4fa8b4ba6e76a94699e7,e6e4c578b70e4bf6b2a8f3d4f704ae00,b2e21bee502f44a3850983103c97d946","discSummary":"武大校花","editTime":1484033565000,"favored":2},{"pkId":"818724225094389760","author":"老司机","avator":"fb47f341c4a64f29a487d74e114ab149","discTags":"别说话快上车","discCovers":"186580bb112e4de3b7585dfa548527f7,0a7367fa4a564e99b74d97236e328418,dcb7c8ec90ee4293b99fbace68c77ef6","discSummary":"","editTime":1484033738000,"favored":2}]
     */

    private int flag;
    /**
     * pkId : 818403770735136767
     * author : 1
     * avator : fd6e9fb9bbbd4804b382f3e89fea44b7
     * discTags : 1
     * discCovers : be063e35c97b40598886b13d44d1ec28,60986d50c98f409b993ac4c3d5668fff,87fed5c7da164c7897eef639938abec4
     * discSummary : 1
     * editTime : 1483930165000
     * favored : 2
     */

    private List<DiscoverListBean> discoverList;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<DiscoverListBean> getDiscoverList() {
        return discoverList;
    }

    public void setDiscoverList(List<DiscoverListBean> discoverList) {
        this.discoverList = discoverList;
    }

    public static class DiscoverListBean {
        private String pkId;
        private String author;
        private String avator;
        private String discTags;
        private String discCovers;
        private String discSummary;

        public String getDiscTitle() {
            return discTitle;
        }

        public void setDiscTitle(String discTitle) {
            this.discTitle = discTitle;
        }

        private String discTitle;
        private long editTime;
        private int favored;

        public String getPkId() {
            return pkId;
        }

        public void setPkId(String pkId) {
            this.pkId = pkId;
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

        public String getDiscSummary() {
            return discSummary;
        }

        public void setDiscSummary(String discSummary) {
            this.discSummary = discSummary;
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
    }
}
