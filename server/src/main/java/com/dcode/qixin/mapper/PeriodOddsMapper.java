package com.dcode.qixin.mapper;

import com.dcode.qixin.model.PeriodOdds;
import com.dcode.qixin.model.PeriodOddsKey;

import java.util.List;

public interface PeriodOddsMapper {
    int deleteByPrimaryKey(PeriodOddsKey key);

    int insert(PeriodOdds record);

    int insertSelective(PeriodOdds record);

    PeriodOdds selectByPrimaryKey(PeriodOddsKey key);

    int updateByPrimaryKeySelective(PeriodOdds record);

    int updateByPrimaryKey(PeriodOdds record);

    List<PeriodOdds> selectByPeriod(Integer periodId);
}