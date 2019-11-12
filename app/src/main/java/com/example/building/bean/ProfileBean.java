package com.example.building.bean;

public class ProfileBean {
    private String avatar;
    private String address;
    private boolean enable;
    private String nickname;
    private String username;
    private String householderCode;
    private String phoneNumber;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHouseholderCode() {
        return householderCode;
    }

    public void setHouseholderCode(String householderCode) {
        this.householderCode = householderCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ProfileBean{" +
                "avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", enable=" + enable +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", householderCode='" + householderCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
