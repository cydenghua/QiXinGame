package com.dcode.qixin.controller;


import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.model.User;
import com.dcode.qixin.model.UserOdds;
import com.dcode.qixin.model.UserOddsKey;
import com.dcode.qixin.service.UserOddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/api/userodds")
public class UserOddsController  extends  BaseController {

    @Autowired
    protected UserOddsService userOddsService;

    @ResponseBody
    @RequestMapping(value = "/getUserOdds", produces = {"application/json;charset=UTF-8"})
    public Object getUserOdds(String userName, @RequestHeader("token")String token){
        User createUser =  userService.getUserByToken(token);
        User user = userService.getUserByName(userName);
        if(!user.getCreateUser().equals(createUser.getSysId()) && !user.getSysId().equals(createUser.getSysId())) {
            return ResultGenerator.genFailResult("操作失败171");
        }
        return ResultGenerator.genSuccessResult(userService.getUserOdds(user.getSysId()));
    }

    @ResponseBody
    @RequestMapping(value = "/editUserOdds", produces = {"application/json;charset=UTF-8"})
    public Object editUserOdds(String userId, String playType, String curOdds, @RequestHeader("token")String token){
//        User createUser =  userService.getUserByToken(token);
        User user = userService.selectByPrimaryKey(Integer.valueOf(userId));
        BigDecimal inputCurOdds = new BigDecimal(curOdds);

        //获取当前的赔率信息
        UserOddsKey ok = new UserOddsKey();
        ok.setUserId(Integer.valueOf(userId));
        ok.setPlayType(playType);
        UserOdds odds = userOddsService.selectByPrimaryKey(ok);

        //赔率没有发生变化，无需修改， 直接返回
        if(odds.getCurrOdds(user.getLevel()).equals(inputCurOdds)){
            return ResultGenerator.genSuccessResult();
        }

        //输入的赔率不能大于最大赔率
        if(inputCurOdds.compareTo(odds.getMaxOdds(user.getLevel())) == 1) {
            return ResultGenerator.genFailResult("数据错误196");
        }

        //赔率的变化量
        BigDecimal sub = inputCurOdds.subtract(odds.getCurrOdds(user.getLevel()));
        odds.setOddsAdd(sub);
        userOddsService.modifyUserOdds(user.getLevel(), odds);
        return ResultGenerator.genSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value = "/editUserKfOdds", produces = {"application/json;charset=UTF-8"})
    public Object editUserOddsKf(String userId, String playType, String curOdds, @RequestHeader("token")String token){
        User createUser =  userService.getUserByToken(token);
        if(createUser.getLevel() != ProjectConstant.LEVEL_HUIYUAN) {
            return ResultGenerator.genFailResult("保存失败221");
        }

//        User user = userService.selectByPrimaryKey(Integer.valueOf(userId));
        BigDecimal inputCurOdds = new BigDecimal(curOdds);

        //获取当前的赔率信息
        UserOddsKey ok = new UserOddsKey();
        ok.setUserId(Integer.valueOf(userId));
        ok.setPlayType(playType);
        UserOdds odds = userOddsService.selectByPrimaryKey(ok);

        //赔率没有发生变化，无需修改， 直接返回
        if(odds.getOddsKf().equals(inputCurOdds)){
            return ResultGenerator.genSuccessResult();
        }

        //输入的赔率不能大于最大赔率
        if(inputCurOdds.compareTo(odds.getOddsHy()) == 1) {
            return ResultGenerator.genFailResult("数据错误241");
        }

        //赔率的变化量
        BigDecimal sub = inputCurOdds.subtract(odds.getOddsKf());
        odds.setOddsAdd(sub);
        userOddsService.modifyUserOddsKf(odds);
        return ResultGenerator.genSuccessResult();
    }


}
