package com.dcode.qixin.mapper;

import com.dcode.qixin.model.QxPeriod;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "periods")
public interface QxPeriodMapper {

    @CacheEvict(key ="#record.sysId",allEntries=true)
    int deleteByPrimaryKey(Integer sysId);

    @CacheEvict(key = "'LastPeriod'",allEntries=true)
    int insert(QxPeriod record);

    @CacheEvict(key = "'LastPeriod'",allEntries=true)
    int insertSelective(QxPeriod record);

    QxPeriod selectByPrimaryKey(Integer sysId);

    @CacheEvict(key = "'LastPeriod'")
    int updateByPrimaryKeySelective(QxPeriod record);

    @CacheEvict(key = "'LastPeriod'")
    int updateByPrimaryKey(QxPeriod record);

    List<QxPeriod> selectByRecent();

    @Cacheable(key ="'LastPeriod'")
    QxPeriod selectLast();

}