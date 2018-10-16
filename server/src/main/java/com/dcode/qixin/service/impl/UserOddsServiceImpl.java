package com.dcode.qixin.service.impl;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.mapper.UserOddsMapper;
import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.model.UserOddsKey;
import com.dcode.qixin.service.UserOddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "userOddsService")
public class UserOddsServiceImpl implements UserOddsService {

    @Autowired(required = false)
    private UserOddsMapper userOddsMapper;//这里会报错，但是并不会影响


    @Override
    public int deleteByPrimaryKey(UserOddsKey key) {
        return userOddsMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(UserOdds record) {
        return userOddsMapper.insert(record);
    }

    @Override
    public int insertSelective(UserOdds record) {
        return userOddsMapper.insertSelective(record);
    }

    @Override
    public UserOdds selectByPrimaryKey(UserOddsKey key) {
        return userOddsMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(UserOdds record) {
        return userOddsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserOdds record) {
        return userOddsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int modifyUserOdds(int level, UserOdds odds) {
        //修改当前赔率
        if(level == ProjectConstant.LEVEL_GUDONG) {
            return userOddsMapper.addGdOdds(odds);
        }
        if(level == ProjectConstant.LEVEL_ZHONGDAI) {
            return userOddsMapper.addZdOdds(odds);
        }
        if(level == ProjectConstant.LEVEL_DAILI) {
            return userOddsMapper.addDlOdds(odds);
        }
        if(level == ProjectConstant.LEVEL_HUIYUAN) {
            return userOddsMapper.addHyOdds(odds);
        }
        return 0;
    }

    @Override
    public int modifyUserOddsKf(UserOdds odds) {
        //修改当前赔率
        return userOddsMapper.addKfOdds(odds);
    }


}
