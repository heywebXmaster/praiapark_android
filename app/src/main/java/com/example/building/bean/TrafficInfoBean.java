package com.example.building.bean;

public class TrafficInfoBean {

    private String from;
    private String image;
    private int order;
    private String title;
    private String to;
    private String updated_at;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "TrafficInfoBean{" +
                "from='" + from + '\'' +
                ", image='" + image + '\'' +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", to='" + to + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
