package com.dcode.corelibrary.event;

public class BaseEventbus {

    private String mKeyEvent;

    public BaseEventbus(String keyEvent) {
        this.mKeyEvent = keyEvent;
    }

    public String getKeyEvent() {
        return mKeyEvent;
    }

    public void setKeyEvent(String keyEvent) {
        this.mKeyEvent = keyEvent;
    }

}
