package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodCountGroupEntity extends BaseEntity {

    private List<PeriodCountGroup> data;

    public List<PeriodCountGroup> getData() {
        return data;
    }

    public void setData(List<PeriodCountGroup> data) {
        this.data = data;
    }
}