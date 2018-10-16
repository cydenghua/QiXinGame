package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodOddsEntity extends BaseEntity {

    private List<PeriodOdds> data;

    public List<PeriodOdds> getData() {
        return data;
    }

    public void setData(List<PeriodOdds> data) {
        this.data = data;
    }
}
