package com.dcode.qixin.service;

import com.dcode.qixin.model.PeriodCount;
import com.dcode.qixin.model.QxPeriod;

import java.util.List;

public interface PeriodService {

    int insertSelective(QxPeriod record);

    int updateByPrimaryKeySelective(QxPeriod record);

    QxPeriod selectByPrimaryKey(Integer sysId);

    List<QxPeriod> getPeriodRecent();

    QxPeriod getPeriodLast();

    List<PeriodCount> getPeriodCount(PeriodCount periodCount);
}
