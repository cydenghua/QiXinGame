package com.dcode.qixin.model;

public class PeriodOddsKey {
    private Integer periodId;

    private String playClass;

    private String pValue;

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public String getPlayClass() {
        return playClass;
    }

    public void setPlayClass(String playType) {
        this.playClass = playType == null ? null : playType.trim();
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue == null ? null : pValue.trim();
    }
}