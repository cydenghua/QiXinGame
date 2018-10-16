package com.dcode.qixin.service;

import com.dcode.qixin.core.Result;
import com.dcode.qixin.entity.PeriodUserOddsEntity;
import com.dcode.qixin.model.*;

import java.util.List;

public interface PeriodBetService {

    int insertSelective(PeriodBet record);

    PeriodBet selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(PeriodBet record);

    PeriodUserOddsEntity getUserPeriodOddsEntity(User createUser, QxPeriod periodLast, PeriodBet periodBet);

    Result addPeriodBet(PeriodBet record, User createUser, QxPeriod periodLast);

    boolean deletePeriodBet(PeriodBet record);

    BetUserSum getBetUserSum(PeriodBet record);

    BetTypeSum getBetTypeSum(PeriodBet record);

    List<PeriodBet> getUserPeriodBet(PeriodBet record);

    boolean deletePeriodBetBatch(String idArr);

    boolean processPeriodOut(QxPeriod period);

}
