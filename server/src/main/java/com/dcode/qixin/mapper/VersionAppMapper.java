package com.dcode.qixin.mapper;

import com.dcode.qixin.model.VersionApp;

public interface VersionAppMapper {
    int deleteByPrimaryKey(Integer sysId);

    int insert(VersionApp record);

    int insertSelective(VersionApp record);

    VersionApp selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(VersionApp record);

    int updateByPrimaryKey(VersionApp record);

    VersionApp getLastVersion();

}