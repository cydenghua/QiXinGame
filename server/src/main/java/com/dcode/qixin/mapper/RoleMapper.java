package com.dcode.qixin.mapper;

import com.dcode.qixin.model.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer sysId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}