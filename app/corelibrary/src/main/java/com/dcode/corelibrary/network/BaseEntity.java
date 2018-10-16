package com.dcode.corelibrary.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiaoming on 2017/8/7.
 */

public class BaseEntity implements Parcelable {

    private String message;
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code != null && code == 200 ? true : false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeValue(this.code);
    }

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
        this.message = in.readString();
        this.code = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel source) {
            return new BaseEntity(source);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };
}
