package com.dcode.qixin.controller;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.entity.PeriodUserOddsEntity;
import com.dcode.qixin.model.*;
import com.dcode.qixin.service.PeriodBetService;
import com.dcode.qixin.service.PeriodOddsService;
import com.dcode.qixin.service.PeriodService;
import com.dcode.qixin.service.UserOddsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/periodbet")
public class PeriodBetController extends BaseController {

    @Autowired
    protected PeriodBetService periodBetService;
    @Autowired
    protected PeriodOddsService periodOddsService;
    @Autowired
    protected PeriodService periodService;
    @Autowired
    protected UserOddsService userOddsService;


//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }


    //获取下单赔率
    @ResponseBody
    @RequestMapping(value = "/getUserPeriodOdds", produces = {"application/json;charset=UTF-8"})
    public Object getUserPeriodOdds(PeriodBet periodBet, @RequestHeader("token")String token){
        //判断用户是会员，会员才可以下单
        User createUser = userService.getUserByToken(token);
        if(createUser == null || createUser.getLevel() != ProjectConstant.LEVEL_HUIYUAN) {
            return ResultGenerator.genFailResult("会员才能下单");
        }
        QxPeriod periodLast = periodService.getPeriodLast();
        if(periodLast.getStatus() != ProjectConstant.PERIOD_STATUS_ING) {
            return ResultGenerator.genFailResult("停止销售");
        }
        PeriodUserOddsEntity periodUserOddsEntity = periodBetService.getUserPeriodOddsEntity(createUser, periodLast, periodBet);
        return ResultGenerator.genSuccessResult(periodUserOddsEntity);
    }

    //下单
    @ResponseBody
    @RequestMapping(value = "/addUserPeriodBet", produces = {"application/json;charset=UTF-8"})
    public Object addUserPeriodBet(PeriodBet periodBet, @RequestHeader("token")String token){
        //判断用户是会员，会员才可以下单
        User createUser = userService.getUserByToken(token);
        if(createUser == null || createUser.getLevel() != ProjectConstant.LEVEL_HUIYUAN) {
            return ResultGenerator.genFailResult("会员才能下单");
        }

        if(createUser.getEneabled() < 1) {
            return ResultGenerator.genFailResult("用户已被锁定,请联系上级解锁");
        }

        QxPeriod periodLast = periodService.getPeriodLast();
        if(periodLast.getStatus() != ProjectConstant.PERIOD_STATUS_ING) {
            return ResultGenerator.genFailResult("停止销售");
        }

        if(periodLast.getStopTime().getTime() < System.currentTimeMillis()) {
            return ResultGenerator.genFailResult("已停止销售");
        }

        PeriodUserOddsEntity periodUserOddsEntity = periodBetService.getUserPeriodOddsEntity(createUser, periodLast, periodBet);
        String[] arrValue = periodBet.getPlayValue().split(",");
        if(periodUserOddsEntity.getTypeLeft() < periodBet.getPlayMoney()*arrValue.length) {
            return ResultGenerator.genFailResult("超出本期可下，请减少投注");
        }
        if(periodUserOddsEntity.getCreditLeft() < periodBet.getPlayMoney()*arrValue.length) {
            return ResultGenerator.genFailResult("信用额度不足，请减少投注");
        }

        periodBet.setUserId(createUser.getSysId());
        periodBet.setPeriodId(periodLast.getSysId());
        return periodBetService.addPeriodBet(periodBet, createUser, periodLast);
    }

    //退单
    @ResponseBody
    @RequestMapping(value = "/deleteUserPeriodBetBatch", produces = {"application/json;charset=UTF-8"})
    public Object deletePeriodOddsBatch(String idArr, @RequestHeader("token")String token){
        //判断用户是会员，会员才可以下单
        User createUser = userService.getUserByToken(token);
        if(createUser == null || createUser.getLevel() != ProjectConstant.LEVEL_HUIYUAN) {
            return ResultGenerator.genFailResult("会员才能下单");
        }

        if(periodService.getPeriodLast().getStatus() != ProjectConstant.PERIOD_STATUS_ING) {
            return ResultGenerator.genFailResult("停止销售，不能退单");
        }

        String arr[] = idArr.split(",");
        for (String sId : arr) {
            PeriodBet pb = periodBetService.selectByPrimaryKey(Integer.valueOf(sId));
            if (null == pb || !pb.getDeleted().equals(Short.valueOf("0"))) {
                return ResultGenerator.genFailResult("请勿重复退单");
            }
            if(!createUser.getSysId().equals(pb.getUserId())) {
                return ResultGenerator.genFailResult("非法操作");
            }
            //退码只能十分钟内
            if(pb.getCreatedTime().getTime() < System.currentTimeMillis()-10*60*1000) {
                return ResultGenerator.genFailResult("超过十分钟，不允许退单");
            }

        }

        if(periodBetService.deletePeriodBetBatch(idArr)) {
            return ResultGenerator.genSuccessResult().setMessage("退单成功");
        }else {
            return ResultGenerator.genFailResult("提交失败");
        }
    }


    //获取用户的记录
    @ResponseBody
    @RequestMapping(value = "/getUserPeriodBet", produces = {"application/json;charset=UTF-8"})
    public Object getUserPeriodBet(PeriodBet periodBet, @RequestHeader("token")String token){
        //判断用户是会员，会员才可以下单
        User createUser = userService.getUserByToken(token);
        if(createUser == null || createUser.getLevel() != ProjectConstant.LEVEL_HUIYUAN) {
            return ResultGenerator.genFailResult("会员才能下单");
        }

        periodBet.setUserId(createUser.getSysId());
        periodBet.setPeriodId(periodService.getPeriodLast().getSysId());
        return ResultGenerator.genSuccessResult(new PageInfo<PeriodBet>(periodBetService.getUserPeriodBet(periodBet)));
    }

}
