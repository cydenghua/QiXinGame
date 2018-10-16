package com.dcode.wanjin.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.dcode.wanjin.util.QXConstant;

import java.util.Date;

public class QxPeriod implements Parcelable{
    private Integer sysId;

    private Integer pId;

    private String pTime;

    private Short p1;

    private Short p2;

    private Short p3;

    private Short p4;

    private Short p5;

    private Short p6;

    private Short p7;

    private Short status;

    private String createdTime;

    private String modifyTime;

    public QxPeriod() {
    }

    protected QxPeriod(Parcel in) {
        if (in.readByte() == 0) {
            sysId = null;
        } else {
            sysId = in.readInt();
        }
        if (in.readByte() == 0) {
            pId = null;
        } else {
            pId = in.readInt();
        }
        pTime = in.readString();
        int tmpP1 = in.readInt();
        p1 = tmpP1 != Integer.MAX_VALUE ? (short) tmpP1 : null;
        int tmpP2 = in.readInt();
        p2 = tmpP2 != Integer.MAX_VALUE ? (short) tmpP2 : null;
        int tmpP3 = in.readInt();
        p3 = tmpP3 != Integer.MAX_VALUE ? (short) tmpP3 : null;
        int tmpP4 = in.readInt();
        p4 = tmpP4 != Integer.MAX_VALUE ? (short) tmpP4 : null;
        int tmpP5 = in.readInt();
        p5 = tmpP5 != Integer.MAX_VALUE ? (short) tmpP5 : null;
        int tmpP6 = in.readInt();
        p6 = tmpP6 != Integer.MAX_VALUE ? (short) tmpP6 : null;
        int tmpP7 = in.readInt();
        p7 = tmpP7 != Integer.MAX_VALUE ? (short) tmpP7 : null;
        int tmpStatus = in.readInt();
        status = tmpStatus != Integer.MAX_VALUE ? (short) tmpStatus : null;
        createdTime = in.readString();
        modifyTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sysId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sysId);
        }
        if (pId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pId);
        }
        dest.writeString(pTime);
        dest.writeInt(p1 != null ? (int) p1 : Integer.MAX_VALUE);
        dest.writeInt(p2 != null ? (int) p2 : Integer.MAX_VALUE);
        dest.writeInt(p3 != null ? (int) p3 : Integer.MAX_VALUE);
        dest.writeInt(p4 != null ? (int) p4 : Integer.MAX_VALUE);
        dest.writeInt(p5 != null ? (int) p5 : Integer.MAX_VALUE);
        dest.writeInt(p6 != null ? (int) p6 : Integer.MAX_VALUE);
        dest.writeInt(p7 != null ? (int) p7 : Integer.MAX_VALUE);
        dest.writeInt(status != null ? (int) status : Integer.MAX_VALUE);
        dest.writeString(createdTime);
        dest.writeString(modifyTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QxPeriod> CREATOR = new Creator<QxPeriod>() {
        @Override
        public QxPeriod createFromParcel(Parcel in) {
            return new QxPeriod(in);
        }

        @Override
        public QxPeriod[] newArray(int size) {
            return new QxPeriod[size];
        }
    };

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

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
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


    public String getStrStatus() {
        if(QXConstant.PERIOD_STATUS_BEGIN == status) { return "准备开始"; }
        if(QXConstant.PERIOD_STATUS_ING == status) { return "正在销售"; }
        if(QXConstant.PERIOD_STATUS_CLOSE == status) { return "已封盘"; }
        if(QXConstant.PERIOD_STATUS_OUT == status) { return "已开奖"; }
        if(QXConstant.PERIOD_STATUS_END == status) { return "已结束"; }
        return "";
    }


}




