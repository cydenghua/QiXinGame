package com.dcode.wanjin.entity;

import java.math.BigDecimal;

public class Odds extends OddsKey {

    private String playTypeName;

    private String playTypeClass;

    private Integer minPlay;

    private Integer maxPlay;

    private Integer maxClassPlay;

    private BigDecimal maxOdds;

    private BigDecimal curOdds;

    private BigDecimal rake;

    private Integer oId;

    public String getPlayTypeName() {
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName == null ? null : playTypeName.trim();
    }

    public String getPlayTypeClass() {
        return playTypeClass;
    }

    public void setPlayTypeClass(String playTypeClass) {
        this.playTypeClass = playTypeClass == null ? null : playTypeClass.trim();
    }

    public Integer getMinPlay() {
        return minPlay;
    }

    public void setMinPlay(Integer minPlay) {
        this.minPlay = minPlay;
    }

    public Integer getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(Integer maxPlay) {
        this.maxPlay = maxPlay;
    }

    public Integer getMaxClassPlay() {
        return maxClassPlay;
    }

    public void setMaxClassPlay(Integer maxClassPlay) {
        this.maxClassPlay = maxClassPlay;
    }

    public BigDecimal getMaxOdds() {
        return maxOdds;
    }

    public void setMaxOdds(BigDecimal maxOdds) {
        this.maxOdds = maxOdds;
    }

    public BigDecimal getCurOdds() {
        return curOdds;
    }

    public void setCurOdds(BigDecimal curOdds) {
        this.curOdds = curOdds;
    }

    public BigDecimal getRake() {
        return rake;
    }

    public void setRake(BigDecimal rake) {
        this.rake = rake;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }
}