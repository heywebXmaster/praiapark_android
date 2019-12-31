package com.savills.praiapark.bean;

public class DevicesBean {
    private int fromTime;
    private int hour;
    private String id;
    private String name;
    private int order;
    private PriceInfo price;
    private int toTime;
    private int type;
    private String workday;

    public class PriceInfo {
        private String price;
        private String type;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "PriceInfo{" +
                    "price='" + price + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public int getFromTime() {
        return fromTime;
    }

    public void setFromTime(int fromTime) {
        this.fromTime = fromTime;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PriceInfo getPrice() {
        return price;
    }

    public void setPrice(PriceInfo price) {
        this.price = price;
    }

    public int getToTime() {
        return toTime;
    }

    public void setToTime(int toTime) {
        this.toTime = toTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    @Override
    public String toString() {
        return "DevicesBean{" +
                "fromTime=" + fromTime +
                ", hour=" + hour +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", price=" + price +
                ", toTime=" + toTime +
                ", type=" + type +
                ", workday='" + workday + '\'' +
                '}';
    }
}
