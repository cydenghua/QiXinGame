package com.dcode.wanjin.event;

import com.dcode.corelibrary.event.BaseEventbus;

public class EventPeriodOdds extends BaseEventbus {

    public static final String KEY_REFRESH = "REFRESH";//刷新页面

    public EventPeriodOdds(String keyEvent) {
        super(keyEvent);
    }

}
