/*
MySQL Backup
Source Server Version: 8.0.11
Source Database: qixin
Date: 2018-09-16 09:28:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `qx_bet_type_sum`
-- ----------------------------
DROP TABLE IF EXISTS `qx_bet_type_sum`;
CREATE TABLE `qx_bet_type_sum` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `period_id` int(11) NOT NULL COMMENT '期号',
  `play_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'type',
  `play_sum` int(11) DEFAULT NULL COMMENT '数量，单位分',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`),
  UNIQUE KEY `index_qx_bet_type_sum` (`period_id`,`play_type`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_bet_user_sum`
-- ----------------------------
DROP TABLE IF EXISTS `qx_bet_user_sum`;
CREATE TABLE `qx_bet_user_sum` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '下注会员id',
  `period_id` int(11) NOT NULL COMMENT '期号',
  `play_sum` int(11) DEFAULT NULL COMMENT '鏁伴噺锛屽崟浣嶅垎',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`),
  UNIQUE KEY `index_bet_user_sum` (`user_id`,`period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_period`
-- ----------------------------
DROP TABLE IF EXISTS `qx_period`;
CREATE TABLE `qx_period` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) NOT NULL COMMENT '期号',
  `p_time` timestamp NOT NULL COMMENT '瀵偓婵傛牗妞傞梻?',
  `p1` smallint(6) DEFAULT NULL COMMENT '球1',
  `p2` smallint(6) DEFAULT NULL COMMENT '球2',
  `p3` smallint(6) DEFAULT NULL COMMENT '球3',
  `p4` smallint(6) DEFAULT NULL COMMENT '球4',
  `p5` smallint(6) DEFAULT NULL COMMENT '球5',
  `p6` smallint(6) DEFAULT NULL COMMENT '球6',
  `p7` smallint(6) DEFAULT NULL COMMENT '球7',
  `status` smallint(6) DEFAULT '0' COMMENT '状态，1：开始销售； 2：停止销售',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_period_bet`
-- ----------------------------
DROP TABLE IF EXISTS `qx_period_bet`;
CREATE TABLE `qx_period_bet` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '下注会员id',
  `period_id` int(11) NOT NULL COMMENT '期号',
  `play_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'type',
  `play_class` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'class',
  `play_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '号码',
  `play_money` int(11) DEFAULT NULL COMMENT '数额',
  `play_odds` decimal(10,3) DEFAULT '0.000' COMMENT '赔率',
  `deleted` smallint(6) DEFAULT '0',
  `period_result` int(11) DEFAULT '0' COMMENT '开奖结果：0.未开,1.中奖，2.没中',
  `rake_gd` int(11) DEFAULT '0' COMMENT '股东水',
  `rake_zd` int(11) DEFAULT '0' COMMENT '总代水',
  `rake_dl` int(11) DEFAULT '0' COMMENT '代理水',
  `rake_hy` int(11) DEFAULT '0' COMMENT '会员水',
  `pay_kf` int(11) DEFAULT '0' COMMENT '中奖赔数',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6535 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_period_count`
-- ----------------------------
DROP TABLE IF EXISTS `qx_period_count`;
CREATE TABLE `qx_period_count` (
  `period_id` int(11) NOT NULL COMMENT '期号',
  `gd_id` int(11) DEFAULT '0' COMMENT '股东id',
  `zd_id` int(11) DEFAULT '0' COMMENT '总代id',
  `dl_id` int(11) DEFAULT '0' COMMENT '代理id',
  `hy_id` int(11) NOT NULL COMMENT '下注会员id',
  `play_money` int(11) DEFAULT NULL COMMENT '数额',
  `rake_gd` int(11) DEFAULT '0' COMMENT '股东水,单位厘',
  `rake_zd` int(11) DEFAULT '0' COMMENT '总代水,单位厘',
  `rake_dl` int(11) DEFAULT '0' COMMENT '代理水,单位厘',
  `rake_hy` int(11) DEFAULT '0' COMMENT '会员水,单位厘',
  `pay_kf` int(11) DEFAULT '0' COMMENT '中奖赔数,单位分',
  `status` int(11) DEFAULT '0' COMMENT 'status',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`period_id`,`hy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_period_odds`
-- ----------------------------
DROP TABLE IF EXISTS `qx_period_odds`;
CREATE TABLE `qx_period_odds` (
  `period_id` int(11) NOT NULL,
  `play_class` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `p_value` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '需要调整赔率的号码',
  `odds_new` decimal(10,3) DEFAULT '0.000' COMMENT '新设置的赔率',
  `happen_sum` int(11) NOT NULL DEFAULT '0' COMMENT '鏂拌禂鐜囪Е鍙戠殑闄愬€?',
  `comment` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `o_id` int(11) DEFAULT '0',
  `eneabled` smallint(6) DEFAULT '1',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`period_id`,`play_class`,`p_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_period_stop`
-- ----------------------------
DROP TABLE IF EXISTS `qx_period_stop`;
CREATE TABLE `qx_period_stop` (
  `period_id` int(11) NOT NULL,
  `p_value` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '需要停止的号码',
  `comment` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `deleted` smallint(6) DEFAULT '0',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`period_id`,`p_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_roles`
-- ----------------------------
DROP TABLE IF EXISTS `qx_roles`;
CREATE TABLE `qx_roles` (
  `sys_id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色代码',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `deleted` smallint(6) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_users`
-- ----------------------------
DROP TABLE IF EXISTS `qx_users`;
CREATE TABLE `qx_users` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `pw_err_count` smallint(6) DEFAULT '0',
  `nickname` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '代号',
  `role_id` int(11) DEFAULT NULL,
  `token` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `token_expire` bigint(20) DEFAULT NULL,
  `level` smallint(6) DEFAULT '0',
  `create_user` int(11) DEFAULT '0' COMMENT '创建用户',
  `credit_limit` int(11) DEFAULT '0' COMMENT '信用额度',
  `credit_limit_left` int(11) DEFAULT '0',
  `deleted` smallint(6) DEFAULT '0',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `eneabled` smallint(6) DEFAULT '1',
  PRIMARY KEY (`sys_id`),
  UNIQUE KEY `index_qx_users_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_user_odds`
-- ----------------------------
DROP TABLE IF EXISTS `qx_user_odds`;
CREATE TABLE `qx_user_odds` (
  `user_id` int(11) NOT NULL,
  `play_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `play_type_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `play_type_class` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `min_play` int(11) DEFAULT '1' COMMENT '最小下注，单位为分',
  `max_play` int(11) DEFAULT '1' COMMENT '单注上限， 单位分',
  `max_class_play` int(11) DEFAULT '1' COMMENT '单项上限， 单位分',
  `user_id_pt` int(11) DEFAULT NULL COMMENT '平台用户id',
  `user_id_gd` int(11) DEFAULT NULL COMMENT '股东用户id',
  `user_id_zd` int(11) DEFAULT NULL COMMENT '总代用户id',
  `user_id_dl` int(11) DEFAULT NULL COMMENT '代理用户id',
  `user_id_hy` int(11) DEFAULT NULL COMMENT '会员用户id',
  `odds_pt` decimal(10,3) DEFAULT '0.000' COMMENT '平台赔率',
  `odds_gd` decimal(10,3) DEFAULT '0.000' COMMENT '股东赔率',
  `odds_zd` decimal(10,3) DEFAULT '0.000' COMMENT '总代赔率',
  `odds_dl` decimal(10,3) DEFAULT '0.000' COMMENT '代理赔率',
  `odds_hy` decimal(10,3) DEFAULT '0.000' COMMENT '会员赔率',
  `odds_kf` decimal(10,3) DEFAULT '0.000' COMMENT '瀹㈡埛璧旂巼',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `o_id` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`,`play_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户赔率表';

-- ----------------------------
--  Table structure for `qx_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `qx_user_role`;
CREATE TABLE `qx_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qx_version_app`
-- ----------------------------
DROP TABLE IF EXISTS `qx_version_app`;
CREATE TABLE `qx_version_app` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `version_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `file_url` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `is_constraint` int(11) DEFAULT '0',
  `update_log` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `deleted` int(6) NOT NULL DEFAULT '0',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records 
-- ----------------------------