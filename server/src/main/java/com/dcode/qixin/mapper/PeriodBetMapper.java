package com.dcode.qixin.mapper;

import com.dcode.qixin.model.PeriodBet;

import java.util.List;

public interface PeriodBetMapper {
    int deleteByPrimaryKey(Integer sysId);

    int insert(PeriodBet record);

    int insertSelective(PeriodBet record);

    PeriodBet selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(PeriodBet record);

    int updateByPrimaryKey(PeriodBet record);

    List<PeriodBet> selectUserPeriodBet(PeriodBet record);

    List<PeriodBet> selectPeriodBet(Integer periodId);

}