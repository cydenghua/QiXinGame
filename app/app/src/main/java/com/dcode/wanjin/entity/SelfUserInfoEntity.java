package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

public class SelfUserInfoEntity extends BaseEntity {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
