package com.dcode.qixin.core;

/**
 * 项目常量
 */
public final class ProjectConstant {
//    public static final String BASE_PACKAGE = "com.company.project";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
//    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//生成的Model所在包
//    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
//    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
//    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
//    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//生成的Controller所在包
//    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名

    public static final int LEVEL_ADMIN = 0;      //超级管理员
    public static final int LEVEL_GUDONG = 1;     //股东
    public static final int LEVEL_ZHONGDAI = 2;   //总代理
    public static final int LEVEL_DAILI= 3;       //代理
    public static final int LEVEL_HUIYUAN = 4;    //会员
    public static final int LEVEL_KF = 5;    //顾客

    public static final int PERIOD_RESULT_ZHONGJIANG = 1;
    public static final int PERIOD_RESULT_WEIZHONG = 2;

    public static final int PERIOD_STATUS_BEGIN = 0;    //
    public static final int PERIOD_STATUS_ING = 1;    //正在销售
    public static final int PERIOD_STATUS_CLOSE = 2;    //封盘
    public static final int PERIOD_STATUS_OUT = 3;    //已开奖
    public static final int PERIOD_STATUS_END = 4;    //结束


    public static final String CLASS_2P = "2p";  //二定位
    public static final String CLASS_3P = "3p";  //三定位
    public static final String CLASS_4P = "4p";  //四定位
    public static final String CLASS_2PX = "2px";  //二字现
    public static final String CLASS_3PX = "3px";  //三字现
    public static final String CLASS_4PX = "4px";  //四字现


}
