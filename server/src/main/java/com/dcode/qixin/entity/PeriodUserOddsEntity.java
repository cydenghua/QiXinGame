package com.dcode.qixin.entity;

import com.dcode.qixin.model.BetTypeSum;
import com.dcode.qixin.model.BetUserSum;

import java.math.BigDecimal;

public class PeriodUserOddsEntity {

    private BigDecimal odds;
    private int minPlay;
    private int maxPlay;
    private int maxClassPlay;
    private int creditLeft;
    private int typeLeft;
    private BetUserSum betUserSum;
    private BetTypeSum betTypeSum;

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public int getMinPlay() {
        return minPlay;
    }

    public void setMinPlay(int minPlay) {
        this.minPlay = minPlay;
    }

    public int getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(int maxPlay) {
        this.maxPlay = maxPlay;
    }

    public int getMaxClassPlay() {
        return maxClassPlay;
    }

    public void setMaxClassPlay(int maxClassPlay) {
        this.maxClassPlay = maxClassPlay;
    }

    public int getCreditLeft() {
        return creditLeft;
    }

    public void setCreditLeft(int creditLeft) {
        this.creditLeft = creditLeft;
    }

    public int getTypeLeft() {
        return typeLeft;
    }

    public void setTypeLeft(int classLeft) {
        this.typeLeft = classLeft;
    }

    public BetUserSum getBetUserSum() {
        return betUserSum;
    }

    public void setBetUserSum(BetUserSum betUserSum) {
        this.betUserSum = betUserSum;
    }

    public BetTypeSum getBetTypeSum() {
        return betTypeSum;
    }

    public void setBetTypeSum(BetTypeSum betTypeSum) {
        this.betTypeSum = betTypeSum;
    }
}
