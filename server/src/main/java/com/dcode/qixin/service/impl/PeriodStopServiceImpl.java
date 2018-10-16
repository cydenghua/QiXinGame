package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.PeriodStopMapper;
import com.dcode.qixin.model.PeriodStop;
import com.dcode.qixin.model.PeriodStopKey;
import com.dcode.qixin.service.PeriodStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "periodStopService")
public class PeriodStopServiceImpl implements PeriodStopService {

    @Autowired(required = false)
    private PeriodStopMapper periodStopMapper;


    @Override
    public int insertSelective(PeriodStop record) {
        return periodStopMapper.insertSelective(record);
    }

    @Override
    public PeriodStop selectByPrimaryKey(PeriodStopKey key) {
        return periodStopMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(PeriodStop record) {
        return periodStopMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<PeriodStop> selectByPeriod(PeriodStopKey key) {
        return periodStopMapper.selectByPeriod(key);
    }
}
