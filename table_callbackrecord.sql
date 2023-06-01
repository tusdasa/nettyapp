/*
Navicat MySQL Data Transfer

Source Server         : xampp
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : db_callback

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2023-06-02 00:18:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for table_callbackrecord
-- ----------------------------
DROP TABLE IF EXISTS `table_callbackrecord`;
CREATE TABLE `table_callbackrecord` (
  `id` bigint(23) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `req_header` text DEFAULT NULL,
  `req_body` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of table_callbackrecord
-- ----------------------------
