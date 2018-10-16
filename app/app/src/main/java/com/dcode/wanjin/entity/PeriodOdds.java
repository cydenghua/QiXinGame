package com.dcode.wanjin.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PeriodOdds extends PeriodOddsKey {
    private BigDecimal oddsNew;

    private Integer happenSum;

    private String comment;

    private Integer oId;

    private Short eneabled;

    private String createdTime;

    private String modifyTime;

    public BigDecimal getOddsNew() {
        return oddsNew;
    }

    public void setOddsNew(BigDecimal oddsNew) {
        this.oddsNew = oddsNew;
    }

    public Integer getHappenSum() {
        return happenSum;
    }

    public void setHappenSum(Integer happenSum) {
        this.happenSum = happenSum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Short getEneabled() {
        return eneabled;
    }

    public void setEneabled(Short eneabled) {
        this.eneabled = eneabled;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}