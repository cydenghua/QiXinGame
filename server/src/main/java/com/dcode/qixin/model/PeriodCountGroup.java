package com.dcode.qixin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeriodCountGroup {

    private PeriodCount periodCountGroup;

    private List<PeriodCount> periodCountDetail = new ArrayList<>();

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