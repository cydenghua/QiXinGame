package com.dcode.wanjin.event;

import com.dcode.corelibrary.event.BaseEventbus;
import com.dcode.wanjin.entity.PeriodUserOdds;

public class EventBetQuick extends BaseEventbus {


    public static final String KEY_ADD = "ADD";//ADD
    public static final String KEY_BACK_NUMBER = "BACK_NUMBER";//在明细里面退码

    private String playValue;
    private String playType;
    private String playMoney;
    private PeriodUserOdds periodUserOdds;

    private String mBackNum;

    public EventBetQuick(String keyEvent) {
        super(keyEvent);
    }

    public EventBetQuick(String keyEvent, String backNum) {
        super(keyEvent);
        mBackNum = backNum;
    }

    public EventBetQuick(String keyEvent, String playValue, String playType, String playMoney, PeriodUserOdds periodUserOdds) {
        super(keyEvent);
        this.playValue = playValue;
        this.playType = playType;
        this.playMoney = playMoney;
        this.periodUserOdds = periodUserOdds;
    }

    public String getBackNum() {
        return mBackNum;
    }

    public void setBackNum(String mBackNum) {
        this.mBackNum = mBackNum;
    }

    public String getPlayValue() {
        return playValue;
    }

    public void setPlayValue(String playValue) {
        this.playValue = playValue;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getPlayMoney() {
        return playMoney;
    }

    public void setPlayMoney(String playMoney) {
        this.playMoney = playMoney;
    }

    public PeriodUserOdds getPeriodUserOdds() {
        return periodUserOdds;
    }

    public void setPeriodUserOdds(PeriodUserOdds periodUserOdds) {
        this.periodUserOdds = periodUserOdds;
    }

}
