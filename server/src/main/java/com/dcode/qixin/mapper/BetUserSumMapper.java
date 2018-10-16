package com.dcode.qixin.mapper;

import com.dcode.qixin.model.BetUserSum;

public interface BetUserSumMapper {
    int deleteByPrimaryKey(Integer sysId);

    int insert(BetUserSum record);

    int insertSelective(BetUserSum record);

    BetUserSum selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(BetUserSum record);

    int updateByPrimaryKey(BetUserSum record);

    BetUserSum selectPeriodBetUserSum(BetUserSum record);
}