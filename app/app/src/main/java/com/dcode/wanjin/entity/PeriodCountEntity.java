package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodCountEntity extends BaseEntity {

    private List<PeriodCount> data;

    public List<PeriodCount> getData() {
        return data;
    }

    public void setData(List<PeriodCount> data) {
        this.data = data;
    }
}
