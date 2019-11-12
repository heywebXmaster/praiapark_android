package com.example.building.bean;

public class AroundInfoBean {

    private int order;
    private String phoneNumber;
    private String title;
    private String updated_at;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        return "AroundInfoBean{" +
                "order=" + order +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
