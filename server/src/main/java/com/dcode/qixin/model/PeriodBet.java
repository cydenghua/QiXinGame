package com.dcode.qixin.model;

import java.math.BigDecimal;
import java.util.Date;

public class PeriodBet extends BaseEntity {
    private Integer sysId;

    private Integer userId;

    private Integer periodId;

    private String playType;

    private String playClass;

    private String playValue;

    private Integer playMoney;

    private BigDecimal playOdds;

    private Short deleted;

    private Integer periodResult;

    private Integer rakeGd;

    private Integer rakeZd;

    private Integer rakeDl;

    private Integer rakeHy;

    private Integer payKf;

    private Date createdTime;

    private Date modifyTime;

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType == null ? null : playType.trim();
    }

    public String getPlayClass() {
        return playClass;
    }

    public void setPlayClass(String playClass) {
        this.playClass = playClass == null ? null : playClass.trim();
    }

    public String getPlayValue() {
        return playValue;
    }

    public void setPlayValue(String playValue) {
        this.playValue = playValue == null ? null : playValue.trim();
    }

    public Integer getPlayMoney() {
        return playMoney;
    }

    public void setPlayMoney(Integer playMoney) {
        this.playMoney = playMoney;
    }

    public BigDecimal getPlayOdds() {
        return playOdds;
    }

    public void setPlayOdds(BigDecimal playOdds) {
        this.playOdds = playOdds;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Integer getPeriodResult() {
        return periodResult;
    }

    public void setPeriodResult(Integer periodResult) {
        this.periodResult = periodResult;
    }

    public Integer getRakeGd() {
        return rakeGd;
    }

    public void setRakeGd(Integer rakeGd) {
        this.rakeGd = rakeGd;
    }

    public Integer getRakeZd() {
        return rakeZd;
    }

    public void setRakeZd(Integer rakeZd) {
        this.rakeZd = rakeZd;
    }

    public Integer getRakeDl() {
        return rakeDl;
    }

    public void setRakeDl(Integer rakeDl) {
        this.rakeDl = rakeDl;
    }

    public Integer getRakeHy() {
        return rakeHy;
    }

    public void setRakeHy(Integer rakeHy) {
        this.rakeHy = rakeHy;
    }

    public Integer getPayKf() {
        return payKf;
    }

    public void setPayKf(Integer payKf) {
        this.payKf = payKf;
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