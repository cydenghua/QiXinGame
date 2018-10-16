package com.dcode.wanjin.entity;

public class PeriodStop  {

    private Integer periodId;

    private String pValue;

    private Short isXian;

    private String comment;

    private Short deleted;

    private String createdTime;

    private String modifyTime;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Short getIsXian() {
        return isXian;
    }

    public void setIsXian(Short isXian) {
        this.isXian = isXian;
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

}