package com.dcode.qixin.mapper;

import com.dcode.qixin.model.UserRoleKey;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

    UserRoleKey getUserRole(Integer userId);
}