package com.dcode.qixin.mapper;

import com.dcode.qixin.model.PeriodCount;
import com.dcode.qixin.model.PeriodCountKey;

import javax.validation.constraints.Max;
import java.util.List;

public interface PeriodCountMapper {
    int deleteByPrimaryKey(PeriodCountKey key);

    int insert(PeriodCount record);

    int insertSelective(PeriodCount record);

    PeriodCount selectByPrimaryKey(PeriodCountKey key);

    int updateByPrimaryKeySelective(PeriodCount record);

    int updateByPrimaryKey(PeriodCount record);

    int deletePeriodCount(Integer periodId);

    int doPeriodCount(Integer periodId);

    int updatePeriodUserId(Integer periodId);

    List<PeriodCount> selectMyPeriodCount(PeriodCount periodCount);

}