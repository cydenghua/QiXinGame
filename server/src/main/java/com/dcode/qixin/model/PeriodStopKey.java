package com.dcode.qixin.model;

public class PeriodStopKey {
    private Integer periodId;

    private String pValue;

    private Short isXian;

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue == null ? null : pValue.trim();
    }

    public Short getIsXian() {
        return isXian;
    }

    public void setIsXian(Short isXian) {
        this.isXian = isXian;
    }
}