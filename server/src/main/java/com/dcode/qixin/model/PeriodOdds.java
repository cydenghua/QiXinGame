package com.dcode.qixin.model;

import java.math.BigDecimal;
import java.util.Date;

public class PeriodOdds extends PeriodOddsKey {
    private BigDecimal oddsNew;

    private Integer happenSum;

    private String comment;

    private Integer oId;

    private Short eneabled;

    private Date createdTime;

    private Date modifyTime;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}