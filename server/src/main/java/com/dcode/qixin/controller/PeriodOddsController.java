package com.dcode.qixin.controller;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.model.PeriodOdds;
import com.dcode.qixin.model.User;
import com.dcode.qixin.service.PeriodOddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/periododds")
public class PeriodOddsController extends BaseController {

    @Autowired
    protected PeriodOddsService periodOddsService;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"})
    public Object getPeriodOdds(Integer periodId){
        return ResultGenerator.genSuccessResult(periodOddsService.selectByPeriod(periodId));
    }

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object addPeriodOdds(PeriodOdds periodOdds, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() < ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }

        PeriodOdds po = periodOddsService.selectByPrimaryKey(periodOdds);
        if(null != po) {
            periodOddsService.updateByPrimaryKeySelective(periodOdds);
            return ResultGenerator.genSuccessResult().setMessage("修改成功");
        }else {
            periodOddsService.insertSelective(periodOdds);
            return ResultGenerator.genSuccessResult().setMessage("保存成功");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    public Object updatePeriodOdds(PeriodOdds periodOdds, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() < ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }
        periodOddsService.updateByPrimaryKeySelective(periodOdds);
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

}
