package com.dcode.qixin.controller;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.entity.LoginSuccessEntity;
import com.dcode.qixin.model.User;
import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.model.UserOddsKey;
import com.dcode.qixin.service.PeriodService;
import com.dcode.qixin.service.UserOddsService;
import com.dcode.qixin.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/api/user")
public class UserController extends  BaseController {

    @Autowired
    protected UserOddsService userOddsService;
    @Autowired
    protected PeriodService periodService;

    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public Object loginUser(User user){

        if(user.getName().isEmpty() || user.getPassword().isEmpty()) {
            return ResultGenerator.genFailResult("用户名 或 密码不能为空");
        }

        Logger.getLogger("aaaaaaaaaaaaa").info("user login, " + user.getName());

        User userInfo = userService.getUserByName(user.getName());
        if(userInfo == null) {
            return ResultGenerator.genFailResult("用户不存在");
        }

        int iPwTotalErr = 5;
        if(userInfo.getPwErrCount() >= iPwTotalErr) {
            return ResultGenerator.genFailResult("密码错误次数超过5次,请联系上级解锁");
        }

        if(userInfo.getEneabled() < 1) {
            return ResultGenerator.genFailResult("用户已被锁定,请联系上级解锁");
        }

        if(userInfo.getPassword().equals(Md5Util.EncoderByMd5(user.getPassword()))) {
            userInfo.setPwErrCount(Short.valueOf("0"));  //清零密码错误次数
            userInfo.setToken(UUID.randomUUID().toString().replace("-", "")); //生成token
            userInfo.setTokenExpire(System.currentTimeMillis()/1000 + 24*3600 );  //设置token的有效期为24小时
            userService.updateByPrimaryKeySelective(userInfo);

            User loginUserInfo = new User();
            loginUserInfo.setToken(userInfo.getToken());
            loginUserInfo.setLevel(userInfo.getLevel());
            loginUserInfo.setName(userInfo.getName());

            LoginSuccessEntity loginSuccessEntity = new LoginSuccessEntity();
            loginSuccessEntity.setLastPeriod(periodService.getPeriodLast());
            loginSuccessEntity.setUserInfo(loginUserInfo);

            return ResultGenerator.genSuccessResult(loginSuccessEntity).setMessage("登录成功");
        }else {
            int i = userInfo.getPwErrCount() + 1;   //密码错误次数+1
            userInfo.setPwErrCount(Short.valueOf(String.valueOf(i)));
            if(i == iPwTotalErr) {
                userInfo.setEneabled(Short.valueOf("0"));
            }
            userService.updateByPrimaryKeySelective(userInfo);
            return ResultGenerator.genFailResult("密码错误,还有" + (iPwTotalErr-i)+"次机会");
        }
//        return ResultGenerator.genFailResult("登录失败");
    }

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object addUser(User user, @RequestHeader("token")String token){
//        Logger.getLogger("dddd").log(Level.SEVERE, "token is " + token);
        try {
            User createUser = userService.getUserByToken(token);
            if (createUser == null) {
                return ResultGenerator.genUnauthorizedResult();
            }

            if (createUser.getLevel() > ProjectConstant.LEVEL_DAILI ) {
                return ResultGenerator.genFailResult("没有操作权限");
            }

            User userInfo = userService.getUserByName(user.getName());
            if(userInfo != null) {
                return ResultGenerator.genFailResult("用户名重复");
            }

            user.setCreditLimit(user.getCreditLimit()*100);  //转换为分存储
            user.setCreditLimitLeft(user.getCreditLimit());
            //增加下线时，需要验证信用额度是否足够
            if(createUser.getLevel() > ProjectConstant.LEVEL_ADMIN) {
                if(user.getCreditLimit() > createUser.getCreditLimitLeft()) {
                    return ResultGenerator.genFailResult("信用额度超过剩余额度，当前剩余信用额度为：" + createUser.getCreditLimitLeft()/100);
                }
                createUser.setCreditLimitLeft(createUser.getCreditLimitLeft()-user.getCreditLimit());
            }

            user.setLevel(Short.valueOf(String.valueOf(createUser.getLevel()+1)));
            user.setPassword(Md5Util.EncoderByMd5(user.getPassword()));  //对前端给出的密码进行编码后保存
            user.setCreateUser(createUser.getSysId());
            if(userService.createMyUser(createUser, user)) {//在事务里面处理
                return ResultGenerator.genSuccessResult().setMessage("增加成功");
            }else  {
                return ResultGenerator.genFailResult("增加失败");
            }
        }catch (Exception e){
            return ResultGenerator.genFailResult("增加失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/edit", produces = {"application/json;charset=UTF-8"})
    public Object editUser(User user, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        User oldUser = userService.getUserByName(user.getName());
        user.setSysId(oldUser.getSysId());
        if(!user.getCreateUser().equals(createUser.getSysId())) {
            return ResultGenerator.genFailResult("保存失败，非法操作");
        }
        if(!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(Md5Util.EncoderByMd5(user.getPassword()));  //对前端给出的密码进行编码后保存
        }

        //修改下线时，需要验证信用额度是否足够
        if(createUser.getLevel() > ProjectConstant.LEVEL_ADMIN) {
            if(user.getCreditLimit() != null) {
                user.setCreditLimit(user.getCreditLimit()*100);  //转换为分存储
                Integer bx = user.getCreditLimit() - oldUser.getCreditLimit();
                user.setCreditLimitLeft(oldUser.getCreditLimitLeft()+bx);
                if(createUser.getCreditLimitLeft()-bx < 0) {
                    return ResultGenerator.genFailResult("保存失败，信用额度不足， 剩余：" + (createUser.getCreditLimitLeft()/100));
                }
                createUser.setCreditLimitLeft(createUser.getCreditLimitLeft()-bx);
            }
        }
        if(userService.modifyUser(createUser, user)) {
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMyUsers", produces = {"application/json;charset=UTF-8"})
    public Object getMyUsers(@RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if (createUser == null) {
            return ResultGenerator.genUnauthorizedResult();
        }
        Object obj = userService.getMyUsers(createUser.getSysId());
        return ResultGenerator.genSuccessResult(obj);
    }

    @ResponseBody
    @RequestMapping(value = "/getSelfUserInfo", produces = {"application/json;charset=UTF-8"})
    public Object getSelfUserInfo(@RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if (createUser == null) {
            return ResultGenerator.genUnauthorizedResult();
        }

        //重新生成user， 避免敏感字段泄露
        User user = new User();
        user.setName(createUser.getName());
        user.setLevel(createUser.getLevel());
        user.setCreditLimit(createUser.getCreditLimit());
        user.setCreditLimitLeft(createUser.getCreditLimitLeft());

        return ResultGenerator.genSuccessResult(user);
    }

}
