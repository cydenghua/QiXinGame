package com.dcode.wanjin.entity;

import com.dcode.wanjin.util.StrUtil;

import java.util.List;

public class PeriodCountGroup {


    private PeriodCount periodCountGroup;

    private List<PeriodCount> periodCountDetail;

    public PeriodCount getPeriodCountGroup() {
        return periodCountGroup;
    }

    public void setPeriodCountGroup(PeriodCount periodCountGroup) {
        this.periodCountGroup = periodCountGroup;
    }

    public List<PeriodCount> getPeriodCountDetail() {
        return periodCountDetail;
    }

    public void setPeriodCountDetail(List<PeriodCount> periodCountDetail) {
        this.periodCountDetail = periodCountDetail;
    }
}