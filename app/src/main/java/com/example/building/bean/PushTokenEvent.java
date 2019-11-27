package com.example.building.bean;

public class PushTokenEvent {
    private String regId;

    public PushTokenEvent(String regId) {
        this.regId = regId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
