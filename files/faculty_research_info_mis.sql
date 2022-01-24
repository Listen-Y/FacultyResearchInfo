/*
MySQL Data Transfer

Source Server         : root
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : faculty_research_info_mis

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2022-01-24 17:48:59
*/
DROP DATABASE IF EXISTS `faculty_research_info_mis`;
CREATE DATABASE `faculty_research_info_mis`;
USE `faculty_research_info_mis`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `job_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `job_basic_info`;
CREATE TABLE `job_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `qualification_name` varchar(10) NOT NULL COMMENT '任职资格名称',
  `access_qualification` varchar(30) NOT NULL COMMENT '取得资格途径',
  `review_unit` varchar(30) NOT NULL COMMENT '评审单位',
  `evaluation_date` date NOT NULL COMMENT '评定日期',
  `appointment` varchar(20) NOT NULL COMMENT '聘任职务',
  `employing_unit` varchar(40) NOT NULL COMMENT '聘任单位',
  `date_appointment` date NOT NULL COMMENT '聘任日期',
  `employment` varchar(10) NOT NULL COMMENT '聘任情况',
  `date_dismissal` date NOT NULL COMMENT '解聘日期',
  `reason_dismissal` varchar(60) NOT NULL COMMENT '解聘原因',
  `teacher_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `job_basic_info_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专业技术职务信息类';

-- ----------------------------
-- Records of job_basic_info
-- ----------------------------

-- ----------------------------
-- Table structure for `patent_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `patent_basic_info`;
CREATE TABLE `patent_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '专利名称',
  `number` varchar(20) NOT NULL COMMENT '专利号',
  `applicant` varchar(60) NOT NULL COMMENT '申请单位',
  `date` date NOT NULL COMMENT '批准日期',
  `role` varchar(20) NOT NULL COMMENT '专利角色',
  `teacher_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL,
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `patent_basic_info_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专利信息类';

-- ----------------------------
-- Records of patent_basic_info
-- ----------------------------

-- ----------------------------
-- Table structure for `project_achievement_info`
-- ----------------------------
DROP TABLE IF EXISTS `project_achievement_info`;
CREATE TABLE `project_achievement_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '成果名称',
  `type` varchar(20) NOT NULL COMMENT '成果类型',
  `level` varchar(10) NOT NULL COMMENT '成果水平',
  `appraisal_unit` varchar(60) NOT NULL COMMENT '鉴定单位',
  `project_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `project_achievement_info_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科研项目成果情况';

-- ----------------------------
-- Records of project_achievement_info
-- ----------------------------

-- ----------------------------
-- Table structure for `project_award_info`
-- ----------------------------
DROP TABLE IF EXISTS `project_award_info`;
CREATE TABLE `project_award_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '获奖项目名称',
  `date` date NOT NULL COMMENT '获奖日期',
  `award_name` varchar(20) NOT NULL COMMENT '奖励名称',
  `role` varchar(10) NOT NULL COMMENT '获奖角色',
  `awarding_unit` varchar(60) NOT NULL COMMENT '授奖单位',
  `project_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `project_award_info_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科研项目获奖情况';

-- ----------------------------
-- Records of project_award_info
-- ----------------------------

-- ----------------------------
-- Table structure for `project_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `project_basic_info`;
CREATE TABLE `project_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(40) NOT NULL COMMENT '项目名称',
  `project_discipline` varchar(10) NOT NULL COMMENT '项目学科领域',
  `category` varchar(20) NOT NULL COMMENT '项目类别',
  `type` varchar(20) NOT NULL COMMENT '项目类型',
  `project_issuing_unit` varchar(20) NOT NULL COMMENT '项目下达单位',
  `funds` decimal(10,3) NOT NULL COMMENT '项目经费',
  `source_funds` varchar(30) NOT NULL COMMENT '项目经费来源',
  `start_date` date NOT NULL COMMENT '项目起始年月',
  `end_date` date NOT NULL COMMENT '项目终止年月',
  `role` varchar(20) NOT NULL COMMENT '项目角色',
  `Identification_conclusion` varchar(20) NOT NULL COMMENT '鉴定结论',
  `appraisal_unit` varchar(60) NOT NULL COMMENT '鉴定单位',
  `research_workload` varchar(3) DEFAULT NULL COMMENT '科研工作量',
  `development_effort` varchar(3) DEFAULT NULL COMMENT '开发工作量',
  `teacher_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `project_basic_info_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科研项目基本情况';

-- ----------------------------
-- Records of project_basic_info
-- ----------------------------

-- ----------------------------
-- Table structure for `reward_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `reward_basic_info`;
CREATE TABLE `reward_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(60) NOT NULL COMMENT '获奖项目',
  `level` varchar(20) NOT NULL COMMENT '奖励级别',
  `type` varchar(20) NOT NULL COMMENT '获奖类别',
  `way` varchar(30) NOT NULL COMMENT '奖励方式',
  `name` varchar(20) NOT NULL COMMENT '奖励名称',
  `awarding_unit` varchar(60) NOT NULL COMMENT '颁奖单位',
  `date` date NOT NULL COMMENT '颁奖日期',
  `honorary_title` varchar(30) NOT NULL COMMENT '荣誉称号码',
  `honor_date` date NOT NULL COMMENT '获荣誉日期',
  `role` varchar(20) NOT NULL COMMENT '获奖角色',
  `teacher_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `reward_basic_info_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励信息类';

-- ----------------------------
-- Records of reward_basic_info
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_basic_info`;
CREATE TABLE `teacher_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `teacher_id` varchar(8) NOT NULL COMMENT '职工号',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `english_name` varchar(60) NOT NULL COMMENT '姓名拼音',
  `old_name` varchar(30) NOT NULL COMMENT '曾用名',
  `id_card` char(18) NOT NULL COMMENT '身份证号',
  `birthday` date NOT NULL COMMENT '出生日期',
  `sex` varchar(5) NOT NULL COMMENT '性别',
  `nation` varchar(10) NOT NULL COMMENT '民族',
  `healthy` varchar(5) NOT NULL COMMENT '健康状况',
  `blood` varchar(2) NOT NULL COMMENT '血型',
  `belief` varchar(10) NOT NULL COMMENT '宗教信仰',
  `overseas` varchar(10) NOT NULL COMMENT '港澳台侨',
  `marital` varchar(5) NOT NULL COMMENT '婚姻状况',
  `native` varchar(10) NOT NULL COMMENT '籍贯',
  `birth_place` varchar(20) NOT NULL COMMENT '出生地',
  `family_origin` varchar(10) NOT NULL COMMENT '家庭出身',
  `composition` varchar(10) NOT NULL COMMENT '本人成分',
  `home_address` varchar(30) NOT NULL COMMENT '家庭住址',
  `current_address` varchar(30) NOT NULL COMMENT '现住址',
  `registered_residence` varchar(30) NOT NULL COMMENT '户口所在地',
  `account_nature` varchar(10) NOT NULL COMMENT '户口性质',
  `education_level` varchar(10) NOT NULL COMMENT '文化程度',
  `date_work` date NOT NULL COMMENT '参加工作年月',
  `date_work_park` date NOT NULL COMMENT '来园年月',
  `teacher_date` date NOT NULL COMMENT '从教年月',
  `faculty_category` varchar(10) NOT NULL COMMENT '教职工类别',
  `teaching_status` varchar(10) NOT NULL COMMENT '任课状况',
  `file_number` char(10) NOT NULL COMMENT '档案编号',
  `file_text` varchar(20) DEFAULT NULL COMMENT '档案文本',
  `photo_url` varchar(30) NOT NULL COMMENT '照片',
  `mailing_address` varchar(60) NOT NULL COMMENT '通信地址',
  `phone` char(11) NOT NULL COMMENT '联系电话',
  `postal_code` char(6) NOT NULL COMMENT '邮政编码',
  `email` varchar(30) NOT NULL COMMENT '电子信箱',
  `page_address` varchar(60) NOT NULL COMMENT '主页地址',
  `specialty` varchar(60) DEFAULT NULL COMMENT '特长',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师基本信息表';

-- ----------------------------
-- Records of teacher_basic_info
-- ----------------------------

-- ----------------------------
-- Table structure for `treatise_basic_info`
-- ----------------------------
DROP TABLE IF EXISTS `treatise_basic_info`;
CREATE TABLE `treatise_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(40) NOT NULL COMMENT '论著名称',
  `type` varchar(10) NOT NULL COMMENT '论著类别',
  `way` varchar(20) NOT NULL COMMENT '论著发表方式',
  `origin` varchar(20) NOT NULL COMMENT '论著出处',
  `publication_number` varchar(30) NOT NULL COMMENT '出版物号',
  `date` date NOT NULL COMMENT '发表年月',
  `role` varchar(20) NOT NULL COMMENT '论著角色',
  `teacher_id` int(11) NOT NULL COMMENT '外键',
  `create_date` date NOT NULL COMMENT '创建时间',
  `update_date` date NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `treatise_basic_info_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_basic_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论著信息类';

-- ----------------------------
-- Records of treatise_basic_info
-- ----------------------------
