package com.dcode.wanjin.event;

import com.dcode.corelibrary.event.BaseEventbus;

public class EventMyUser  extends BaseEventbus {

    public static final String KEY_REFRESH = "REFRESH";//刷新页面

    public EventMyUser(String keyEvent) {
        super(keyEvent);
    }

}
