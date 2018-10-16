package com.dcode.wanjin.entity;

import com.dcode.wanjin.util.QXConstant;
import java.math.BigDecimal;

public class UserOdds extends UserOddsKey {
    private String playTypeName;

    private String playTypeClass;

    private Integer minPlay;

    private Integer maxPlay;

    private Integer maxClassPlay;

    private Integer userIdPt;

    private Integer userIdGd;

    private Integer userIdZd;

    private Integer userIdDl;

    private Integer userIdHy;

    private BigDecimal oddsPt;

    private BigDecimal oddsGd;

    private BigDecimal oddsZd;

    private BigDecimal oddsDl;

    private BigDecimal oddsHy;

    private BigDecimal oddsKf;

    private String createdTime;

    private String modifyTime;

    private Integer oId;

    public String getPlayTypeName() {
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName == null ? null : playTypeName.trim();
    }

    public String getPlayTypeClass() {
        return playTypeClass;
    }

    public void setPlayTypeClass(String playTypeClass) {
        this.playTypeClass = playTypeClass == null ? null : playTypeClass.trim();
    }

    public Integer getMinPlay() {
        return minPlay;
    }

    public void setMinPlay(Integer minPlay) {
        this.minPlay = minPlay;
    }

    public Integer getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(Integer maxPlay) {
        this.maxPlay = maxPlay;
    }

    public Integer getMaxClassPlay() {
        return maxClassPlay;
    }

    public void setMaxClassPlay(Integer maxClassPlay) {
        this.maxClassPlay = maxClassPlay;
    }

    public Integer getUserIdPt() {
        return userIdPt;
    }

    public void setUserIdPt(Integer userIdPt) {
        this.userIdPt = userIdPt;
    }

    public Integer getUserIdGd() {
        return userIdGd;
    }

    public void setUserIdGd(Integer userIdGd) {
        this.userIdGd = userIdGd;
    }

    public Integer getUserIdZd() {
        return userIdZd;
    }

    public void setUserIdZd(Integer userIdZd) {
        this.userIdZd = userIdZd;
    }

    public Integer getUserIdDl() {
        return userIdDl;
    }

    public void setUserIdDl(Integer userIdDl) {
        this.userIdDl = userIdDl;
    }

    public Integer getUserIdHy() {
        return userIdHy;
    }

    public void setUserIdHy(Integer userIdHy) {
        this.userIdHy = userIdHy;
    }

    public BigDecimal getOddsPt() {
        return oddsPt;
    }

    public void setOddsPt(BigDecimal oddsPt) {
        this.oddsPt = oddsPt;
    }

    public BigDecimal getOddsGd() {
        return oddsGd;
    }

    public void setOddsGd(BigDecimal oddsGd) {
        this.oddsGd = oddsGd;
    }

    public BigDecimal getOddsZd() {
        return oddsZd;
    }

    public void setOddsZd(BigDecimal oddsZd) {
        this.oddsZd = oddsZd;
    }

    public BigDecimal getOddsDl() {
        return oddsDl;
    }

    public void setOddsDl(BigDecimal oddsDl) {
        this.oddsDl = oddsDl;
    }

    public BigDecimal getOddsHy() {
        return oddsHy;
    }

    public void setOddsHy(BigDecimal oddsHy) {
        this.oddsHy = oddsHy;
    }

    public BigDecimal getOddsKf() {
        return oddsKf;
    }

    public void setOddsKf(BigDecimal oddsKf) {
        this.oddsKf = oddsKf;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    //获取最大赔率， 既上级的赔率
    public BigDecimal getMaxOdds(Integer userLevel) {
        if(userLevel == QXConstant.LEVEL_ADMIN) {
            return getOddsPt();
        }
        if(userLevel == QXConstant.LEVEL_GUDONG) {
            return getOddsPt();
        }
        if(userLevel == QXConstant.LEVEL_ZHONGDAI) {
            return getOddsGd();
        }
        if(userLevel == QXConstant.LEVEL_DAILI) {
            return getOddsZd();
        }
        if(userLevel == QXConstant.LEVEL_HUIYUAN) {
            return getOddsDl();
        }
        return null;
    }

    //获取上级对我的抽水
    public BigDecimal getRakeNum(Integer userLevel) {
        BigDecimal tmp = getRake(userLevel);
        BigDecimal div = new BigDecimal(0);
        //二字定的，赔率差值0.1则抽水0.001
        if(getPlayTypeClass().equals("2p")) {
            div = BigDecimal.valueOf(0.1);
        }
        //三字定的，赔率差值1则抽水0.001
        if(getPlayTypeClass().equals("3p")) {
            div = BigDecimal.valueOf(1);
        }
        //四字定的，赔率差值10则抽水0.001
        if(getPlayTypeClass().equals("4p")) {
            div = BigDecimal.valueOf(10);
        }
        //二字现的，赔率差值0.01则抽水0.001
        if(getPlayTypeClass().equals("2px")) {
            div = BigDecimal.valueOf(0.01);
        }
        //三字现的，赔率差值0.05则抽水0.001
        if(getPlayTypeClass().equals("3px")) {
            div = BigDecimal.valueOf(0.05);
        }
        //四字现的，赔率差值0.4则抽水0.001
        if(getPlayTypeClass().equals("4px")) {
            div = BigDecimal.valueOf(0.4);
        }

        BigDecimal b = tmp.divideAndRemainder(div)[0].multiply(new BigDecimal(0.001));
        //保留三位小数
        return b.setScale(3, BigDecimal.ROUND_DOWN);
    }

    //获取上级对我的抽水， 既：上级赔率 减去 我的赔率
    public BigDecimal getRake(Integer userLevel) {
        try{
            if(userLevel == QXConstant.LEVEL_ADMIN) {
                return getOddsPt().subtract(getOddsPt());
            }
            if(userLevel == QXConstant.LEVEL_GUDONG) {
                return getOddsPt().subtract(getOddsGd());
            }
            if(userLevel == QXConstant.LEVEL_ZHONGDAI) {
                return getOddsGd().subtract(getOddsZd());
            }
            if(userLevel == QXConstant.LEVEL_DAILI) {
                return getOddsZd().subtract(getOddsDl());
            }
            if(userLevel == QXConstant.LEVEL_HUIYUAN) {
                return getOddsDl().subtract(getOddsHy());
            }
            if(userLevel == QXConstant.LEVEL_KF) {
                return getOddsHy().subtract(getOddsKf());
            }
        }catch (Exception e) {
        }
        return null;
    }

    //获取我的赔率
    public BigDecimal getMyOdds(Integer userLevel) {
        if(userLevel == QXConstant.LEVEL_ADMIN) {
            return getOddsPt();
        }
        if(userLevel == QXConstant.LEVEL_GUDONG) {
            return getOddsGd();
        }
        if(userLevel == QXConstant.LEVEL_ZHONGDAI) {
            return getOddsZd();
        }
        if(userLevel == QXConstant.LEVEL_DAILI) {
            return getOddsDl();
        }
        if(userLevel == QXConstant.LEVEL_HUIYUAN) {
            return getOddsHy();
        }
        return null;
    }


}