package com.dcode.qixin.mapper;

import com.dcode.qixin.model.PeriodStop;
import com.dcode.qixin.model.PeriodStopKey;

import java.util.List;

public interface PeriodStopMapper {
    int deleteByPrimaryKey(PeriodStopKey key);

    int insert(PeriodStop record);

    int insertSelective(PeriodStop record);

    PeriodStop selectByPrimaryKey(PeriodStopKey key);

    int updateByPrimaryKeySelective(PeriodStop record);

    int updateByPrimaryKey(PeriodStop record);

    List<PeriodStop> selectByPeriod(PeriodStopKey key);

}