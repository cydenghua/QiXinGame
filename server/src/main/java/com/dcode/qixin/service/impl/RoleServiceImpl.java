package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.RoleMapper;
import com.dcode.qixin.model.Role;
import com.dcode.qixin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Override
    public Role selectByPrimaryKey(Integer sysId) {
        return roleMapper.selectByPrimaryKey(sysId);
    }
}
