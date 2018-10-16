package com.dcode.wanjin.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.dcode.wanjin.util.QXConstant;

public class User implements Parcelable {

    private Integer sysId;

    private String name;

    private String nickname;

    private String password;

    private Short pwErrCount;

    private Integer roleId;

    private String token;

    private Long tokenExpire;

    private Integer level;

    private Integer createUser;

    private Integer creditLimit;

    private Integer creditLimitLeft;

    private Short deleted;

    private Short eneabled;


    protected User(Parcel in) {
        if (in.readByte() == 0) {
            sysId = null;
        } else {
            sysId = in.readInt();
        }
        name = in.readString();
        nickname = in.readString();
        password = in.readString();
        int tmpPwErrCount = in.readInt();
        pwErrCount = tmpPwErrCount != Integer.MAX_VALUE ? (short) tmpPwErrCount : null;
        if (in.readByte() == 0) {
            roleId = null;
        } else {
            roleId = in.readInt();
        }
        token = in.readString();
        if (in.readByte() == 0) {
            tokenExpire = null;
        } else {
            tokenExpire = in.readLong();
        }
        int tmpLevel = in.readInt();
        level = tmpLevel != Integer.MAX_VALUE ? tmpLevel : null;
        if (in.readByte() == 0) {
            createUser = null;
        } else {
            createUser = in.readInt();
        }
        if (in.readByte() == 0) {
            creditLimit = null;
        } else {
            creditLimit = in.readInt();
        }
        if (in.readByte() == 0) {
            creditLimitLeft = null;
        } else {
            creditLimitLeft = in.readInt();
        }
        int tmpDeleted = in.readInt();
        deleted = tmpDeleted != Integer.MAX_VALUE ? (short) tmpDeleted : null;
        int tmpEneabled = in.readInt();
        eneabled = tmpEneabled != Integer.MAX_VALUE ? (short) tmpEneabled : null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sysId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sysId);
        }
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeString(password);
        dest.writeInt(pwErrCount != null ? (int) pwErrCount : Integer.MAX_VALUE);
        if (roleId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(roleId);
        }
        dest.writeString(token);
        if (tokenExpire == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(tokenExpire);
        }
        dest.writeInt(level != null ? (int) level : Integer.MAX_VALUE);
        if (createUser == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createUser);
        }
        if (creditLimit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditLimit);
        }
        if (creditLimitLeft == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditLimitLeft);
        }
        dest.writeInt(deleted != null ? (int) deleted : Integer.MAX_VALUE);
        dest.writeInt(eneabled != null ? (int) eneabled : Integer.MAX_VALUE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getPwErrCount() {
        return pwErrCount;
    }

    public void setPwErrCount(Short pwErrCount) {
        this.pwErrCount = pwErrCount;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Long tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getCreditLimitLeft() {
        return creditLimitLeft;
    }

    public void setCreditLimitLeft(Integer creditLimitLeft) {
        this.creditLimitLeft = creditLimitLeft;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Short getEneabled() {
        return eneabled;
    }

    public void setEneabled(Short eneabled) {
        this.eneabled = eneabled;
    }

    public String getUserTitle() {
        if(QXConstant.LEVEL_ADMIN == level) { return "超级"; }
        if(QXConstant.LEVEL_GUDONG == level) { return "股东"; }
        if(QXConstant.LEVEL_ZHONGDAI == level) { return "总代"; }
        if(QXConstant.LEVEL_DAILI == level) { return "代理"; }
        if(QXConstant.LEVEL_HUIYUAN == level) { return "会员"; }
        return "";
    }
}