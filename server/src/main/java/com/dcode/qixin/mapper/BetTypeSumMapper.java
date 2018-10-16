package com.dcode.qixin.mapper;

import com.dcode.qixin.model.BetTypeSum;

public interface BetTypeSumMapper {
    int deleteByPrimaryKey(Integer sysId);

    int insert(BetTypeSum record);

    int insertSelective(BetTypeSum record);

    BetTypeSum selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(BetTypeSum record);

    int updateByPrimaryKey(BetTypeSum record);

    BetTypeSum selectPeriodBetTypeSum(BetTypeSum record);

    int addPeriodBetTypeSum(BetTypeSum record);

}