package com.dcode.qixin.service;

import com.dcode.qixin.model.PeriodOdds;
import com.dcode.qixin.model.PeriodOddsKey;

import java.util.List;

public interface PeriodOddsService {

    int insertSelective(PeriodOdds record);

    int updateByPrimaryKeySelective(PeriodOdds record);

    PeriodOdds selectByPrimaryKey(PeriodOddsKey key);

    List<PeriodOdds> selectByPeriod(Integer periodId);


}
