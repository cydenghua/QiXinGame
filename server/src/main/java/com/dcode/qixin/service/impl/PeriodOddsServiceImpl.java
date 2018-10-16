package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.PeriodOddsMapper;
import com.dcode.qixin.mapper.QxPeriodMapper;
import com.dcode.qixin.model.PeriodOdds;
import com.dcode.qixin.model.PeriodOddsKey;
import com.dcode.qixin.model.QxPeriod;
import com.dcode.qixin.service.PeriodOddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "periodOddsService")
public class PeriodOddsServiceImpl implements PeriodOddsService {

    @Autowired(required = false)
    private PeriodOddsMapper periodOddsMapper;


    @Override
    public int insertSelective(PeriodOdds record) {
        return periodOddsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PeriodOdds record) {
        return periodOddsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PeriodOdds selectByPrimaryKey(PeriodOddsKey key) {
        return periodOddsMapper.selectByPrimaryKey(key);
    }

    @Override
    public List<PeriodOdds> selectByPeriod(Integer periodId) {
        return periodOddsMapper.selectByPeriod(periodId);
    }


}
