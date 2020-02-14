package com.savills.praiapark.bean;

import java.io.Serializable;
import java.util.List;

public class DevicesBean implements Serializable {
    private int fromTime;
    private int hour;
    private String id;
    private String name;
    private int order;
    private Prices price;
    private int toTime;
    private int type;
    private String workday;
    private String address;
    private String facilityId;
    private String facilityName;
    private int facilityType;
    private String householderCode;
    private String username;
    private String amount;
    private String key;
    private int iconId;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public int getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(int facilityType) {
        this.facilityType = facilityType;
    }

    public String getHouseholderCode() {
        return householderCode;
    }

    public void setHouseholderCode(String householderCode) {
        this.householderCode = householderCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public class Prices implements Serializable {
        private List<PriceInfo> prices;
        private String type;


        public class PriceInfo implements Serializable {
            private String fromTime;
            private String price;
            private String toTime;

            public String getFromTime() {
                return fromTime;
            }

            public void setFromTime(String fromTime) {
                this.fromTime = fromTime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getToTime() {
                return toTime;
            }

            public void setToTime(String toTime) {
                this.toTime = toTime;
            }
        }

        public List<PriceInfo> getPrices() {
            return prices;
        }

        public void setPrices(List<PriceInfo> prices) {
            this.prices = prices;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

    public Prices getPrice() {
        return price;
    }

    public void setPrice(Prices price) {
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
