package com.dcode.qixin.mapper;

import com.dcode.qixin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "users")
public interface UserMapper {
    @CacheEvict(key = "#record.token",allEntries=true)
    int deleteByPrimaryKey(Integer sysId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer sysId);

    @CacheEvict(key = "#record.token",allEntries=true)
    int updateByPrimaryKeySelective(User record);

//    @CacheEvict(key = "#record.sysId")
    @CacheEvict(key = "#record.token",allEntries=true)
    int updateByPrimaryKey(User record);

    User getUserByName(String userName);

    @Cacheable(key ="#p0")
    User getUserByToken(String userToken);

    List<User> getMyUsers(Integer userId);

    List<User> getAllUsers(@Param("idList")List<Integer> idList);

}