package com.mhl.shop.homepage.bean;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2017-2-13 20:02
 * 描述：
 */
public class Welcome {
    /**
     * img : 167e8eb91dd74d2dab4efd626fcfc024
     * title : 测试
     * url : 0
     */

    private List<WellomeBean> wellome;

    public List<WellomeBean> getWellome() {
        return wellome;
    }

    public void setWellome(List<WellomeBean> wellome) {
        this.wellome = wellome;
    }

    public static class WellomeBean {
        private String img;
        private String title;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
