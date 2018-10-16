package com.dcode.wanjin.entity;

import com.dcode.corelibrary.network.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

public class AddUserPeriodBetEntity extends BaseEntity {

    List<PeriodBet> data;

    public List<PeriodBet> getData() {
        return data;
    }

    public void setData(List<PeriodBet> data) {
        this.data = data;
    }
}
