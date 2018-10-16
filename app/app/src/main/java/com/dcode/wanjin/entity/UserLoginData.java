package com.dcode.wanjin.entity;


public class UserLoginData {

    private QxPeriod lastPeriod;
    private User userInfo;

    public QxPeriod getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(QxPeriod lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
