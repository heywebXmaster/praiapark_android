package com.savills.praiapark.bean;

import java.util.List;

public class AnnouncementBean {

    private List<AnnouncementItemBean> list;
    private int total;

    public List<AnnouncementItemBean> getList() {
        return list;
    }

    public void setList(List<AnnouncementItemBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public class AnnouncementItemBean {
        private String id;
        private int isPdf;
        private String pdf;
        private String title;
        private String content;
        private String thumbnail;
        private String publishDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsPdf() {
            return isPdf;
        }

        public void setIsPdf(int isPdf) {
            this.isPdf = isPdf;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        @Override
        public String toString() {
            return "AnnouncementItemBean{" +
                    "id='" + id + '\'' +
                    ", isPdf=" + isPdf +
                    ", pdf='" + pdf + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", publishDate='" + publishDate + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
