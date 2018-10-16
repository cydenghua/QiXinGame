package com.dcode.wanjin.entity;

import com.dcode.wanjin.util.QXConstant;

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

    public String getPlayClassName() {
        if(getPlayClass().equals(QXConstant.CLASS_2P)) {
            return "二字定";
        }
        if(getPlayClass().equals(QXConstant.CLASS_3P)) {
            return "三字定";
        }
        if(getPlayClass().equals(QXConstant.CLASS_4P)) {
            return "四字定";
        }
        if(getPlayClass().equals(QXConstant.CLASS_2PX)) {
            return "二字现";
        }
        if(getPlayClass().equals(QXConstant.CLASS_3PX)) {
            return "三字现";
        }
        if(getPlayClass().equals(QXConstant.CLASS_4PX)) {
            return "四字现";
        }

        return  "";
    }
}