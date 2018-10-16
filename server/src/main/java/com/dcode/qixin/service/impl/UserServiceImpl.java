package com.dcode.qixin.service.impl;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.mapper.UserMapper;
import com.dcode.qixin.mapper.UserOddsMapper;
import com.dcode.qixin.model.User;
import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private UserOddsMapper userOddsMapper;//这里会报错，但是并不会影响

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User selectByPrimaryKey(Integer sysId) {
        return userMapper.selectByPrimaryKey(sysId);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public User getUserByToken(String userToken) {
        return userMapper.getUserByToken(userToken);
    }

    @Override
    public List<User> getMyUsers(Integer userId) {
        return userMapper.getMyUsers(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public boolean createMyUser(User createUser, User newUser) {
        addUser(newUser);
        //init odds
        List<UserOdds> oddsList = userOddsMapper.selectByUserId(createUser.getSysId());
        for (UserOdds ods: oddsList) {
            ods.setUserId(newUser.getSysId());

            //新建下线，默认不抽水，则下线的赔率为我的当前赔率
            if(createUser.getLevel() == ProjectConstant.LEVEL_ADMIN) {
                ods.setUserIdPt(createUser.getSysId());
                ods.setUserIdGd(newUser.getSysId());
                ods.setOddsGd(ods.getOddsPt());
            }
            if(createUser.getLevel() == ProjectConstant.LEVEL_GUDONG) {
                ods.setUserIdGd(createUser.getSysId());
                ods.setUserIdZd(newUser.getSysId());
                ods.setOddsZd(ods.getOddsGd());
            }
            if(createUser.getLevel() == ProjectConstant.LEVEL_ZHONGDAI) {
                ods.setUserIdZd(createUser.getSysId());
                ods.setUserIdDl(newUser.getSysId());
                ods.setOddsDl(ods.getOddsZd());
            }
            if(createUser.getLevel() == ProjectConstant.LEVEL_DAILI) {
                ods.setUserIdDl(createUser.getSysId());
                ods.setUserIdHy(newUser.getSysId());
                ods.setOddsHy(ods.getOddsDl());
                ods.setOddsKf(ods.getOddsDl());
            }

            userOddsMapper.insert(ods);
        }

        updateByPrimaryKeySelective(createUser);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public boolean modifyUser(User createUser, User user) {
        updateByPrimaryKeySelective(createUser);
        updateByPrimaryKeySelective(user);
        return true;
    }

    @Override
    public List<UserOdds> getUserOdds(Integer userId) {
        List<UserOdds> oddsList = userOddsMapper.selectByUserId(userId);
        return oddsList;
    }


}
