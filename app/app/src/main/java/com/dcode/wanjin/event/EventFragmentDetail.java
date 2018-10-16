package com.dcode.wanjin.event;

import com.dcode.corelibrary.event.BaseEventbus;

public class EventFragmentDetail extends BaseEventbus {

    public static final String KEY_REFRESH = "REFRESH";//刷新页面

    public EventFragmentDetail(String keyEvent) {
        super(keyEvent);
    }

}
