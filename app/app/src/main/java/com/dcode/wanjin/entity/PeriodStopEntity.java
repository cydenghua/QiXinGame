package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodStopEntity extends BaseEntity {

    private List<PeriodStop> data;

    public List<PeriodStop> getData() {
        return data;
    }

    public void setData(List<PeriodStop> data) {
        this.data = data;
    }
}