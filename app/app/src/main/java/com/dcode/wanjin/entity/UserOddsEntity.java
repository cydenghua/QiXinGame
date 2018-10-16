package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class UserOddsEntity extends BaseEntity {

    private List<UserOdds> data;

    public List<UserOdds> getData() {
        return data;
    }

    public void setData(List<UserOdds> data) {
        this.data = data;
    }

}
