/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : dynamic_thread_pool_ctl

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 05/07/2020 17:01:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm_rule
-- ----------------------------
DROP TABLE IF EXISTS `alarm_rule`;
CREATE TABLE `alarm_rule` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '报警名称',
  `app_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '应用名称',
  `pool_name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '线程池名称',
  `task_completion` decimal(10,2) DEFAULT NULL COMMENT '线程池活跃度',
  `queue_usage` decimal(10,0) DEFAULT NULL COMMENT '队列使用情况',
  `content` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '报警内容(支持通配符匹配)',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='报警规则表';

-- ----------------------------
-- Records of alarm_rule
-- ----------------------------
BEGIN;
INSERT INTO `alarm_rule` VALUES (1, '测试规则', 'client-example', 'printThread', 0.10, 0, 'test', 0, '2020-07-05 12:47:47', '2020-07-05 12:47:51');
COMMIT;

-- ----------------------------
-- Table structure for expansion_rule
-- ----------------------------
DROP TABLE IF EXISTS `expansion_rule`;
CREATE TABLE `expansion_rule` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `alarm_rule_id` int DEFAULT NULL COMMENT '绑定一个报警规则',
  `core_size` int DEFAULT NULL COMMENT '扩容后核心线程数',
  `max_size` int DEFAULT NULL COMMENT '扩容后最大线程数',
  `queue_size` int DEFAULT NULL COMMENT '扩容后队列大小',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='扩容规则';

-- ----------------------------
-- Records of expansion_rule
-- ----------------------------
BEGIN;
INSERT INTO `expansion_rule` VALUES (1, 1, 10, 20, 1024, 0, '2020-07-05 12:50:20', '2020-07-05 12:50:24');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
