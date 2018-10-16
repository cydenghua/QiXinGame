package com.dcode.wanjin.entity;

import com.dcode.wanjin.util.StrUtil;

import java.math.BigDecimal;
import java.util.Date;

public class PeriodBet {
    private Integer sysId;

    private Integer userId;

    private Integer periodId;

    private String playType;

    private String playClass;

    private String playValue;

    private Integer playMoney;

    private BigDecimal playOdds;

    private Short deleted;

    private String createdTime;

    private String modifyTime;

    private int tmpDeleted;

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

    public int getTmpDeleted() {
        return tmpDeleted;
    }

    public void setTmpDeleted(int tmpDeleted) {
        this.tmpDeleted = tmpDeleted;
    }

    public boolean isCancelable() {
        return StrUtil.strToDateTime(createdTime).getTime() > System.currentTimeMillis()-10*60*1000;//退码只能十分钟内
    }
}