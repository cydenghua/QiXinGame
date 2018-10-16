package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

public class UserLoginEntity extends BaseEntity {

    /**
     * code : 200
     * message : 登录成功
     * data : {"token":"10575430d8b74ec89f2d894a8b982b35","userLevel":1}
     */

    private UserLoginData data;

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }


}
