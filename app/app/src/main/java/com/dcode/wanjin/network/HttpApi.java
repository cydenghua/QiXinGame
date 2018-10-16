package com.dcode.wanjin.network;

public class HttpApi {

    public static String BASE_URL = "http://192.168.3.99:8090";

    public static String getUrlWithHost(String url) {
        return HttpApi.BASE_URL + url;
    }

    public static final String getVersion = "/version/getVersion";//检查版本信息
    public static final String login = "/api/user/login";//用户登录

    //会员用户管理
    public static final String getMyUsers = "/api/user/getMyUsers";//获取我的会员
    public static final String getSelfUserInfo = "/api/user/getSelfUserInfo";//获取我的信息
    public static final String addMyUser = "/api/user/add";//增加我的会员
    public static final String editMyUser = "/api/user/edit";//编辑我的会员
    public static final String getUserOdds = "/api/userodds/getUserOdds";//获取我的会员赔率设置
    public static final String editUserOdds = "/api/userodds/editUserOdds";//修改会员赔率设置
    public static final String editUserKfOdds = "/api/userodds/editUserKfOdds";//会员修改给顾客的赔率

    //奖期
    public static final String getPeriodRecent = "/api/period/recent";//获取最近的奖期
    public static final String addPeriod = "/api/period/add";//增加奖期
    public static final String updatePeriod = "/api/period/update";//更新奖期
    public static final String getLastPeriod = "/api/period/getlast";//最新的奖期
    public static final String getPeriodCount = "/api/period/getPeriodCount";//奖期账单
    public static final String getPeriodCountGroup = "/api/period/getPeriodCountGroup";//奖期账单Group

    //奖期,停押
    public static final String addPeriodStop = "/api/periodstop/add";//设置本期停押
    public static final String deletePeriodStop = "/api/periodstop/delete";//删除本期停押
    public static final String getPeriodStopCurrPeriod = "/api/periodstop/getCurrPeriod";//获取本期停押

    //奖期赔率
    public static final String getPeriodOdds = "/api/periododds/get";//获取
    public static final String addPeriodOdds = "/api/periododds/add";//增加
    public static final String updatePeriodOdds = "/api/periododds/update";//更新

    //下单
    public static final String getUsePeriodOdds = "/api/periodbet/getUserPeriodOdds";//获取本期赔率
    public static final String addUserPeriodBet = "/api/periodbet/addUserPeriodBet";//下单
//    public static final String deleteUserPeriodBet = "/api/periodbet/deleteUserPeriodBet";//退单
    public static final String deleteUserPeriodBetBatch = "/api/periodbet/deleteUserPeriodBetBatch";//退单
    public static final String getUserPeriodBet = "/api/periodbet/getUserPeriodBet";//获取用户下单记录



}
