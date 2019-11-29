package com.savills.praiapark.bean;

import java.util.List;

public class NoticeBean {
    private List<NoticeItemBean> list;
    private int total;


    public List<NoticeItemBean> getList() {
        return list;
    }

    public void setList(List<NoticeItemBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public class NoticeItemBean {
        private String content;
        private String title;
        private String updated_at;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return "NoticeItemBean{" +
                    "content='" + content + '\'' +
                    ", title='" + title + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
