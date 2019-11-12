package com.example.building.bean;

public class UnitInfoBean {
    private int order;
    private String pdf;
    private String title;
    private String updated_at;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "UnitInfoBean{" +
                "order=" + order +
                ", pdf='" + pdf + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
