package com.dcode.qixin.service;

import com.dcode.qixin.model.User;
import com.dcode.qixin.model.UserOdds;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {


    int addUser(User user);

    User selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    User getUserByName(String userName);

    User getUserByToken(String userToken);

    List<User> getMyUsers(Integer userId);

    boolean createMyUser(User createUser, User newUser);

    boolean modifyUser(User createUser, User user);

    List<UserOdds> getUserOdds(Integer userId);

    
}
