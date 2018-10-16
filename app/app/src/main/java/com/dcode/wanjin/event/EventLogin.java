package com.dcode.wanjin.event;

import com.dcode.corelibrary.event.BaseEventbus;

import org.greenrobot.eventbus.EventBus;

public class EventLogin extends BaseEventbus {

    public static final String KEY_REFRESH = "REFRESH";//刷新页面

    public EventLogin(String keyEvent) {
        super(keyEvent);
    }

}
