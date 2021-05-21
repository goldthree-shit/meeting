/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.28-log : Database - meeting
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`meeting` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `meeting`;


DROP TABLE IF EXISTS `meeting_file`;

CREATE TABLE `meeting_file` (
  `mfid` int(11) NOT NULL AUTO_INCREMENT,
  `meid` int(11) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL,
  `file_name` varchar(20) DEFAULT NULL,
  `destPath` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT '1',
  `deleted` int(11) DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`mfid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `meeting_recoding`;

CREATE TABLE `meeting_recoding` (
  `mreid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `meid` int(11) DEFAULT NULL COMMENT '对应的会议id',
  `meeting_name` varchar(20) DEFAULT NULL COMMENT '会议名称',
  `meeting_time` varchar(30) DEFAULT NULL COMMENT '会议时间',
  `location` varchar(30) DEFAULT NULL COMMENT '会议室地点',
  `participants` varchar(20) DEFAULT NULL COMMENT '参会人员',
  `absence_person` varchar(50) DEFAULT NULL COMMENT '缺席人员',
  `meeting_content` varchar(200) DEFAULT NULL COMMENT '会议记录',
  `version` int(11) DEFAULT '1',
  `deleted` int(11) DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`mreid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `meeting_room`;

CREATE TABLE `meeting_room` (
  `mrid` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议室id',
  `user_group` varchar(20) DEFAULT NULL COMMENT '会议室对应的公司',
  `room_name` varchar(25) DEFAULT NULL COMMENT '会议室名称',
  `location` varchar(30) DEFAULT NULL COMMENT '会议室位置',
  `floor` varchar(5) DEFAULT NULL COMMENT '会议室楼层',
  `plate_num` varchar(10) DEFAULT NULL COMMENT '会议室门牌号',
  `equipment` varchar(21) DEFAULT NULL COMMENT '会议室设备',
  `equipment_state` varchar(1) DEFAULT NULL COMMENT '会议室设备状态',
  `capacity` int(11) DEFAULT NULL COMMENT '会议室设备状态',
  `state` varchar(1) DEFAULT NULL COMMENT '会议室状态',
  `description` varchar(30) DEFAULT NULL COMMENT '会议室描述',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mrid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `meetings`;

CREATE TABLE `meetings` (
  `meid` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议id',
  `mrid` int(11) DEFAULT NULL,
  `detailed_location` varchar(50) DEFAULT NULL COMMENT '会议室具体位置',
  `room_name` varchar(20) DEFAULT NULL COMMENT '所用会议室名称',
  `meeting_name` varchar(20) DEFAULT NULL COMMENT '会议名称',
  `person_num` int(11) DEFAULT NULL COMMENT '预计人数',
  `username` varchar(25) DEFAULT NULL COMMENT '会议发起人',
  `user_group` varchar(20) DEFAULT NULL COMMENT '公司名称',
  `start_time` datetime DEFAULT NULL COMMENT '会议开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '会议结束时间',
  `meeting_description` varchar(100) DEFAULT NULL COMMENT '会议描述',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`meid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `participants`;

CREATE TABLE `participants` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `meid` int(11) DEFAULT NULL COMMENT '对应的会议id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(25) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `is_admin` tinyint(1) DEFAULT NULL COMMENT '是管理员',
  `user_group` varchar(20) DEFAULT NULL COMMENT '公司名称',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

