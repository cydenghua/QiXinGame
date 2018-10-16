package com.dcode.qixin.controller;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.model.PeriodCount;
import com.dcode.qixin.model.PeriodCountGroup;
import com.dcode.qixin.model.QxPeriod;
import com.dcode.qixin.model.User;
import com.dcode.qixin.service.PeriodBetService;
import com.dcode.qixin.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/api/period")
public class PeriodController extends BaseController {

    @Autowired
    protected PeriodService periodService;
    @Autowired
    protected PeriodBetService periodBetService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @RequestMapping(value = "/recent", produces = {"application/json;charset=UTF-8"})
    public Object getRecent(){
//        List<QxPeriod> ll = periodService.getPeriodRecent();
//        return ResultGenerator.genSuccessResult(ll);
        return ResultGenerator.genSuccessResult(periodService.getPeriodRecent());
    }

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object addPeriod(QxPeriod period, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() > ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }

        periodService.insertSelective(period);
        return ResultGenerator.genSuccessResult().setMessage("增加成功");
    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    public Object updatePeriod(QxPeriod period, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() > ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }

        QxPeriod tp = periodService.selectByPrimaryKey(period.getSysId());
        if (tp.getStatus() == ProjectConstant.PERIOD_STATUS_END) {
            return ResultGenerator.genFailResult("已结束，不能修改");
        }

        if (tp.getStatus() > period.getStatus()) {
            return ResultGenerator.genFailResult("状态不能倒回");
        }

        if(period.getStatus() > ProjectConstant.PERIOD_STATUS_CLOSE) {
            if(period.getP1().toString().isEmpty()
                    || period.getP2().toString().isEmpty()
                    || period.getP3().toString().isEmpty()
                    || period.getP4().toString().isEmpty()
                    || period.getP5().toString().isEmpty()
                    || period.getP6().toString().isEmpty()
                    || period.getP7().toString().isEmpty() ) {
                return ResultGenerator.genFailResult("开奖号码不能为空");
            }
        }

        if(period.getStatus() == ProjectConstant.PERIOD_STATUS_OUT) { //状态设置为已开奖，计算
            periodBetService.processPeriodOut(period);
        }else {
            periodService.updateByPrimaryKeySelective(period);
        }
        return ResultGenerator.genSuccessResult().setMessage("操作成功");
    }

    @ResponseBody
    @RequestMapping(value = "/getlast", produces = {"application/json;charset=UTF-8"})
    public Object getLast(){
        return ResultGenerator.genSuccessResult(periodService.getPeriodLast());
    }


    private List<PeriodCount> getPeriodCountData(String token){
        User createUser = userService.getUserByToken(token);

        PeriodCount periodCount = new PeriodCount();
        if(createUser.getLevel() == ProjectConstant.LEVEL_GUDONG) {
            periodCount.setGdId(createUser.getSysId());
        }else {
            periodCount.setGdId(0);
        }
        if(createUser.getLevel() == ProjectConstant.LEVEL_ZHONGDAI) {
            periodCount.setZdId(createUser.getSysId());
        }else {
            periodCount.setZdId(0);
        }
        if(createUser.getLevel() == ProjectConstant.LEVEL_DAILI) {
            periodCount.setDlId(createUser.getSysId());
        }else {
            periodCount.setDlId(0);
        }
        if(createUser.getLevel() == ProjectConstant.LEVEL_HUIYUAN) {
            periodCount.setHyId(createUser.getSysId());
        }else {
            periodCount.setHyId(0);
        }

        return periodService.getPeriodCount(periodCount);
    }

    @ResponseBody
    @RequestMapping(value = "/getPeriodCount", produces = {"application/json;charset=UTF-8"})
    public Object getPeriodCount(@RequestHeader("token")String token){
        return ResultGenerator.genSuccessResult(getPeriodCountData(token));
    }

    @ResponseBody
    @RequestMapping(value = "/getPeriodCountGroup", produces = {"application/json;charset=UTF-8"})
    public Object getPeriodCountGroup(@RequestHeader("token")String token){
        List<PeriodCount> pList = getPeriodCountData(token);
        List<PeriodCountGroup> pGroupList = new ArrayList<>();
        String pid = "0";
        PeriodCountGroup pGroup = new PeriodCountGroup();
        for (PeriodCount pCount: pList) {
            if(!pid.equals(pCount.getpId())) {
                pid = pCount.getpId();
                pGroup = new PeriodCountGroup();
                pGroupList.add(pGroup);

                PeriodCount pTitle = new PeriodCount();
                pTitle.setPeriodId(pCount.getPeriodId());
                pTitle.setpId(pCount.getpId());
                pTitle.setpTime(pCount.getpTime());
                pTitle.setPlayMoney(pCount.getPlayMoney());
                pTitle.setRakeGd(pCount.getRakeGd());
                pTitle.setRakeZd(pCount.getRakeZd());
                pTitle.setRakeDl(pCount.getRakeDl());
                pTitle.setRakeHy(pCount.getRakeHy());
                pTitle.setPayKf(pCount.getPayKf());

                pGroup.setPeriodCountGroup(pTitle);
            }else {
                pGroup.getPeriodCountGroup().setPlayMoney(pGroup.getPeriodCountGroup().getPlayMoney() + pCount.getPlayMoney());
                pGroup.getPeriodCountGroup().setRakeGd(pGroup.getPeriodCountGroup().getRakeGd() + pCount.getRakeGd());
                pGroup.getPeriodCountGroup().setRakeZd(pGroup.getPeriodCountGroup().getRakeZd() + pCount.getRakeZd());
                pGroup.getPeriodCountGroup().setRakeDl(pGroup.getPeriodCountGroup().getRakeDl() + pCount.getRakeDl());
                pGroup.getPeriodCountGroup().setRakeHy(pGroup.getPeriodCountGroup().getRakeHy() + pCount.getRakeHy());
                pGroup.getPeriodCountGroup().setPayKf(pGroup.getPeriodCountGroup().getPayKf() + pCount.getPayKf());
            }
            pGroup.getPeriodCountDetail().add(pCount);
        }

        return ResultGenerator.genSuccessResult(pGroupList);
    }


}
