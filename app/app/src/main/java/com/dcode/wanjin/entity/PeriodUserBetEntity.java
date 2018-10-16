package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.util.List;

public class PeriodUserBetEntity extends BaseEntity {

    private PageInfo<PeriodBet> data;

    public PageInfo<PeriodBet> getData() {
        return data;
    }

    public void setData(PageInfo<PeriodBet> data) {
        this.data = data;
    }

    //    private List<PeriodBet> data;
//
//    public List<PeriodBet> getData() {
//        return data;
//    }
//
//    public void setData(List<PeriodBet> data) {
//        this.data = data;
//    }

}
