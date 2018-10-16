package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodEntity extends BaseEntity {

    private List<QxPeriod> data;

    public List<QxPeriod> getData() {
        return data;
    }

    public void setData(List<QxPeriod> data) {
        this.data = data;
    }

}
