package com.dcode.wanjin.entity;

import com.dcode.wanjin.util.StrUtil;

import java.util.Date;

public class PeriodCount {

    private Integer periodId;
    private Integer hyId;
    private Integer gdId;
    private Integer zdId;
    private Integer dlId;
    private Integer playMoney;
    private Integer rakeGd;
    private Integer rakeZd;
    private Integer rakeDl;
    private Integer rakeHy;
    private Integer payKf;
    private Integer status;
    private String createdTime;
    private String modifyTime;

    private String pId;
    private String pTime;
    private String gdName;
    private String gdNickname;
    private String zdName;
    private String zdNickname;
    private String dlName;
    private String dlNickname;
    private String hyName;
    private String hyNickname;

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getHyId() {
        return hyId;
    }

    public void setHyId(Integer hyId) {
        this.hyId = hyId;
    }

    public Integer getGdId() {
        return gdId;
    }

    public void setGdId(Integer gdId) {
        this.gdId = gdId;
    }

    public Integer getZdId() {
        return zdId;
    }

    public void setZdId(Integer zdId) {
        this.zdId = zdId;
    }

    public Integer getDlId() {
        return dlId;
    }

    public void setDlId(Integer dlId) {
        this.dlId = dlId;
    }

    public Integer getPlayMoney() {
        return playMoney;
    }

    public void setPlayMoney(Integer playMoney) {
        this.playMoney = playMoney;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTime() {
        return pTime;
    }

    public String getpTimeShort() {
        return pTime.substring(5, 10);
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getGdName() {
        return gdName;
    }

    public void setGdName(String gdName) {
        this.gdName = gdName;
    }

    public String getGdNickname() {
        return gdNickname;
    }

    public void setGdNickname(String gdNickname) {
        this.gdNickname = gdNickname;
    }

    public String getZdName() {
        return zdName;
    }

    public void setZdName(String zdName) {
        this.zdName = zdName;
    }

    public String getZdNickname() {
        return zdNickname;
    }

    public void setZdNickname(String zdNickname) {
        this.zdNickname = zdNickname;
    }

    public String getDlName() {
        return dlName;
    }

    public void setDlName(String dlName) {
        this.dlName = dlName;
    }

    public String getDlNickname() {
        return dlNickname;
    }

    public void setDlNickname(String dlNickname) {
        this.dlNickname = dlNickname;
    }

    public String getHyName() {
        return hyName;
    }

    public void setHyName(String hyName) {
        this.hyName = hyName;
    }

    public String getHyNickname() {
        return hyNickname;
    }

    public void setHyNickname(String hyNickname) {
        this.hyNickname = hyNickname;
    }

    public String getStrPlayMoney() {
        return StrUtil.intFenToYuan(playMoney);
    }

    public String getStrPayKfMoney() {
        return StrUtil.intFenToYuan(payKf);
    }

    public String getStrRakeGd() {
        return StrUtil.intLiToYuan(rakeGd);
    }
    public String getStrRakeZd() {
        return StrUtil.intLiToYuan(rakeZd);
    }
    public String getStrRakeDl() {
        return StrUtil.intLiToYuan(rakeDl);
    }
    public String getStrRakeHy() {
        return StrUtil.intLiToYuan(rakeHy);
    }

}