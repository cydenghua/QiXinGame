package com.dcode.qixin.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class QxPeriod implements Serializable {
    private Integer sysId;

    private Integer pId;

    private Date pTime;

    private Short p1;

    private Short p2;

    private Short p3;

    private Short p4;

    private Short p5;

    private Short p6;

    private Short p7;

    private Short status;

    private Date createdTime;

    private Date modifyTime;

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Date getpTime() {
        return pTime;
    }

    public void setpTime(Date pTime) {
        this.pTime = pTime;
    }

    public Short getP1() {
        return p1;
    }

    public void setP1(Short p1) {
        this.p1 = p1;
    }

    public Short getP2() {
        return p2;
    }

    public void setP2(Short p2) {
        this.p2 = p2;
    }

    public Short getP3() {
        return p3;
    }

    public void setP3(Short p3) {
        this.p3 = p3;
    }

    public Short getP4() {
        return p4;
    }

    public void setP4(Short p4) {
        this.p4 = p4;
    }

    public Short getP5() {
        return p5;
    }

    public void setP5(Short p5) {
        this.p5 = p5;
    }

    public Short getP6() {
        return p6;
    }

    public void setP6(Short p6) {
        this.p6 = p6;
    }

    public Short getP7() {
        return p7;
    }

    public void setP7(Short p7) {
        this.p7 = p7;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public Date getStopTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pTime);
        calendar.set(Calendar.HOUR, 20);
        calendar.set(Calendar.MINUTE, 22);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}