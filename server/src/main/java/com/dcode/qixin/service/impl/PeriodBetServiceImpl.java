package com.dcode.qixin.service.impl;

import com.dcode.qixin.controller.PeriodBetController;
import com.dcode.qixin.core.ProjectConstant;
import com.dcode.qixin.core.Result;
import com.dcode.qixin.core.ResultGenerator;
import com.dcode.qixin.entity.PeriodUserOddsEntity;
import com.dcode.qixin.mapper.*;
import com.dcode.qixin.model.*;
import com.dcode.qixin.service.PeriodBetService;
import com.dcode.qixin.service.PeriodOddsService;
import com.dcode.qixin.service.UserOddsService;
import com.dcode.qixin.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "periodBetService")
public class PeriodBetServiceImpl implements PeriodBetService {

    @Autowired(required = false)
    private QxPeriodMapper periodMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private PeriodBetMapper periodBetMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private PeriodCountMapper periodCountMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private BetTypeSumMapper betTypeSumMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private BetUserSumMapper betUserSumMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private UserOddsMapper userOddsMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private PeriodOddsMapper periodOddsMapper;//这里会报错，但是并不会影响
    @Autowired(required = false)
    private PeriodStopMapper periodStopMapper;//这里会报错，但是并不会影响


    @Override
    public int insertSelective(PeriodBet record) {
        return periodBetMapper.insertSelective(record);
    }

    @Override
    public PeriodBet selectByPrimaryKey(Integer sysId) {
        return periodBetMapper.selectByPrimaryKey(sysId);
    }

    @Override
    public int updateByPrimaryKeySelective(PeriodBet record) {
        return periodBetMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PeriodUserOddsEntity getUserPeriodOddsEntity(User createUser, QxPeriod periodLast, PeriodBet periodBet) {
        periodBet.setUserId(createUser.getSysId());
        periodBet.setPeriodId(periodLast.getSysId());
        //从用户赔率表里面获取赔率
        UserOddsKey uok = new UserOddsKey();
        uok.setUserId(createUser.getSysId());
        uok.setPlayType(periodBet.getPlayType());
        UserOdds uo = userOddsMapper.selectByPrimaryKey(uok);

        //2.是否有调整的赔率，如果有取这赔率， 是否与第一个赔率对比，取小赔率
        PeriodOddsKey pok = new PeriodOddsKey();
        pok.setPeriodId(periodLast.getSysId());
        pok.setpValue(periodBet.getPlayValue());
        pok.setPlayClass(periodBet.getPlayClass());
        PeriodOdds po = periodOddsMapper.selectByPrimaryKey(pok);

        PeriodUserOddsEntity periodUserOddsEntity = new PeriodUserOddsEntity();
        periodUserOddsEntity.setMinPlay(uo.getMinPlay());
        periodUserOddsEntity.setMaxPlay(uo.getMaxPlay());
        periodUserOddsEntity.setMaxClassPlay(uo.getMaxClassPlay());
        periodUserOddsEntity.setBetTypeSum(getBetTypeSum(periodBet));
        periodUserOddsEntity.setBetUserSum(getBetUserSum(periodBet));

        periodUserOddsEntity.setCreditLeft(createUser.getCreditLimit() - periodUserOddsEntity.getBetUserSum().getPlaySum());  //信用额度剩余
        periodUserOddsEntity.setTypeLeft(uo.getMaxClassPlay() - periodUserOddsEntity.getBetTypeSum().getPlaySum());   //本期剩余

        if(po ==  null) {
            periodUserOddsEntity.setOdds(uo.getOddsKf());
        }else {
            periodUserOddsEntity.setOdds(po.getOddsNew());
        }
        return periodUserOddsEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public Result addPeriodBet(PeriodBet periodBet, User createUser, QxPeriod periodLast) {

        List<PeriodBet> resList = new ArrayList<>();
        String[] arrValue = periodBet.getPlayValue().split(",");

        PeriodStopKey psk = new PeriodStopKey();
        PeriodStop ps;

        for (int i = 0; i < arrValue.length; i++) {
            periodBet.setPlayValue(arrValue[i]);
            periodBet.setSysId(null);

            PeriodUserOddsEntity periodUserOddsEntity = getUserPeriodOddsEntity(createUser, periodLast, periodBet);
            periodBet.setPlayOdds(periodUserOddsEntity.getOdds());

            psk.setPeriodId(periodBet.getPeriodId());
            psk.setpValue(periodBet.getPlayValue());
            psk.setIsXian(periodBet.getPlayClass().contains("x") ? new Short("1") : new Short("0") );
            ps = periodStopMapper.selectByPrimaryKey(psk);
            if(null != ps && ps.getDeleted().equals(new Short("0"))) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return  ResultGenerator.genFailResult(periodBet.getPlayValue() + " 已停押");
            }
            if(periodUserOddsEntity.getTypeLeft() < periodBet.getPlayMoney()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return  ResultGenerator.genFailResult("超出本期可下，请减少投注");
            }
            if(periodUserOddsEntity.getCreditLeft() < periodBet.getPlayMoney()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultGenerator.genFailResult("信用额度不足，请减少投注");
            }
            if(periodBet.getPlayMoney() < periodUserOddsEntity.getMinPlay()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultGenerator.genFailResult("金额输入太小了");
            }
            if (periodBet.getPlayMoney() > periodUserOddsEntity.getMaxPlay()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultGenerator.genFailResult("单注金额太大了");
            }
            if (periodBet.getPlayMoney() > periodUserOddsEntity.getTypeLeft()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultGenerator.genFailResult(periodBet.getPlayValue() + "  号码当前可下只剩:" + periodUserOddsEntity.getTypeLeft()/100);
            }

            //下注
            insertSelective(periodBet);
            periodBet.setDeleted(Short.valueOf("0"));

            //增加本期类型汇总，便于判断类型是否超出上限
            BetTypeSum betTypeSum = getBetTypeSum(periodBet);
            if(betTypeSum.getSysId() == null) {
                betTypeSum.setPlaySum(periodBet.getPlayMoney());
                betTypeSumMapper.insert(betTypeSum);
            }else {
                betTypeSum.setPlaySum(periodBet.getPlayMoney());
                betTypeSumMapper.addPeriodBetTypeSum(betTypeSum);
            }

            //增加本期用户汇总，便于判断类型是否超出用户信用额度
            BetUserSum betUserSum = getBetUserSum(periodBet);
            if(betUserSum.getSysId() == null) {
                betUserSum.setPlaySum(periodBet.getPlayMoney());
                betUserSumMapper.insert(betUserSum);
            }else {
                betUserSum.setPlaySum(betUserSum.getPlaySum()+periodBet.getPlayMoney());
                betUserSumMapper.updateByPrimaryKeySelective(betUserSum);
            }

            PeriodBet pb = new PeriodBet();
            BeanUtils.copyProperties(periodBet, pb);
            pb.setCreatedTime(new Date(System.currentTimeMillis()));
            resList.add(pb);
        }
        return ResultGenerator.genSuccessResult(resList).setMessage("增加成功");
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public boolean deletePeriodBet(PeriodBet record) {
        //退单
        PeriodBet pb = new PeriodBet();
        pb.setSysId(record.getSysId());
        pb.setDeleted(Short.decode("1"));
        updateByPrimaryKeySelective(pb);

        //减少本期类型汇总，便于判断类型是否超出上限
        BetTypeSum betTypeSum = getBetTypeSum(record);
        betTypeSum.setPlaySum(-1*record.getPlayMoney());
        betTypeSumMapper.addPeriodBetTypeSum(betTypeSum);

        //减少本期用户汇总，便于判断类型是否超出用户信用额度
        BetUserSum betUserSum = getBetUserSum(record);
        betUserSum.setPlaySum(betUserSum.getPlaySum()-record.getPlayMoney());
        betUserSumMapper.updateByPrimaryKeySelective(betUserSum);

        return true;
    }

    @Override
    public BetUserSum getBetUserSum(PeriodBet record) {
        BetUserSum betUserSum = new BetUserSum();
        betUserSum.setPeriodId(record.getPeriodId());
        betUserSum.setUserId(record.getUserId());
        betUserSum = betUserSumMapper.selectPeriodBetUserSum(betUserSum);
        if(null == betUserSum){
            betUserSum = new BetUserSum();
            betUserSum.setPeriodId(record.getPeriodId());
            betUserSum.setUserId(record.getUserId());
            betUserSum.setPlaySum(0);
        }
        return betUserSum;
    }

    @Override
    public BetTypeSum getBetTypeSum(PeriodBet record) {
        BetTypeSum betTypeSum = new BetTypeSum();
        betTypeSum.setPeriodId(record.getPeriodId());
        betTypeSum.setPlayType(record.getPlayType());
        betTypeSum = betTypeSumMapper.selectPeriodBetTypeSum(betTypeSum);
        if(null == betTypeSum) {
            betTypeSum = new BetTypeSum();
            betTypeSum.setPeriodId(record.getPeriodId());
            betTypeSum.setPlayType(record.getPlayType());
            betTypeSum.setPlaySum(0);
        }
        return betTypeSum;
    }

    @Override
    public List<PeriodBet> getUserPeriodBet(PeriodBet record) {
        if (record.getPage() != null && record.getRows() != null) {
            PageHelper.startPage(record.getPage(), record.getRows());
        }
        return  periodBetMapper.selectUserPeriodBet(record);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public boolean deletePeriodBetBatch(String idArr) {
        String arr[] = idArr.split(",");
        for (String sId : arr) {
            PeriodBet pb = new PeriodBet();
            pb.setSysId(Integer.valueOf(sId));
            pb.setDeleted(Short.decode("1"));
            updateByPrimaryKeySelective(pb);

            PeriodBet pb2 = selectByPrimaryKey(Integer.valueOf(sId));
            //减少本期类型汇总，便于判断类型是否超出上限
            BetTypeSum betTypeSum = getBetTypeSum(pb2);
            betTypeSum.setPlaySum(-1*pb2.getPlayMoney());
            betTypeSumMapper.addPeriodBetTypeSum(betTypeSum);

            //减少本期用户汇总，便于判断类型是否超出用户信用额度
            BetUserSum betUserSum = getBetUserSum(pb2);
            betUserSum.setPlaySum(betUserSum.getPlaySum()-pb2.getPlayMoney());
            betUserSumMapper.updateByPrimaryKeySelective(betUserSum);
        }
        return true;
    }

    //开奖计算结果
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    @Override
    public boolean processPeriodOut(QxPeriod period) {
        List<PeriodBet> pList = periodBetMapper.selectPeriodBet(period.getSysId());
        if(pList == null) {
            return true;
        }

        int k = 0;
        UserOddsKey uok = new UserOddsKey();
        UserOdds uo = new UserOdds();
        String periodRes = period.getP1().toString() +period.getP2().toString() +period.getP3().toString() +period.getP4().toString();
        String tmpStr = "";
        boolean tmpBool = false;
        for (PeriodBet pb : pList) {
            tmpBool = true;
            if(pb.getPlayClass().contains("x")) {  //处理现
                //需要同时满足两个条件：所有现都有出现在结果中  且  结果中包含现中的数字长度大于等于现
                for (int i = 0; i < pb.getPlayValue().length(); i++) {
                    if(!periodRes.contains(pb.getPlayValue().substring(i, i+1))) {
                        tmpBool = false;
                    }
                }
                k = 0;
                if(tmpBool) {
                    if(pb.getPlayValue().contains(period.getP1().toString())) { k++; }
                    if(pb.getPlayValue().contains(period.getP2().toString())) { k++; }
                    if(pb.getPlayValue().contains(period.getP3().toString())) { k++; }
                    if(pb.getPlayValue().contains(period.getP4().toString())) { k++; }
                    if(k < pb.getPlayValue().length()){
                        tmpBool = false;
                    }
                }
            }else {//处理定
                tmpStr = pb.getPlayValue().substring(0, 1);
                if(!"X".equals(tmpStr) && !tmpStr.equals(period.getP1().toString())) { tmpBool = false; }
                tmpStr = pb.getPlayValue().substring(1, 2);
                if(!"X".equals(tmpStr) && !tmpStr.equals(period.getP2().toString())) { tmpBool = false; }
                tmpStr = pb.getPlayValue().substring(2, 3);
                if(!"X".equals(tmpStr) && !tmpStr.equals(period.getP3().toString())) { tmpBool = false; }
                tmpStr = pb.getPlayValue().substring(3, 4);
                if(!"X".equals(tmpStr) && !tmpStr.equals(period.getP4().toString())) { tmpBool = false; }
            }
            pb.setPeriodResult(tmpBool ? ProjectConstant.PERIOD_RESULT_ZHONGJIANG : ProjectConstant.PERIOD_RESULT_WEIZHONG);

            //计算结果
            if(tmpBool) {
                BigDecimal bd = pb.getPlayOdds().multiply(new BigDecimal(pb.getPlayMoney()));
                pb.setPayKf(bd.intValue());
            }else {
                pb.setPayKf(0);
            }

            //计算抽水
            if(!pb.getPlayType().equals(uok.getPlayType()) || !pb.getUserId().equals(uok.getUserId())) {
                uok.setUserId(pb.getUserId());
                uok.setPlayType(pb.getPlayType());
                uo = userOddsMapper.selectByPrimaryKey(uok);
            }
            BigDecimal bdMoney = new BigDecimal(pb.getPlayMoney()*10); //*10 单位扩展到厘
            pb.setRakeGd(uo.getRakeNum(ProjectConstant.LEVEL_GUDONG).multiply(bdMoney).intValue());
            pb.setRakeZd(uo.getRakeNum(ProjectConstant.LEVEL_ZHONGDAI).multiply(bdMoney).intValue());
            pb.setRakeDl(uo.getRakeNum(ProjectConstant.LEVEL_DAILI).multiply(bdMoney).intValue());
            pb.setRakeHy(uo.getRakeNum(ProjectConstant.LEVEL_HUIYUAN).multiply(bdMoney).intValue());

            periodBetMapper.updateByPrimaryKeySelective(pb);
        }

        periodMapper.updateByPrimaryKeySelective(period);

        periodCountMapper.deletePeriodCount(period.getSysId());
        periodCountMapper.doPeriodCount(period.getSysId());
        periodCountMapper.updatePeriodUserId(period.getSysId());

        return true;
    }


}
