package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.UserRoleMapper;
import com.dcode.qixin.model.UserRoleKey;
import com.dcode.qixin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;//这里会报错，但是并不会影响

    @Override
    public UserRoleKey getUserRole(Integer userId) {
        return userRoleMapper.getUserRole(userId);
    }
}
