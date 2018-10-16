package com.dcode.qixin.service;


import com.dcode.qixin.model.PeriodStop;
import com.dcode.qixin.model.PeriodStopKey;

import java.util.List;

public interface PeriodStopService {


    int insertSelective(PeriodStop record);

    PeriodStop selectByPrimaryKey(PeriodStopKey key);

    int updateByPrimaryKeySelective(PeriodStop record);

    List<PeriodStop> selectByPeriod(PeriodStopKey key);


}
