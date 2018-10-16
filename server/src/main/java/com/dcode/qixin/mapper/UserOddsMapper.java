package com.dcode.qixin.mapper;

import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.model.UserOddsKey;

import java.util.List;

public interface UserOddsMapper {
    int deleteByPrimaryKey(UserOddsKey key);

    int insert(UserOdds record);

    int insertSelective(UserOdds record);

    UserOdds selectByPrimaryKey(UserOddsKey key);

    int updateByPrimaryKeySelective(UserOdds record);

    int updateByPrimaryKey(UserOdds record);

    List<UserOdds> selectByUserId(Integer userId);

    int addGdOdds(UserOdds record);
    int addZdOdds(UserOdds record);
    int addDlOdds(UserOdds record);
    int addHyOdds(UserOdds record);
    int addKfOdds(UserOdds record);
}