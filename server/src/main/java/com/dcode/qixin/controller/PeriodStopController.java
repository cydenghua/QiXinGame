package com.dcode.qixin.controller;

import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.model.PeriodStop;
import com.dcode.qixin.model.QxPeriod;
import com.dcode.qixin.model.User;
import com.dcode.qixin.service.PeriodService;
import com.dcode.qixin.service.PeriodStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/periodstop")
public class PeriodStopController extends BaseController {

    @Autowired
    protected PeriodStopService periodStopService;
    @Autowired
    protected PeriodService periodService;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }


    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object addPeriodStop(PeriodStop periodStop, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() < ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }

        QxPeriod period = periodService.getPeriodLast();
        periodStop.setPeriodId(period.getSysId());
        PeriodStop ps = periodStopService.selectByPrimaryKey(periodStop);
        if(null != ps) {
            ps.setDeleted(new Short("0"));
            periodStopService.updateByPrimaryKeySelective(ps);
            return ResultGenerator.genSuccessResult().setMessage("设置成功");
        }else {
            periodStopService.insertSelective(periodStop);
            return ResultGenerator.genSuccessResult().setMessage("设置成功");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public Object deletePeriodStop(PeriodStop periodStop, @RequestHeader("token")String token){
        User createUser = userService.getUserByToken(token);
        if(createUser.getLevel() < ProjectConstant.LEVEL_GUDONG) {
            return ResultGenerator.genFailResult("无权限操作");
        }

        QxPeriod period = periodService.getPeriodLast();
        periodStop.setPeriodId(period.getSysId());
        PeriodStop ps = periodStopService.selectByPrimaryKey(periodStop);
        if(null != ps) {
            ps.setDeleted(new Short("1"));
            periodStopService.updateByPrimaryKeySelective(ps);
            return ResultGenerator.genSuccessResult().setMessage("设置成功");
        }
        return ResultGenerator.genFailResult("数据不存在");
    }

    @ResponseBody
    @RequestMapping(value = "/getCurrPeriod", produces = {"application/json;charset=UTF-8"})
    public Object getPeriodStop(){
        QxPeriod period = periodService.getPeriodLast();
        PeriodStop periodStop = new PeriodStop();
        periodStop.setPeriodId(period.getSysId());
        return ResultGenerator.genSuccessResult(periodStopService.selectByPeriod(periodStop));
    }

}
