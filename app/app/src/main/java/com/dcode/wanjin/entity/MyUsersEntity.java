package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class MyUsersEntity extends BaseEntity {


    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    private List<User> data;
}
