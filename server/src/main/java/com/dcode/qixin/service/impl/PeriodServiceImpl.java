package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.PeriodCountMapper;
import com.dcode.qixin.mapper.QxPeriodMapper;
import com.dcode.qixin.model.PeriodCount;
import com.dcode.qixin.model.QxPeriod;
import com.dcode.qixin.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "periodService")
public class PeriodServiceImpl implements PeriodService {

    @Autowired(required = false)
    private QxPeriodMapper periodMapper;
    @Autowired(required = false)
    private PeriodCountMapper periodCountMapper;

    @Override
    public int insertSelective(QxPeriod record) {
        return periodMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(QxPeriod record) {
        return periodMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public QxPeriod selectByPrimaryKey(Integer sysId) {
        return periodMapper.selectByPrimaryKey(sysId);
    }

    @Override
    public List<QxPeriod> getPeriodRecent() {
        return periodMapper.selectByRecent();
    }

    @Override
    public QxPeriod getPeriodLast() {
        return periodMapper.selectLast();
    }

    @Override
    public List<PeriodCount> getPeriodCount(PeriodCount periodCount) {
        return periodCountMapper.selectMyPeriodCount(periodCount);
    }

}
