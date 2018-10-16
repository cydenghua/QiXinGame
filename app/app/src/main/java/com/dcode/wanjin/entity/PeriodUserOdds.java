package com.dcode.wanjin.entity;

import java.math.BigDecimal;

public class PeriodUserOdds {

    private BigDecimal odds;
    private int minPlay;
    private int maxPlay;
    private int maxClassPlay;
    private int creditLeft;
    private int typeLeft;

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

    public void setTypeLeft(int typeLeft) {
        this.typeLeft = typeLeft;
    }
}
