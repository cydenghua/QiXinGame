package com.dcode.qixin.entity;

import com.dcode.qixin.model.QxPeriod;
import com.dcode.qixin.model.User;

public class LoginSuccessEntity {

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
