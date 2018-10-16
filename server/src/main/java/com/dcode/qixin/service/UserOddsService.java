package com.dcode.qixin.service;

import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.model.UserOddsKey;

public interface UserOddsService {

    int deleteByPrimaryKey(UserOddsKey key);

    int insert(UserOdds record);

    int insertSelective(UserOdds record);

    UserOdds selectByPrimaryKey(UserOddsKey key);

    int updateByPrimaryKeySelective(UserOdds record);

    int updateByPrimaryKey(UserOdds record);

    int modifyUserOdds(int level, UserOdds odds);

    int modifyUserOddsKf(UserOdds odds);

}
