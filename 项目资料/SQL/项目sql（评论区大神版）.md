## 使用时自行修改sql

* 1

```mysql
CREATE DATABASE `jxmall_admin`;

USE `jmall_admin`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000172687412E87874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'LAPTOP-1IOHSLBR1594385595491', 1594389562774, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1594389600000, 1594387800000, 5, 'WAITING', 'CRON', 1590919162000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000172687412E87874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000A66697265666C796E617974000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'testTask', 'fireflynay', '0 0/30 * * * ?', 0, '参数测试', '2020-05-31 09:58:57');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 308 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES (1, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 18:00:00');
INSERT INTO `schedule_job_log` VALUES (2, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 18:30:00');
INSERT INTO `schedule_job_log` VALUES (3, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 19:00:00');
INSERT INTO `schedule_job_log` VALUES (4, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 19:30:00');
INSERT INTO `schedule_job_log` VALUES (5, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 20:00:00');
INSERT INTO `schedule_job_log` VALUES (6, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 20:30:00');
INSERT INTO `schedule_job_log` VALUES (7, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 21:00:00');
INSERT INTO `schedule_job_log` VALUES (8, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 21:30:00');
INSERT INTO `schedule_job_log` VALUES (9, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 22:00:00');
INSERT INTO `schedule_job_log` VALUES (10, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 22:30:00');
INSERT INTO `schedule_job_log` VALUES (11, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 23:00:00');
INSERT INTO `schedule_job_log` VALUES (12, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 23:30:00');
INSERT INTO `schedule_job_log` VALUES (13, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 10:30:00');
INSERT INTO `schedule_job_log` VALUES (14, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 11:00:00');
INSERT INTO `schedule_job_log` VALUES (15, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 11:30:00');
INSERT INTO `schedule_job_log` VALUES (16, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 12:00:00');
INSERT INTO `schedule_job_log` VALUES (17, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 12:30:00');
INSERT INTO `schedule_job_log` VALUES (18, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 13:00:00');
INSERT INTO `schedule_job_log` VALUES (19, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 13:30:00');
INSERT INTO `schedule_job_log` VALUES (20, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 14:00:00');
INSERT INTO `schedule_job_log` VALUES (21, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 14:30:00');
INSERT INTO `schedule_job_log` VALUES (22, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 15:00:00');
INSERT INTO `schedule_job_log` VALUES (23, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 15:30:00');
INSERT INTO `schedule_job_log` VALUES (24, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 16:00:00');
INSERT INTO `schedule_job_log` VALUES (25, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 16:30:00');
INSERT INTO `schedule_job_log` VALUES (26, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 17:00:00');
INSERT INTO `schedule_job_log` VALUES (27, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 17:30:00');
INSERT INTO `schedule_job_log` VALUES (28, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 18:00:00');
INSERT INTO `schedule_job_log` VALUES (29, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 18:30:00');
INSERT INTO `schedule_job_log` VALUES (30, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 19:00:00');
INSERT INTO `schedule_job_log` VALUES (31, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 19:30:00');
INSERT INTO `schedule_job_log` VALUES (32, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 20:00:00');
INSERT INTO `schedule_job_log` VALUES (33, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 20:30:00');
INSERT INTO `schedule_job_log` VALUES (34, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 21:00:00');
INSERT INTO `schedule_job_log` VALUES (35, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 21:30:00');
INSERT INTO `schedule_job_log` VALUES (36, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 22:00:00');
INSERT INTO `schedule_job_log` VALUES (37, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 23:00:00');
INSERT INTO `schedule_job_log` VALUES (38, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 23:30:00');
INSERT INTO `schedule_job_log` VALUES (39, 1, 'testTask', 'renren', 0, NULL, 6, '2020-06-02 00:00:00');
INSERT INTO `schedule_job_log` VALUES (40, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES (41, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES (42, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES (43, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES (44, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES (45, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES (46, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES (47, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES (48, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES (49, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 15:30:00');
INSERT INTO `schedule_job_log` VALUES (50, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES (51, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES (52, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES (53, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 17:30:00');
INSERT INTO `schedule_job_log` VALUES (54, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 18:00:00');
INSERT INTO `schedule_job_log` VALUES (55, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 18:30:00');
INSERT INTO `schedule_job_log` VALUES (56, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES (57, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 20:00:00');
INSERT INTO `schedule_job_log` VALUES (58, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES (59, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES (60, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES (61, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES (62, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES (63, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES (64, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 23:30:00');
INSERT INTO `schedule_job_log` VALUES (65, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 15:00:00');
INSERT INTO `schedule_job_log` VALUES (66, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 15:30:00');
INSERT INTO `schedule_job_log` VALUES (67, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 16:00:00');
INSERT INTO `schedule_job_log` VALUES (68, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 16:30:00');
INSERT INTO `schedule_job_log` VALUES (69, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 17:00:00');
INSERT INTO `schedule_job_log` VALUES (70, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 17:30:00');
INSERT INTO `schedule_job_log` VALUES (71, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 18:00:00');
INSERT INTO `schedule_job_log` VALUES (72, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 18:30:00');
INSERT INTO `schedule_job_log` VALUES (73, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES (74, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES (75, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES (76, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES (77, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 21:00:00');
INSERT INTO `schedule_job_log` VALUES (78, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 21:30:00');
INSERT INTO `schedule_job_log` VALUES (79, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 22:00:00');
INSERT INTO `schedule_job_log` VALUES (80, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 23:00:00');
INSERT INTO `schedule_job_log` VALUES (81, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 23:30:00');
INSERT INTO `schedule_job_log` VALUES (82, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES (83, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES (84, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES (85, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES (86, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES (87, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 15:30:00');
INSERT INTO `schedule_job_log` VALUES (88, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 16:00:00');
INSERT INTO `schedule_job_log` VALUES (89, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 16:30:00');
INSERT INTO `schedule_job_log` VALUES (90, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 17:00:00');
INSERT INTO `schedule_job_log` VALUES (91, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 17:30:00');
INSERT INTO `schedule_job_log` VALUES (92, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 18:00:00');
INSERT INTO `schedule_job_log` VALUES (93, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 18:30:00');
INSERT INTO `schedule_job_log` VALUES (94, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 19:30:00');
INSERT INTO `schedule_job_log` VALUES (95, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 20:00:00');
INSERT INTO `schedule_job_log` VALUES (96, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 20:30:00');
INSERT INTO `schedule_job_log` VALUES (97, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 21:00:00');
INSERT INTO `schedule_job_log` VALUES (98, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 21:30:00');
INSERT INTO `schedule_job_log` VALUES (99, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 22:00:00');
INSERT INTO `schedule_job_log` VALUES (100, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 22:30:00');
INSERT INTO `schedule_job_log` VALUES (101, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 23:00:00');
INSERT INTO `schedule_job_log` VALUES (102, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 23:30:00');
INSERT INTO `schedule_job_log` VALUES (103, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 11:00:00');
INSERT INTO `schedule_job_log` VALUES (104, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 11:30:00');
INSERT INTO `schedule_job_log` VALUES (105, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 12:00:00');
INSERT INTO `schedule_job_log` VALUES (106, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 12:30:00');
INSERT INTO `schedule_job_log` VALUES (107, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 13:00:00');
INSERT INTO `schedule_job_log` VALUES (108, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 13:30:00');
INSERT INTO `schedule_job_log` VALUES (109, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 14:00:00');
INSERT INTO `schedule_job_log` VALUES (110, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 14:30:00');
INSERT INTO `schedule_job_log` VALUES (111, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 15:00:00');
INSERT INTO `schedule_job_log` VALUES (112, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 15:30:00');
INSERT INTO `schedule_job_log` VALUES (113, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 16:00:00');
INSERT INTO `schedule_job_log` VALUES (114, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 16:30:00');
INSERT INTO `schedule_job_log` VALUES (115, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 17:00:00');
INSERT INTO `schedule_job_log` VALUES (116, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 17:30:00');
INSERT INTO `schedule_job_log` VALUES (117, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 18:00:00');
INSERT INTO `schedule_job_log` VALUES (118, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 18:30:00');
INSERT INTO `schedule_job_log` VALUES (119, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 19:00:00');
INSERT INTO `schedule_job_log` VALUES (120, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 19:30:00');
INSERT INTO `schedule_job_log` VALUES (121, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 20:00:00');
INSERT INTO `schedule_job_log` VALUES (122, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 20:30:00');
INSERT INTO `schedule_job_log` VALUES (123, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 21:00:00');
INSERT INTO `schedule_job_log` VALUES (124, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 21:30:00');
INSERT INTO `schedule_job_log` VALUES (125, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 22:30:00');
INSERT INTO `schedule_job_log` VALUES (126, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 23:00:00');
INSERT INTO `schedule_job_log` VALUES (127, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 23:30:00');
INSERT INTO `schedule_job_log` VALUES (128, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 11:00:00');
INSERT INTO `schedule_job_log` VALUES (129, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 11:30:00');
INSERT INTO `schedule_job_log` VALUES (130, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 12:00:00');
INSERT INTO `schedule_job_log` VALUES (131, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 12:30:00');
INSERT INTO `schedule_job_log` VALUES (132, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 13:00:00');
INSERT INTO `schedule_job_log` VALUES (133, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 19:00:00');
INSERT INTO `schedule_job_log` VALUES (134, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES (135, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES (136, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES (137, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES (138, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES (139, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES (140, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES (141, 1, 'testTask', 'fireflynay', 0, NULL, 7, '2020-06-07 00:00:00');
INSERT INTO `schedule_job_log` VALUES (142, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 00:30:00');
INSERT INTO `schedule_job_log` VALUES (143, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 01:00:00');
INSERT INTO `schedule_job_log` VALUES (144, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 10:30:00');
INSERT INTO `schedule_job_log` VALUES (145, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 11:00:00');
INSERT INTO `schedule_job_log` VALUES (146, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 11:30:00');
INSERT INTO `schedule_job_log` VALUES (147, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES (148, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 12:30:00');
INSERT INTO `schedule_job_log` VALUES (149, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES (150, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES (151, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES (152, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 14:30:00');
INSERT INTO `schedule_job_log` VALUES (153, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 15:00:00');
INSERT INTO `schedule_job_log` VALUES (154, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 15:30:00');
INSERT INTO `schedule_job_log` VALUES (155, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 16:00:00');
INSERT INTO `schedule_job_log` VALUES (156, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 16:30:00');
INSERT INTO `schedule_job_log` VALUES (157, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 17:00:00');
INSERT INTO `schedule_job_log` VALUES (158, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES (159, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES (160, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 19:30:00');
INSERT INTO `schedule_job_log` VALUES (161, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 20:00:00');
INSERT INTO `schedule_job_log` VALUES (162, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 20:30:00');
INSERT INTO `schedule_job_log` VALUES (163, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 21:00:00');
INSERT INTO `schedule_job_log` VALUES (164, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 21:30:00');
INSERT INTO `schedule_job_log` VALUES (165, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 22:00:00');
INSERT INTO `schedule_job_log` VALUES (166, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 22:30:00');
INSERT INTO `schedule_job_log` VALUES (167, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 23:00:00');
INSERT INTO `schedule_job_log` VALUES (168, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 17:00:00');
INSERT INTO `schedule_job_log` VALUES (169, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 17:30:00');
INSERT INTO `schedule_job_log` VALUES (170, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 18:00:00');
INSERT INTO `schedule_job_log` VALUES (171, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 18:30:00');
INSERT INTO `schedule_job_log` VALUES (172, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 19:00:00');
INSERT INTO `schedule_job_log` VALUES (173, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 19:30:00');
INSERT INTO `schedule_job_log` VALUES (174, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 20:00:00');
INSERT INTO `schedule_job_log` VALUES (175, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 20:30:00');
INSERT INTO `schedule_job_log` VALUES (176, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 21:00:00');
INSERT INTO `schedule_job_log` VALUES (177, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 21:30:00');
INSERT INTO `schedule_job_log` VALUES (178, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 22:00:00');
INSERT INTO `schedule_job_log` VALUES (179, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 22:30:00');
INSERT INTO `schedule_job_log` VALUES (180, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 23:00:00');
INSERT INTO `schedule_job_log` VALUES (181, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 23:30:00');
INSERT INTO `schedule_job_log` VALUES (182, 1, 'testTask', 'fireflynay', 0, NULL, 5, '2020-06-09 00:00:00');
INSERT INTO `schedule_job_log` VALUES (183, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 00:30:00');
INSERT INTO `schedule_job_log` VALUES (184, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 01:00:00');
INSERT INTO `schedule_job_log` VALUES (185, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (186, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (187, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (188, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (189, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (190, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (191, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (192, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (193, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (194, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (195, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (196, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (197, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (198, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES (199, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES (200, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES (201, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES (202, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES (203, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES (204, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (205, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (206, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (207, 1, 'testTask', 'fireflynay', 0, NULL, 5, '2020-06-10 00:00:00');
INSERT INTO `schedule_job_log` VALUES (208, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES (209, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 18:30:00');
INSERT INTO `schedule_job_log` VALUES (210, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 19:00:00');
INSERT INTO `schedule_job_log` VALUES (211, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-12 19:30:00');
INSERT INTO `schedule_job_log` VALUES (212, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 20:00:00');
INSERT INTO `schedule_job_log` VALUES (213, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 20:30:00');
INSERT INTO `schedule_job_log` VALUES (214, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 21:00:00');
INSERT INTO `schedule_job_log` VALUES (215, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-12 21:30:00');
INSERT INTO `schedule_job_log` VALUES (216, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 22:00:00');
INSERT INTO `schedule_job_log` VALUES (217, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 22:30:00');
INSERT INTO `schedule_job_log` VALUES (218, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 23:30:00');
INSERT INTO `schedule_job_log` VALUES (219, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 11:30:00');
INSERT INTO `schedule_job_log` VALUES (220, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 12:30:00');
INSERT INTO `schedule_job_log` VALUES (221, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 13:00:00');
INSERT INTO `schedule_job_log` VALUES (222, 1, 'testTask', 'fireflynay', 0, NULL, 3, '2020-06-15 00:00:00');
INSERT INTO `schedule_job_log` VALUES (223, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-15 10:30:00');
INSERT INTO `schedule_job_log` VALUES (224, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-15 11:00:00');
INSERT INTO `schedule_job_log` VALUES (225, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-15 11:30:00');
INSERT INTO `schedule_job_log` VALUES (226, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES (227, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES (228, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES (229, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES (230, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES (231, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES (232, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES (233, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 17:00:00');
INSERT INTO `schedule_job_log` VALUES (234, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES (235, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES (236, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES (237, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES (238, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 19:30:00');
INSERT INTO `schedule_job_log` VALUES (239, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 20:30:00');
INSERT INTO `schedule_job_log` VALUES (240, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 21:00:00');
INSERT INTO `schedule_job_log` VALUES (241, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 21:30:00');
INSERT INTO `schedule_job_log` VALUES (242, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 22:00:00');
INSERT INTO `schedule_job_log` VALUES (243, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 22:30:00');
INSERT INTO `schedule_job_log` VALUES (244, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 23:00:00');
INSERT INTO `schedule_job_log` VALUES (245, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 23:30:00');
INSERT INTO `schedule_job_log` VALUES (246, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES (247, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES (248, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 15:30:00');
INSERT INTO `schedule_job_log` VALUES (249, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 16:00:00');
INSERT INTO `schedule_job_log` VALUES (250, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 16:30:00');
INSERT INTO `schedule_job_log` VALUES (251, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 17:00:00');
INSERT INTO `schedule_job_log` VALUES (252, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 17:30:00');
INSERT INTO `schedule_job_log` VALUES (253, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 18:00:00');
INSERT INTO `schedule_job_log` VALUES (254, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 18:30:00');
INSERT INTO `schedule_job_log` VALUES (255, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 19:00:00');
INSERT INTO `schedule_job_log` VALUES (256, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES (257, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES (258, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES (259, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES (260, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES (261, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES (262, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 22:30:00');
INSERT INTO `schedule_job_log` VALUES (263, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 23:00:00');
INSERT INTO `schedule_job_log` VALUES (264, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES (265, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (266, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (267, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (268, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (269, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (270, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (271, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (272, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (273, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (274, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (275, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (276, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (277, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (278, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES (279, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES (280, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES (281, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES (282, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES (283, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES (284, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (285, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (286, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (287, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES (288, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 12:00:00');
INSERT INTO `schedule_job_log` VALUES (289, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 12:30:00');
INSERT INTO `schedule_job_log` VALUES (290, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES (291, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES (292, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 14:00:00');
INSERT INTO `schedule_job_log` VALUES (293, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 14:30:00');
INSERT INTO `schedule_job_log` VALUES (294, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 15:00:00');
INSERT INTO `schedule_job_log` VALUES (295, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 15:30:00');
INSERT INTO `schedule_job_log` VALUES (296, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES (297, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 16:30:00');
INSERT INTO `schedule_job_log` VALUES (298, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 17:00:00');
INSERT INTO `schedule_job_log` VALUES (299, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 17:30:00');
INSERT INTO `schedule_job_log` VALUES (300, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 18:00:00');
INSERT INTO `schedule_job_log` VALUES (301, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 18:30:00');
INSERT INTO `schedule_job_log` VALUES (302, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES (303, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 19:30:00');
INSERT INTO `schedule_job_log` VALUES (304, 1, 'testTask', 'fireflynay', 0, NULL, 2, '2020-07-10 20:00:00');
INSERT INTO `schedule_job_log` VALUES (305, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 20:30:00');
INSERT INTO `schedule_job_log` VALUES (306, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 21:00:00');
INSERT INTO `schedule_job_log` VALUES (307, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 21:30:00');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('027ea200-df77-4fd4-8eea-cf78f7fb2023', 'pwexg', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('03324fa8-36af-439e-840b-30fcad65f6b1', 'e568f', '2020-05-31 19:33:35');
INSERT INTO `sys_captcha` VALUES ('0c159600-76da-49c7-8313-872d6d2802b7', '7wyxw', '2020-06-04 12:52:51');
INSERT INTO `sys_captcha` VALUES ('1341f11a-9044-4a17-8e10-6200c1a6c4f9', '3wym7', '2020-05-31 19:54:24');
INSERT INTO `sys_captcha` VALUES ('142702cc-b370-4ee3-83de-9c10cdefe938', 'wwdng', '2020-06-23 15:56:11');
INSERT INTO `sys_captcha` VALUES ('14fd5939-5b26-4489-8d56-c46a2e4b28f9', 'xwnp2', '2020-06-23 15:34:57');
INSERT INTO `sys_captcha` VALUES ('1b10ff7f-d12e-4381-8cc9-79febd353970', '7n7gm', '2020-05-31 19:33:27');
INSERT INTO `sys_captcha` VALUES ('1d406841-28f0-4ad0-8042-07417a62e1fd', 'c8axn', '2020-06-04 18:34:15');
INSERT INTO `sys_captcha` VALUES ('294b1c97-f8df-4244-88c4-47141c8d56af', 'bgcyw', '2020-07-09 13:18:19');
INSERT INTO `sys_captcha` VALUES ('3c82fc6b-31f5-48a5-83a4-ceca5f598179', '6w52n', '2020-06-23 15:56:14');
INSERT INTO `sys_captcha` VALUES ('42f6f840-fc40-4e8a-8528-b79a4a2b8f2a', '654ed', '2020-06-04 18:34:33');
INSERT INTO `sys_captcha` VALUES ('43cd5131-d40b-4a0e-8ba8-f7f68df6f849', '4dem4', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('55779b39-e638-4a2f-8462-c20297531925', 'x3bpb', '2020-06-02 22:50:47');
INSERT INTO `sys_captcha` VALUES ('640dbcfb-f3cd-421c-8d81-eeac7b11bb87', '27n4g', '2020-05-31 19:55:20');
INSERT INTO `sys_captcha` VALUES ('73031619-e44a-40f7-8ac8-baf960e8df6d', 'gcawe', '2020-06-04 12:55:50');
INSERT INTO `sys_captcha` VALUES ('737d045c-d156-46f9-899a-83aec5846c69', 'gdaxy', '2020-06-22 13:39:52');
INSERT INTO `sys_captcha` VALUES ('7bdfb155-9e27-446c-8e0d-89bc1ff519ed', '54dfb', '2020-05-31 20:27:33');
INSERT INTO `sys_captcha` VALUES ('9b2990ef-98c2-4865-83d3-c04286488498', 'npec2', '2020-06-03 15:05:49');
INSERT INTO `sys_captcha` VALUES ('a0c259cd-9a98-4e6f-893b-f0d203803548', 'xbm2n', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('a3ae36dd-0cc3-434c-870e-692785911564', 'c664y', '2020-06-13 12:31:03');
INSERT INTO `sys_captcha` VALUES ('a6819c8e-3e7e-44de-8721-0db9d915fbf1', '7n246', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('ac99ae8c-ec94-4c23-8cce-550570983503', 'fe7en', '2020-06-04 18:34:46');
INSERT INTO `sys_captcha` VALUES ('b8ffa0df-2964-4929-8cec-b7c20f3117fe', 'y6e73', '2020-05-31 18:04:52');
INSERT INTO `sys_captcha` VALUES ('bc588733-8369-4fe0-84d3-ec536a3304a4', 'n5cbf', '2020-05-31 19:53:44');
INSERT INTO `sys_captcha` VALUES ('c2b49735-63aa-4d13-8a8f-f3f325e94f38', 'dbmb8', '2020-06-03 15:05:42');
INSERT INTO `sys_captcha` VALUES ('c79c06bf-1724-4c16-8172-b78106fb86bf', 'f8db4', '2020-06-13 11:06:43');
INSERT INTO `sys_captcha` VALUES ('dd3ab546-a671-4fcc-8b9c-11c0d6254b72', '8g5f6', '2020-06-23 13:39:27');
INSERT INTO `sys_captcha` VALUES ('f4c57fb0-a2cb-4355-8198-3d4ba215c575', 'ancc5', '2020-06-12 18:30:33');
INSERT INTO `sys_captcha` VALUES ('f69bd42b-4f2a-4d96-8176-6701e3621b13', '6g237', '2020-05-31 19:55:17');
INSERT INTO `sys_captcha` VALUES ('ff8267ba-eb4b-49f1-8fe5-62188f93b8cb', 'abxcn', '2020-06-09 00:30:58');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"zhedie\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2020-05-31 18:33:19');
INSERT INTO `sys_log` VALUES (2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 15, '0:0:0:0:0:0:0:1', '2020-05-31 18:34:36');
INSERT INTO `sys_log` VALUES (3, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"system\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2020-06-01 14:23:02');
INSERT INTO `sys_log` VALUES (4, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":2,\"username\":\"fireflynay\",\"password\":\"f35b148a296ded29376259f9e7e57650a606be557973c67e7e6807a612d3fdfd\",\"salt\":\"18lAY9Gul41yGzBkSTpT\",\"email\":\"1046762075s@gmail.com\",\"mobile\":\"18156475879\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Jun 6, 2020 11:17:16 PM\"}]', 127, '0:0:0:0:0:0:0:1', '2020-06-06 23:17:16');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (31, 0, '商品系统', '', '', 0, 'editor', 0);
INSERT INTO `sys_menu` VALUES (32, 31, '分类维护', 'product/category', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (37, 31, '平台属性', '', '', 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (38, 37, '属性分组', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (39, 37, '规格参数', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (40, 37, '销售属性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (41, 31, '商品维护', 'product/spu', '', 0, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (42, 0, '优惠营销', '', '', 0, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (43, 0, '库存系统', '', '', 0, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (44, 0, '订单系统', '', '', 0, 'config', 0);
INSERT INTO `sys_menu` VALUES (45, 0, '用户系统', '', '', 0, 'admin', 0);
INSERT INTO `sys_menu` VALUES (46, 0, '内容管理', '', '', 0, 'sousuo', 0);
INSERT INTO `sys_menu` VALUES (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0);
INSERT INTO `sys_menu` VALUES (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0);
INSERT INTO `sys_menu` VALUES (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0);
INSERT INTO `sys_menu` VALUES (53, 43, '仓库维护', 'ware/wareinfo', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (54, 43, '库存工作单', 'ware/task', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (55, 43, '商品库存', 'ware/sku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` VALUES (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` VALUES (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0);
INSERT INTO `sys_menu` VALUES (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` VALUES (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0);
INSERT INTO `sys_menu` VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0);
INSERT INTO `sys_menu` VALUES (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (70, 43, '采购单维护', '', '', 0, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (71, 70, '采购需求', 'ware/purchaseitem', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (72, 70, '采购单', 'ware/purchase', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0);
INSERT INTO `sys_menu` VALUES (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '1046762075@qq.com', '18173516309', 1, 1, '2020-05-20 05:20:00');
INSERT INTO `sys_user` VALUES (2, 'fireflynay', 'f35b148a296ded29376259f9e7e57650a606be557973c67e7e6807a612d3fdfd', '18lAY9Gul41yGzBkSTpT', '1046762075s@gmail.com', '18156475879', 1, 1, '2020-06-06 23:17:16');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1, 'db8c608f439fda16db8551f485ff1790', '2020-07-10 01:13:50', '2020-07-09 13:13:50');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

* JXmall_oms.sql

```mysql
CREATE DATABASE `jxmall_oms`;

USE `jxmall_oms`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`  (
  `message_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'JSON',
  `to_exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `class_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_status` int(1) NULL DEFAULT 0 COMMENT '0-新建 1-已发送 2-错误抵达 3-已抵达',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1, 3, '202007101511435951281486241929375746', NULL, '2020-07-10 07:11:44', 'firenayfly', 8888.0000, 8893.0000, 5.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 0, 0, NULL, NULL, NULL, NULL, 'xxx@gmail.com', 'firenayfly', '15421564125', NULL, '陕西', '西安', NULL, '新城区', NULL, NULL, 0, NULL, NULL, '2020-07-10 07:11:44', '2020-07-10 07:11:44', '2020-07-10 07:11:44', '2020-07-10 07:11:44');
INSERT INTO `oms_order` VALUES (2, 3, '202007102108315951281576033585246209', NULL, '2020-07-10 13:08:32', 'firenayfly', 8888.0000, 8893.0000, 5.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 0, 0, NULL, NULL, NULL, NULL, 'xxx@gmail.com', 'firenayfly', '15421564125', NULL, '陕西', '西安', NULL, '新城区', NULL, NULL, 0, NULL, NULL, '2020-07-10 13:08:32', '2020-07-10 13:08:32', '2020-07-10 13:08:32', '2020-07-10 13:08:32');

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单项信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1, 1, '202007101511435951281486241929375746', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (2, 2, '202007102108315951281576033585246209', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单退货申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '退货原因' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime(0) NULL DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `alipay_trade_no`(`alipay_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------
INSERT INTO `oms_payment_info` VALUES (1, '202007051323559261279647175235624962', NULL, '2020070522001473990501031834', 5990.0000, 'glmall', 'TRADE_SUCCESS', '2020-07-05 05:24:06', NULL, NULL, '2020-07-05 05:24:21');

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '退款信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

* JXmall_pms.sql

```mysql
CREATE DATABASE `jxmall_pms`;

USE `jxmall_pms`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attr
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr`;
CREATE TABLE `pms_attr`  (
  `attr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `attr_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性名',
  `search_type` tinyint(4) NULL DEFAULT NULL COMMENT '是否需要检索[0-不需要，1-需要]',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性图标',
  `value_select` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '可选值列表[用逗号分隔]',
  `attr_type` tinyint(4) NULL DEFAULT NULL COMMENT '属性类型[0-销售属性，1-基本属性',
  `enable` bigint(20) NULL DEFAULT NULL COMMENT '启用状态[0 - 禁用，1 - 启用]',
  `catelog_id` bigint(20) NULL DEFAULT NULL COMMENT '所属分类',
  `show_desc` tinyint(4) NULL DEFAULT NULL COMMENT '快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整',
  PRIMARY KEY (`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品属性' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attr
-- ----------------------------
INSERT INTO `pms_attr` VALUES (1, '入网参数', 1, 'xxx', '4G;5G', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (2, '上市年份', 1, 'xxx', '2019;2020;2021', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (3, '颜色', 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', '极光蓝;钛空银;霓影紫;流光幻镜', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (4, '颜色', 0, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', '极光蓝;钛空银;霓影紫;流光幻镜', 0, 1, 225, 0);
INSERT INTO `pms_attr` VALUES (5, '内存', 0, 'xxx', '4G;6G;8G;12G', 0, 1, 225, 0);
INSERT INTO `pms_attr` VALUES (6, '套餐', 0, 'xxx', '套餐一;套餐二;套餐三', 0, 1, 225, 0);
INSERT INTO `pms_attr` VALUES (7, 'CPU型号', 1, 'xxx', '麒麟990;麒麟820', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (8, 'CPU工艺', 1, 'xxx', '7nm;5nm;3nm', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (9, '电池容量', 1, 'xxx', '3500mAh;4000mAh;5000mAh', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (10, '机身长度(mm)', 1, 'xxx', '168;155;172', 1, 1, 225, 1);
INSERT INTO `pms_attr` VALUES (11, '版本', 0, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_%E5%9B%BE%E9%9B%861.jpg', '8GB+128GB;8GB+256GB;12GB+256GB', 0, 1, 225, 0);
INSERT INTO `pms_attr` VALUES (12, 'CPU品牌', 1, 'xxx', '海思(Hisilicon);高通(Qualcomm)', 1, 1, 225, 1);

-- ----------------------------
-- Table structure for pms_attr_attrgroup_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_attrgroup_relation`;
CREATE TABLE `pms_attr_attrgroup_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT '属性id',
  `attr_group_id` bigint(20) NULL DEFAULT NULL COMMENT '属性分组id',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '属性组内排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '属性&属性分组关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attr_attrgroup_relation
-- ----------------------------
INSERT INTO `pms_attr_attrgroup_relation` VALUES (6, 3, 1, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (7, 2, 1, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (8, 1, 2, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (9, 7, 4, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (10, 8, 4, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (11, 9, 2, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (12, 10, 2, 0);
INSERT INTO `pms_attr_attrgroup_relation` VALUES (13, 12, 4, 0);

-- ----------------------------
-- Table structure for pms_attr_group
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_group`;
CREATE TABLE `pms_attr_group`  (
  `attr_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `attr_group_name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组图标',
  `catelog_id` bigint(20) NULL DEFAULT NULL COMMENT '所属分类id',
  PRIMARY KEY (`attr_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '属性分组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attr_group
-- ----------------------------
INSERT INTO `pms_attr_group` VALUES (1, '信息', 0, '荣耀', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', 225);
INSERT INTO `pms_attr_group` VALUES (2, '基本信息', 0, '华为', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', 225);
INSERT INTO `pms_attr_group` VALUES (3, '香奈儿', 0, 'fire', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', 637);
INSERT INTO `pms_attr_group` VALUES (4, '芯片', 0, 'CPU型号', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/1.jpg', 225);

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品牌名',
  `logo` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品牌logo地址',
  `descript` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '介绍',
  `show_status` tinyint(4) NULL DEFAULT NULL COMMENT '显示状态[0-不显示；1-显示]',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '检索首字母',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`brand_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '品牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '荣耀', '', '优秀', 1, 'r', 0);
INSERT INTO `pms_brand` VALUES (2, '华为', '', '华为', 1, 'h', 0);
INSERT INTO `pms_brand` VALUES (3, '小米', '', '小米', 1, 'm', 0);
INSERT INTO `pms_brand` VALUES (4, 'oppo', '', 'oppo', 1, 'o', 0);
INSERT INTO `pms_brand` VALUES (5, 'viov', '', 'viov', 1, 'v', 0);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `parent_cid` bigint(20) NULL DEFAULT NULL COMMENT '父分类id',
  `cat_level` int(11) NULL DEFAULT NULL COMMENT '层级',
  `show_status` tinyint(4) NULL DEFAULT NULL COMMENT '是否显示[0-不显示，1显示]',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `icon` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标地址',
  `product_unit` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位',
  `product_count` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`cat_id`) USING BTREE,
  INDEX `parent_cid`(`parent_cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1437 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品三级分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1, '图书、音像、电子书刊', 0, 1, 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (2, '手机', 0, 1, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (3, '家用电器', 0, 1, 1, 2, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (4, '数码', 0, 1, 1, 3, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (5, '家居家装', 0, 1, 1, 4, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (6, '电脑办公', 0, 1, 1, 5, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (7, '厨具', 0, 1, 1, 6, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (8, '个护化妆', 0, 1, 1, 7, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (9, '服饰内衣', 0, 1, 1, 8, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (10, '钟表', 0, 1, 1, 9, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (11, '鞋靴', 0, 1, 1, 10, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (12, '母婴', 0, 1, 1, 11, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (13, '礼品箱包', 0, 1, 1, 12, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (14, '食品饮料、保健食品', 0, 1, 1, 13, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (15, '珠宝', 0, 1, 1, 14, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (16, '汽车用品', 0, 1, 1, 15, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (17, '运动健康', 0, 1, 1, 16, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (18, '玩具乐器', 0, 1, 1, 17, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (19, '彩票、旅行、充值、票务', 0, 1, 1, 18, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (20, '生鲜', 0, 1, 1, 19, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (21, '整车', 0, 1, 1, 20, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (22, '电子书刊', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (23, '音像', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (24, '英文原版', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (25, '文艺', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (26, '少儿', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (27, '人文社科', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (28, '经管励志', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (29, '生活', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (30, '科技', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (31, '教育', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (32, '港台图书', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (33, '其他', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (34, '手机通讯', 2, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (35, '运营商', 2, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (36, '手机配件', 2, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (37, '大 家 电', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (38, '厨卫大电', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (39, '厨房小电', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (40, '生活电器', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (41, '个护健康', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (42, '五金家装', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (43, '摄影摄像', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (44, '数码配件', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (45, '智能设备', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (46, '影音娱乐', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (47, '电子教育', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (48, '虚拟商品', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (49, '家纺', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (50, '灯具', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (51, '生活日用', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (52, '家装软饰', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (53, '宠物生活', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (54, '电脑整机', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (55, '电脑配件', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (56, '外设产品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (57, '游戏设备', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (58, '网络产品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (59, '办公设备', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (60, '文具/耗材', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (61, '服务产品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (62, '烹饪锅具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (63, '刀剪菜板', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (64, '厨房配件', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (65, '水具酒具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (66, '餐具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (67, '酒店用品', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (68, '茶具/咖啡具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (69, '清洁用品', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (70, '面部护肤', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (71, '身体护理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (72, '口腔护理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (73, '女性护理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (74, '洗发护发', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (75, '香水彩妆', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (76, '女装', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (77, '男装', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (78, '内衣', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (79, '洗衣服务', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (80, '服饰配件', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (81, '钟表', 10, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (82, '流行男鞋', 11, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (83, '时尚女鞋', 11, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (84, '奶粉', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (85, '营养辅食', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (86, '尿裤湿巾', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (87, '喂养用品', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (88, '洗护用品', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (89, '童车童床', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (90, '寝居服饰', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (91, '妈妈专区', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (92, '童装童鞋', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (93, '安全座椅', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (94, '潮流女包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (95, '精品男包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (96, '功能箱包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (97, '礼品', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (98, '奢侈品', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (99, '婚庆', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (100, '进口食品', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (101, '地方特产', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (102, '休闲食品', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (103, '粮油调味', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (104, '饮料冲调', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (105, '食品礼券', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (106, '茗茶', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (107, '时尚饰品', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (108, '黄金', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (109, 'K金饰品', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (110, '金银投资', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (111, '银饰', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (112, '钻石', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (113, '翡翠玉石', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (114, '水晶玛瑙', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (115, '彩宝', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (116, '铂金', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (117, '木手串/把件', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (118, '珍珠', 15, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (119, '维修保养', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (120, '车载电器', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (121, '美容清洗', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (122, '汽车装饰', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (123, '安全自驾', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (124, '汽车服务', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (125, '赛事改装', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (126, '运动鞋包', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (127, '运动服饰', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (128, '骑行运动', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (129, '垂钓用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (130, '游泳用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (131, '户外鞋服', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (132, '户外装备', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (133, '健身训练', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (134, '体育用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (135, '适用年龄', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (136, '遥控/电动', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (137, '毛绒布艺', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (138, '娃娃玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (139, '模型玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (140, '健身玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (141, '动漫玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (142, '益智玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (143, '积木拼插', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (144, 'DIY玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (145, '创意减压', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (146, '乐器', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (147, '彩票', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (148, '机票', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (149, '酒店', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (150, '旅行', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (151, '充值', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (152, '游戏', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (153, '票务', 19, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (154, '产地直供', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (155, '水果', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (156, '猪牛羊肉', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (157, '海鲜水产', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (158, '禽肉蛋品', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (159, '冷冻食品', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (160, '熟食腊味', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (161, '饮品甜品', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (162, '蔬菜', 20, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (163, '全新整车', 21, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (164, '二手车', 21, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (165, '电子书', 22, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (166, '网络原创', 22, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (167, '数字杂志', 22, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (168, '多媒体图书', 22, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (169, '音乐', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (170, '影视', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (171, '教育音像', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (172, '少儿', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (173, '商务投资', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (174, '英语学习与考试', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (175, '文学', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (176, '传记', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (177, '励志', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (178, '小说', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (179, '文学', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (180, '青春文学', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (181, '传记', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (182, '艺术', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (183, '少儿', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (184, '0-2岁', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (185, '3-6岁', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (186, '7-10岁', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (187, '11-14岁', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (188, '历史', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (189, '哲学', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (190, '国学', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (191, '政治/军事', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (192, '法律', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (193, '人文社科', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (194, '心理学', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (195, '文化', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (196, '社会科学', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (197, '经济', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (198, '金融与投资', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (199, '管理', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (200, '励志与成功', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (201, '生活', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (202, '健身与保健', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (203, '家庭与育儿', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (204, '旅游', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (205, '烹饪美食', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (206, '工业技术', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (207, '科普读物', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (208, '建筑', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (209, '医学', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (210, '科学与自然', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (211, '计算机与互联网', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (212, '电子通信', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (213, '中小学教辅', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (214, '教育与考试', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (215, '外语学习', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (216, '大中专教材', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (217, '字典词典', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (218, '艺术/设计/收藏', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (219, '经济管理', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (220, '文化/学术', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (221, '少儿', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (222, '工具书', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (223, '杂志/期刊', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (224, '套装书', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (225, '手机', 34, 3, 1, 0, '', NULL, 0);
INSERT INTO `pms_category` VALUES (226, '对讲机', 34, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (227, '合约机', 35, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (228, '选号中心', 35, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (229, '装宽带', 35, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (230, '办套餐', 35, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (231, '移动电源', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (232, '电池/移动电源', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (233, '蓝牙耳机', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (234, '充电器/数据线', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (235, '苹果周边', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (236, '手机耳机', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (237, '手机贴膜', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (238, '手机存储卡', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (239, '充电器', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (240, '数据线', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (241, '手机保护套', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (242, '车载配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (243, 'iPhone 配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (244, '手机电池', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (245, '创意配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (246, '便携/无线音响', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (247, '手机饰品', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (248, '拍照配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (249, '手机支架', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (250, '平板电视', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (251, '空调', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (252, '冰箱', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (253, '洗衣机', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (254, '家庭影院', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (255, 'DVD/电视盒子', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (256, '迷你音响', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (257, '冷柜/冰吧', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (258, '家电配件', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (259, '功放', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (260, '回音壁/Soundbar', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (261, 'Hi-Fi专区', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (262, '电视盒子', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (263, '酒柜', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (264, '燃气灶', 38, 3, 0, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (265, '油烟机', 38, 3, 0, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (266, '热水器', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (267, '消毒柜', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (268, '洗碗机', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (269, '料理机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (270, '榨汁机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (271, '电饭煲', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (272, '电压力锅', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (273, '豆浆机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (274, '咖啡机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (275, '微波炉', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (276, '电烤箱', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (277, '电磁炉', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (278, '面包机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (279, '煮蛋器', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (280, '酸奶机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (281, '电炖锅', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (282, '电水壶/热水瓶', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (283, '电饼铛', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (284, '多用途锅', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (285, '电烧烤炉', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (286, '果蔬解毒机', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (287, '其它厨房电器', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (288, '养生壶/煎药壶', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (289, '电热饭盒', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (290, '取暖电器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (291, '净化器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (292, '加湿器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (293, '扫地机器人', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (294, '吸尘器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (295, '挂烫机/熨斗', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (296, '插座', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (297, '电话机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (298, '清洁机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (299, '除湿机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (300, '干衣机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (301, '收录/音机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (302, '电风扇', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (303, '冷风扇', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (304, '其它生活电器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (305, '生活电器配件', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (306, '净水器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (307, '饮水机', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (308, '剃须刀', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (309, '剃/脱毛器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (310, '口腔护理', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (311, '电吹风', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (312, '美容器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (313, '理发器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (314, '卷/直发器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (315, '按摩椅', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (316, '按摩器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (317, '足浴盆', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (318, '血压计', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (319, '电子秤/厨房秤', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (320, '血糖仪', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (321, '体温计', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (322, '其它健康电器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (323, '计步器/脂肪检测仪', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (324, '电动工具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (325, '手动工具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (326, '仪器仪表', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (327, '浴霸/排气扇', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (328, '灯具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (329, 'LED灯', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (330, '洁身器', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (331, '水槽', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (332, '龙头', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (333, '淋浴花洒', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (334, '厨卫五金', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (335, '家具五金', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (336, '门铃', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (337, '电气开关', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (338, '插座', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (339, '电工电料', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (340, '监控安防', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (341, '电线/线缆', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (342, '数码相机', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (343, '单电/微单相机', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (344, '单反相机', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (345, '摄像机', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (346, '拍立得', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (347, '运动相机', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (348, '镜头', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (349, '户外器材', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (350, '影棚器材', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (351, '冲印服务', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (352, '数码相框', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (353, '存储卡', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (354, '读卡器', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (355, '滤镜', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (356, '闪光灯/手柄', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (357, '相机包', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (358, '三脚架/云台', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (359, '相机清洁/贴膜', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (360, '机身附件', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (361, '镜头附件', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (362, '电池/充电器', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (363, '移动电源', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (364, '数码支架', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (365, '智能手环', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (366, '智能手表', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (367, '智能眼镜', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (368, '运动跟踪器', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (369, '健康监测', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (370, '智能配饰', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (371, '智能家居', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (372, '体感车', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (373, '其他配件', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (374, '智能机器人', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (375, '无人机', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (376, 'MP3/MP4', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (377, '智能设备', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (378, '耳机/耳麦', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (379, '便携/无线音箱', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (380, '音箱/音响', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (381, '高清播放器', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (382, '收音机', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (383, 'MP3/MP4配件', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (384, '麦克风', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (385, '专业音频', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (386, '苹果配件', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (387, '学生平板', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (388, '点读机/笔', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (389, '早教益智', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (390, '录音笔', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (391, '电纸书', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (392, '电子词典', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (393, '复读机', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (394, '延保服务', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (395, '杀毒软件', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (396, '积分商品', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (397, '桌布/罩件', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (398, '地毯地垫', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (399, '沙发垫套/椅垫', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (400, '床品套件', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (401, '被子', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (402, '枕芯', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (403, '床单被罩', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (404, '毯子', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (405, '床垫/床褥', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (406, '蚊帐', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (407, '抱枕靠垫', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (408, '毛巾浴巾', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (409, '电热毯', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (410, '窗帘/窗纱', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (411, '布艺软饰', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (412, '凉席', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (413, '台灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (414, '节能灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (415, '装饰灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (416, '落地灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (417, '应急灯/手电', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (418, 'LED灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (419, '吸顶灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (420, '五金电器', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (421, '筒灯射灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (422, '吊灯', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (423, '氛围照明', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (424, '保暖防护', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (425, '收纳用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (426, '雨伞雨具', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (427, '浴室用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (428, '缝纫/针织用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (429, '洗晒/熨烫', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (430, '净化除味', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (431, '相框/照片墙', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (432, '装饰字画', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (433, '节庆饰品', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (434, '手工/十字绣', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (435, '装饰摆件', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (436, '帘艺隔断', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (437, '墙贴/装饰贴', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (438, '钟饰', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (439, '花瓶花艺', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (440, '香薰蜡烛', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (441, '创意家居', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (442, '宠物主粮', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (443, '宠物零食', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (444, '医疗保健', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (445, '家居日用', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (446, '宠物玩具', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (447, '出行装备', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (448, '洗护美容', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (449, '笔记本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (450, '超极本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (451, '游戏本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (452, '平板电脑', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (453, '平板电脑配件', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (454, '台式机', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (455, '服务器/工作站', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (456, '笔记本配件', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (457, '一体机', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (458, 'CPU', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (459, '主板', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (460, '显卡', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (461, '硬盘', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (462, 'SSD固态硬盘', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (463, '内存', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (464, '机箱', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (465, '电源', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (466, '显示器', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (467, '刻录机/光驱', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (468, '散热器', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (469, '声卡/扩展卡', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (470, '装机配件', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (471, '组装电脑', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (472, '移动硬盘', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (473, 'U盘', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (474, '鼠标', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (475, '键盘', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (476, '鼠标垫', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (477, '摄像头', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (478, '手写板', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (479, '硬盘盒', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (480, '插座', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (481, '线缆', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (482, 'UPS电源', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (483, '电脑工具', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (484, '游戏设备', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (485, '电玩', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (486, '电脑清洁', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (487, '网络仪表仪器', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (488, '游戏机', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (489, '游戏耳机', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (490, '手柄/方向盘', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (491, '游戏软件', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (492, '游戏周边', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (493, '路由器', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (494, '网卡', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (495, '交换机', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (496, '网络存储', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (497, '4G/3G上网', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (498, '网络盒子', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (499, '网络配件', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (500, '投影机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (501, '投影配件', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (502, '多功能一体机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (503, '打印机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (504, '传真设备', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (505, '验钞/点钞机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (506, '扫描设备', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (507, '复合机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (508, '碎纸机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (509, '考勤机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (510, '收款/POS机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (511, '会议音频视频', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (512, '保险柜', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (513, '装订/封装机', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (514, '安防监控', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (515, '办公家具', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (516, '白板', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (517, '硒鼓/墨粉', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (518, '墨盒', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (519, '色带', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (520, '纸类', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (521, '办公文具', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (522, '学生文具', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (523, '财会用品', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (524, '文件管理', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (525, '本册/便签', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (526, '计算器', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (527, '笔类', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (528, '画具画材', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (529, '刻录碟片/附件', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (530, '上门安装', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (531, '延保服务', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (532, '维修保养', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (533, '电脑软件', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (534, '京东服务', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (535, '炒锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (536, '煎锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (537, '压力锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (538, '蒸锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (539, '汤锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (540, '奶锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (541, '锅具套装', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (542, '煲类', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (543, '水壶', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (544, '火锅', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (545, '菜刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (546, '剪刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (547, '刀具套装', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (548, '砧板', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (549, '瓜果刀/刨', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (550, '多功能刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (551, '保鲜盒', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (552, '烘焙/烧烤', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (553, '饭盒/提锅', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (554, '储物/置物架', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (555, '厨房DIY/小工具', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (556, '塑料杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (557, '运动水壶', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (558, '玻璃杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (559, '陶瓷/马克杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (560, '保温杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (561, '保温壶', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (562, '酒杯/酒具', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (563, '杯具套装', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (564, '餐具套装', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (565, '碗/碟/盘', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (566, '筷勺/刀叉', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (567, '一次性用品', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (568, '果盘/果篮', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (569, '自助餐炉', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (570, '酒店餐具', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (571, '酒店水具', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (572, '整套茶具', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (573, '茶杯', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (574, '茶壶', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (575, '茶盘茶托', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (576, '茶叶罐', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (577, '茶具配件', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (578, '茶宠摆件', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (579, '咖啡具', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (580, '其他', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (581, '纸品湿巾', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (582, '衣物清洁', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (583, '清洁工具', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (584, '驱虫用品', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (585, '家庭清洁', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (586, '皮具护理', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (587, '一次性用品', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (588, '洁面', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (589, '乳液面霜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (590, '面膜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (591, '剃须', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (592, '套装', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (593, '精华', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (594, '眼霜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (595, '卸妆', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (596, '防晒', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (597, '防晒隔离', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (598, 'T区护理', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (599, '眼部护理', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (600, '精华露', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (601, '爽肤水', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (602, '沐浴', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (603, '润肤', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (604, '颈部', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (605, '手足', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (606, '纤体塑形', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (607, '美胸', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (608, '套装', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (609, '精油', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (610, '洗发护发', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (611, '染发/造型', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (612, '香薰精油', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (613, '磨砂/浴盐', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (614, '手工/香皂', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (615, '洗发', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (616, '护发', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (617, '染发', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (618, '磨砂膏', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (619, '香皂', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (620, '牙膏/牙粉', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (621, '牙刷/牙线', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (622, '漱口水', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (623, '套装', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (624, '卫生巾', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (625, '卫生护垫', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (626, '私密护理', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (627, '脱毛膏', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (628, '其他', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (629, '洗发', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (630, '护发', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (631, '染发', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (632, '造型', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (633, '假发', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (634, '套装', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (635, '美发工具', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (636, '脸部护理', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (637, '香水', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (638, '底妆', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (639, '腮红', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (640, '眼影', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (641, '唇部', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (642, '美甲', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (643, '眼线', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (644, '美妆工具', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (645, '套装', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (646, '防晒隔离', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (647, '卸妆', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (648, '眉笔', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (649, '睫毛膏', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (650, 'T恤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (651, '衬衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (652, '针织衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (653, '雪纺衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (654, '卫衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (655, '马甲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (656, '连衣裙', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (657, '半身裙', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (658, '牛仔裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (659, '休闲裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (660, '打底裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (661, '正装裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (662, '小西装', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (663, '短外套', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (664, '风衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (665, '毛呢大衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (666, '真皮皮衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (667, '棉服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (668, '羽绒服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (669, '大码女装', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (670, '中老年女装', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (671, '婚纱', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (672, '打底衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (673, '旗袍/唐装', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (674, '加绒裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (675, '吊带/背心', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (676, '羊绒衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (677, '短裤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (678, '皮草', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (679, '礼服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (680, '仿皮皮衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (681, '羊毛衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (682, '设计师/潮牌', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (683, '衬衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (684, 'T恤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (685, 'POLO衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (686, '针织衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (687, '羊绒衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (688, '卫衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (689, '马甲/背心', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (690, '夹克', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (691, '风衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (692, '毛呢大衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (693, '仿皮皮衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (694, '西服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (695, '棉服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (696, '羽绒服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (697, '牛仔裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (698, '休闲裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (699, '西裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (700, '西服套装', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (701, '大码男装', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (702, '中老年男装', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (703, '唐装/中山装', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (704, '工装', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (705, '真皮皮衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (706, '加绒裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (707, '卫裤/运动裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (708, '短裤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (709, '设计师/潮牌', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (710, '羊毛衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (711, '文胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (712, '女式内裤', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (713, '男式内裤', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (714, '睡衣/家居服', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (715, '塑身美体', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (716, '泳衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (717, '吊带/背心', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (718, '抹胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (719, '连裤袜/丝袜', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (720, '美腿袜', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (721, '商务男袜', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (722, '保暖内衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (723, '情侣睡衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (724, '文胸套装', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (725, '少女文胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (726, '休闲棉袜', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (727, '大码内衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (728, '内衣配件', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (729, '打底裤袜', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (730, '打底衫', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (731, '秋衣秋裤', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (732, '情趣内衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (733, '服装洗护', 79, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (734, '太阳镜', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (735, '光学镜架/镜片', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (736, '围巾/手套/帽子套装', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (737, '袖扣', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (738, '棒球帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (739, '毛线帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (740, '遮阳帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (741, '老花镜', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (742, '装饰眼镜', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (743, '防辐射眼镜', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (744, '游泳镜', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (745, '女士丝巾/围巾/披肩', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (746, '男士丝巾/围巾', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (747, '鸭舌帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (748, '贝雷帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (749, '礼帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (750, '真皮手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (751, '毛线手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (752, '防晒手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (753, '男士腰带/礼盒', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (754, '女士腰带/礼盒', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (755, '钥匙扣', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (756, '遮阳伞/雨伞', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (757, '口罩', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (758, '耳罩/耳包', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (759, '假领', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (760, '毛线/布面料', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (761, '领带/领结/领带夹', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (762, '男表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (763, '瑞表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (764, '女表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (765, '国表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (766, '日韩表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (767, '欧美表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (768, '德表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (769, '儿童手表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (770, '智能手表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (771, '闹钟', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (772, '座钟挂钟', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (773, '钟表配件', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (774, '商务休闲鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (775, '正装鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (776, '休闲鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (777, '凉鞋/沙滩鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (778, '男靴', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (779, '功能鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (780, '拖鞋/人字拖', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (781, '雨鞋/雨靴', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (782, '传统布鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (783, '鞋配件', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (784, '帆布鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (785, '增高鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (786, '工装鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (787, '定制鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (788, '高跟鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (789, '单鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (790, '休闲鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (791, '凉鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (792, '女靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (793, '雪地靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (794, '拖鞋/人字拖', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (795, '踝靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (796, '筒靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (797, '帆布鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (798, '雨鞋/雨靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (799, '妈妈鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (800, '鞋配件', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (801, '特色鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (802, '鱼嘴鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (803, '布鞋/绣花鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (804, '马丁靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (805, '坡跟鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (806, '松糕鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (807, '内增高', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (808, '防水台', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (809, '婴幼奶粉', 84, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (810, '孕妈奶粉', 84, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (811, '益生菌/初乳', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (812, '米粉/菜粉', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (813, '果泥/果汁', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (814, 'DHA', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (815, '宝宝零食', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (816, '钙铁锌/维生素', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (817, '清火/开胃', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (818, '面条/粥', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (819, '婴儿尿裤', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (820, '拉拉裤', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (821, '婴儿湿巾', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (822, '成人尿裤', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (823, '奶瓶奶嘴', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (824, '吸奶器', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (825, '暖奶消毒', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (826, '儿童餐具', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (827, '水壶/水杯', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (828, '牙胶安抚', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (829, '围兜/防溅衣', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (830, '辅食料理机', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (831, '食物存储', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (832, '宝宝护肤', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (833, '洗发沐浴', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (834, '奶瓶清洗', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (835, '驱蚊防晒', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (836, '理发器', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (837, '洗澡用具', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (838, '婴儿口腔清洁', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (839, '洗衣液/皂', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (840, '日常护理', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (841, '座便器', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (842, '婴儿推车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (843, '餐椅摇椅', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (844, '婴儿床', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (845, '学步车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (846, '三轮车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (847, '自行车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (848, '电动车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (849, '扭扭车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (850, '滑板车', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (851, '婴儿床垫', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (852, '婴儿外出服', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (853, '婴儿内衣', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (854, '婴儿礼盒', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (855, '婴儿鞋帽袜', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (856, '安全防护', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (857, '家居床品', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (858, '睡袋/抱被', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (859, '爬行垫', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (860, '妈咪包/背婴带', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (861, '产后塑身', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (862, '文胸/内裤', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (863, '防辐射服', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (864, '孕妈装', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (865, '孕期营养', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (866, '孕妇护肤', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (867, '待产护理', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (868, '月子装', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (869, '防溢乳垫', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (870, '套装', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (871, '上衣', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (872, '裤子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (873, '裙子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (874, '内衣/家居服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (875, '羽绒服/棉服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (876, '亲子装', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (877, '儿童配饰', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (878, '礼服/演出服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (879, '运动鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (880, '皮鞋/帆布鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (881, '靴子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (882, '凉鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (883, '功能鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (884, '户外/运动服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (885, '提篮式', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (886, '安全座椅', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (887, '增高垫', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (888, '钱包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (889, '手拿包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (890, '单肩包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (891, '双肩包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (892, '手提包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (893, '斜挎包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (894, '钥匙包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (895, '卡包/零钱包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (896, '男士钱包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (897, '男士手包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (898, '卡包名片夹', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (899, '商务公文包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (900, '双肩包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (901, '单肩/斜挎包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (902, '钥匙包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (903, '电脑包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (904, '拉杆箱', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (905, '旅行包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (906, '旅行配件', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (907, '休闲运动包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (908, '拉杆包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (909, '登山包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (910, '妈咪包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (911, '书包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (912, '相机包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (913, '腰包/胸包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (914, '火机烟具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (915, '礼品文具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (916, '军刀军具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (917, '收藏品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (918, '工艺礼品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (919, '创意礼品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (920, '礼盒礼券', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (921, '鲜花绿植', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (922, '婚庆节庆', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (923, '京东卡', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (924, '美妆礼品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (925, '礼品定制', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (926, '京东福卡', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (927, '古董文玩', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (928, '箱包', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (929, '钱包', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (930, '服饰', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (931, '腰带', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (932, '太阳镜/眼镜框', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (933, '配件', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (934, '鞋靴', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (935, '饰品', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (936, '名品腕表', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (937, '高档化妆品', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (938, '婚嫁首饰', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (939, '婚纱摄影', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (940, '婚纱礼服', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (941, '婚庆服务', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (942, '婚庆礼品/用品', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (943, '婚宴', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (944, '饼干蛋糕', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (945, '糖果/巧克力', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (946, '休闲零食', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (947, '冲调饮品', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (948, '粮油调味', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (949, '牛奶', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (950, '其他特产', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (951, '新疆', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (952, '北京', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (953, '山西', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (954, '内蒙古', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (955, '福建', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (956, '湖南', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (957, '四川', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (958, '云南', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (959, '东北', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (960, '休闲零食', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (961, '坚果炒货', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (962, '肉干肉脯', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (963, '蜜饯果干', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (964, '糖果/巧克力', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (965, '饼干蛋糕', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (966, '无糖食品', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (967, '米面杂粮', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (968, '食用油', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (969, '调味品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (970, '南北干货', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (971, '方便食品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (972, '有机食品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (973, '饮用水', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (974, '饮料', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (975, '牛奶乳品', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (976, '咖啡/奶茶', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (977, '冲饮谷物', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (978, '蜂蜜/柚子茶', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (979, '成人奶粉', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (980, '月饼', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (981, '大闸蟹', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (982, '粽子', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (983, '卡券', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (984, '铁观音', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (985, '普洱', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (986, '龙井', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (987, '绿茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (988, '红茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (989, '乌龙茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (990, '花草茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (991, '花果茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (992, '养生茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (993, '黑茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (994, '白茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (995, '其它茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (996, '项链', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (997, '手链/脚链', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (998, '戒指', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (999, '耳饰', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1000, '毛衣链', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1001, '发饰/发卡', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1002, '胸针', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1003, '饰品配件', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1004, '婚庆饰品', 107, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1005, '黄金吊坠', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1006, '黄金项链', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1007, '黄金转运珠', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1008, '黄金手镯/手链/脚链', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1009, '黄金耳饰', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1010, '黄金戒指', 108, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1011, 'K金吊坠', 109, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1012, 'K金项链', 109, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1013, 'K金手镯/手链/脚链', 109, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1014, 'K金戒指', 109, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1015, 'K金耳饰', 109, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1016, '投资金', 110, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1017, '投资银', 110, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1018, '投资收藏', 110, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1019, '银吊坠/项链', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1020, '银手镯/手链/脚链', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1021, '银戒指', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1022, '银耳饰', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1023, '足银手镯', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1024, '宝宝银饰', 111, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1025, '裸钻', 112, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1026, '钻戒', 112, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1027, '钻石项链/吊坠', 112, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1028, '钻石耳饰', 112, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1029, '钻石手镯/手链', 112, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1030, '项链/吊坠', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1031, '手镯/手串', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1032, '戒指', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1033, '耳饰', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1034, '挂件/摆件/把件', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1035, '玉石孤品', 113, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1036, '项链/吊坠', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1037, '耳饰', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1038, '手镯/手链/脚链', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1039, '戒指', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1040, '头饰/胸针', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1041, '摆件/挂件', 114, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1042, '琥珀/蜜蜡', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1043, '碧玺', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1044, '红宝石/蓝宝石', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1045, '坦桑石', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1046, '珊瑚', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1047, '祖母绿', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1048, '葡萄石', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1049, '其他天然宝石', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1050, '项链/吊坠', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1051, '耳饰', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1052, '手镯/手链', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1053, '戒指', 115, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1054, '铂金项链/吊坠', 116, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1055, '铂金手镯/手链/脚链', 116, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1056, '铂金戒指', 116, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1057, '铂金耳饰', 116, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1058, '小叶紫檀', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1059, '黄花梨', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1060, '沉香木', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1061, '金丝楠', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1062, '菩提', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1063, '其他', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1064, '橄榄核/核桃', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1065, '檀香', 117, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1066, '珍珠项链', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1067, '珍珠吊坠', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1068, '珍珠耳饰', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1069, '珍珠手链', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1070, '珍珠戒指', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1071, '珍珠胸针', 118, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1072, '机油', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1073, '正时皮带', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1074, '添加剂', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1075, '汽车喇叭', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1076, '防冻液', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1077, '汽车玻璃', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1078, '滤清器', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1079, '火花塞', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1080, '减震器', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1081, '柴机油/辅助油', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1082, '雨刷', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1083, '车灯', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1084, '后视镜', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1085, '轮胎', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1086, '轮毂', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1087, '刹车片/盘', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1088, '维修配件', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1089, '蓄电池', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1090, '底盘装甲/护板', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1091, '贴膜', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1092, '汽修工具', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1093, '改装配件', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1094, '导航仪', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1095, '安全预警仪', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1096, '行车记录仪', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1097, '倒车雷达', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1098, '蓝牙设备', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1099, '车载影音', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1100, '净化器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1101, '电源', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1102, '智能驾驶', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1103, '车载电台', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1104, '车载电器配件', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1105, '吸尘器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1106, '智能车机', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1107, '冰箱', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1108, '汽车音响', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1109, '车载生活电器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1110, '车蜡', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1111, '补漆笔', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1112, '玻璃水', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1113, '清洁剂', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1114, '洗车工具', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1115, '镀晶镀膜', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1116, '打蜡机', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1117, '洗车配件', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1118, '洗车机', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1119, '洗车水枪', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1120, '毛巾掸子', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1121, '脚垫', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1122, '座垫', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1123, '座套', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1124, '后备箱垫', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1125, '头枕腰靠', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1126, '方向盘套', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1127, '香水', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1128, '空气净化', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1129, '挂件摆件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1130, '功能小件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1131, '车身装饰件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1132, '车衣', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1133, '安全座椅', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1134, '胎压监测', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1135, '防盗设备', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1136, '应急救援', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1137, '保温箱', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1138, '地锁', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1139, '摩托车', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1140, '充气泵', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1141, '储物箱', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1142, '自驾野营', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1143, '摩托车装备', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1144, '清洗美容', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1145, '功能升级', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1146, '保养维修', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1147, '油卡充值', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1148, '车险', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1149, '加油卡', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1150, 'ETC', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1151, '驾驶培训', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1152, '赛事服装', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1153, '赛事用品', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1154, '制动系统', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1155, '悬挂系统', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1156, '进气系统', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1157, '排气系统', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1158, '电子管理', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1159, '车身强化', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1160, '赛事座椅', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1161, '跑步鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1162, '休闲鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1163, '篮球鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1164, '板鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1165, '帆布鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1166, '足球鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1167, '乒羽网鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1168, '专项运动鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1169, '训练鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1170, '拖鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1171, '运动包', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1172, '羽绒服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1173, '棉服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1174, '运动裤', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1175, '夹克/风衣', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1176, '卫衣/套头衫', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1177, 'T恤', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1178, '套装', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1179, '乒羽网服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1180, '健身服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1181, '运动背心', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1182, '毛衫/线衫', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1183, '运动配饰', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1184, '折叠车', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1185, '山地车/公路车', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1186, '电动车', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1187, '其他整车', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1188, '骑行服', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1189, '骑行装备', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1190, '平衡车', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1191, '鱼竿鱼线', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1192, '浮漂鱼饵', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1193, '钓鱼桌椅', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1194, '钓鱼配件', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1195, '钓箱鱼包', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1196, '其它', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1197, '泳镜', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1198, '泳帽', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1199, '游泳包防水包', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1200, '女士泳衣', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1201, '男士泳衣', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1202, '比基尼', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1203, '其它', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1204, '冲锋衣裤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1205, '速干衣裤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1206, '滑雪服', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1207, '羽绒服/棉服', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1208, '休闲衣裤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1209, '抓绒衣裤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1210, '软壳衣裤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1211, 'T恤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1212, '户外风衣', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1213, '功能内衣', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1214, '军迷服饰', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1215, '登山鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1216, '雪地靴', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1217, '徒步鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1218, '越野跑鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1219, '休闲鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1220, '工装鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1221, '溯溪鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1222, '沙滩/凉拖', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1223, '户外袜', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1224, '帐篷/垫子', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1225, '睡袋/吊床', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1226, '登山攀岩', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1227, '户外配饰', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1228, '背包', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1229, '户外照明', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1230, '户外仪表', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1231, '户外工具', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1232, '望远镜', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1233, '旅游用品', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1234, '便携桌椅床', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1235, '野餐烧烤', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1236, '军迷用品', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1237, '救援装备', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1238, '滑雪装备', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1239, '极限户外', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1240, '冲浪潜水', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1241, '综合训练器', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1242, '其他大型器械', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1243, '哑铃', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1244, '仰卧板/收腹机', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1245, '其他中小型器材', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1246, '瑜伽舞蹈', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1247, '甩脂机', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1248, '踏步机', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1249, '武术搏击', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1250, '健身车/动感单车', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1251, '跑步机', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1252, '运动护具', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1253, '羽毛球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1254, '乒乓球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1255, '篮球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1256, '足球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1257, '网球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1258, '排球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1259, '高尔夫', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1260, '台球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1261, '棋牌麻将', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1262, '轮滑滑板', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1263, '其他', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1264, '0-6个月', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1265, '6-12个月', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1266, '1-3岁', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1267, '3-6岁', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1268, '6-14岁', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1269, '14岁以上', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1270, '遥控车', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1271, '遥控飞机', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1272, '遥控船', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1273, '机器人', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1274, '轨道/助力', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1275, '毛绒/布艺', 137, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1276, '靠垫/抱枕', 137, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1277, '芭比娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1278, '卡通娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1279, '智能娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1280, '仿真模型', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1281, '拼插模型', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1282, '收藏爱好', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1283, '炫舞毯', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1284, '爬行垫/毯', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1285, '户外玩具', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1286, '戏水玩具', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1287, '电影周边', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1288, '卡通周边', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1289, '网游周边', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1290, '摇铃/床铃', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1291, '健身架', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1292, '早教启智', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1293, '拖拉玩具', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1294, '积木', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1295, '拼图', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1296, '磁力棒', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1297, '立体拼插', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1298, '手工彩泥', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1299, '绘画工具', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1300, '情景玩具', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1301, '减压玩具', 145, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1302, '创意玩具', 145, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1303, '钢琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1304, '电子琴/电钢琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1305, '吉他/尤克里里', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1306, '打击乐器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1307, '西洋管弦', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1308, '民族管弦乐器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1309, '乐器配件', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1310, '电脑音乐', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1311, '工艺礼品乐器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1312, '口琴/口风琴/竖笛', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1313, '手风琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1314, '双色球', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1315, '大乐透', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1316, '福彩3D', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1317, '排列三', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1318, '排列五', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1319, '七星彩', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1320, '七乐彩', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1321, '竞彩足球', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1322, '竞彩篮球', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1323, '新时时彩', 147, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1324, '国内机票', 148, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1325, '国内酒店', 149, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1326, '酒店团购', 149, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1327, '度假', 150, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1328, '景点', 150, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1329, '租车', 150, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1330, '火车票', 150, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1331, '旅游团购', 150, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1332, '手机充值', 151, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1333, '游戏点卡', 152, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1334, 'QQ充值', 152, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1335, '电影票', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1336, '演唱会', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1337, '话剧歌剧', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1338, '音乐会', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1339, '体育赛事', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1340, '舞蹈芭蕾', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1341, '戏曲综艺', 153, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1342, '东北', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1343, '华北', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1344, '西北', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1345, '华中', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1346, '华东', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1347, '华南', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1348, '西南', 154, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1349, '苹果', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1350, '橙子', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1351, '奇异果/猕猴桃', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1352, '车厘子/樱桃', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1353, '芒果', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1354, '蓝莓', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1355, '火龙果', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1356, '葡萄/提子', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1357, '柚子', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1358, '香蕉', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1359, '牛油果', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1360, '梨', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1361, '菠萝/凤梨', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1362, '桔/橘', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1363, '柠檬', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1364, '草莓', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1365, '桃/李/杏', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1366, '更多水果', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1367, '水果礼盒/券', 155, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1368, '牛肉', 156, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1369, '羊肉', 156, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1370, '猪肉', 156, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1371, '内脏类', 156, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1372, '鱼类', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1373, '虾类', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1374, '蟹类', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1375, '贝类', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1376, '海参', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1377, '海产干货', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1378, '其他水产', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1379, '海产礼盒', 157, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1380, '鸡肉', 158, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1381, '鸭肉', 158, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1382, '蛋类', 158, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1383, '其他禽类', 158, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1384, '水饺/馄饨', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1385, '汤圆/元宵', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1386, '面点', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1387, '火锅丸串', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1388, '速冻半成品', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1389, '奶酪黄油', 159, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1390, '熟食', 160, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1391, '腊肠/腊肉', 160, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1392, '火腿', 160, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1393, '糕点', 160, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1394, '礼品卡券', 160, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1395, '冷藏果蔬汁', 161, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1396, '冰激凌', 161, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1397, '其他', 161, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1398, '叶菜类', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1399, '茄果瓜类', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1400, '根茎类', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1401, '鲜菌菇', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1402, '葱姜蒜椒', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1403, '半加工蔬菜', 162, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1404, '微型车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1405, '小型车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1406, '紧凑型车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1407, '中型车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1408, '中大型车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1409, '豪华车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1410, 'MPV', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1411, 'SUV', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1412, '跑车', 163, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1413, '微型车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1414, '小型车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1415, '紧凑型车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1416, '中型车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1417, '中大型车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1418, '豪华车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1419, 'MPV（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1420, 'SUV（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1421, '跑车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1422, '皮卡（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1423, '面包车（二手）', 164, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (1431, 'dsa323', 1, 2, 1, 0, NULL, NULL, NULL);
INSERT INTO `pms_category` VALUES (1432, 'fdsffdsadddd大萨达', 1431, 3, 1, 0, NULL, NULL, NULL);
INSERT INTO `pms_category` VALUES (1434, 'test', 107, 3, 1, 0, '', NULL, NULL);
INSERT INTO `pms_category` VALUES (1435, 'test', 107, 3, 1, 0, NULL, NULL, NULL);
INSERT INTO `pms_category` VALUES (1436, 'tesat', 107, 3, 1, 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for pms_category_brand_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_brand_relation`;
CREATE TABLE `pms_category_brand_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `catelog_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `brand_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `catelog_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '品牌分类关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_category_brand_relation
-- ----------------------------
INSERT INTO `pms_category_brand_relation` VALUES (1, 2, 225, '华为', '手机');
INSERT INTO `pms_category_brand_relation` VALUES (2, 1, 225, '荣耀', '手机');

-- ----------------------------
-- Table structure for pms_comment_replay
-- ----------------------------
DROP TABLE IF EXISTS `pms_comment_replay`;
CREATE TABLE `pms_comment_replay`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` bigint(20) NULL DEFAULT NULL COMMENT '评论id',
  `reply_id` bigint(20) NULL DEFAULT NULL COMMENT '回复id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品评价回复关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_attr_value`;
CREATE TABLE `pms_product_attr_value`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT '属性id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性值',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `quick_show` tinyint(4) NULL DEFAULT NULL COMMENT '快速展示【是否展示在介绍上；0-否 1-是】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'spu属性值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_product_attr_value
-- ----------------------------
INSERT INTO `pms_product_attr_value` VALUES (29, 3, 2, '上市年份', '2020', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (30, 3, 3, '颜色', '流光幻镜', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (31, 3, 1, '入网参数', '5G', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (32, 3, 9, '电池容量', '5000mAh', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (33, 3, 10, '机身长度(mm)', '168', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (34, 3, 7, 'CPU型号', '麒麟990', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (35, 3, 8, 'CPU工艺', '5nm', 0, 1);
INSERT INTO `pms_product_attr_value` VALUES (36, 3, 12, 'CPU品牌', '海思(Hisilicon)', 0, 1);

-- ----------------------------
-- Table structure for pms_sku_images
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_images`;
CREATE TABLE `pms_sku_images`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `default_img` int(11) NULL DEFAULT NULL COMMENT '默认图[0 - 不是默认图，1 - 是默认图]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'sku图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku_images
-- ----------------------------
INSERT INTO `pms_sku_images` VALUES (1, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (2, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (3, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (4, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (5, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (6, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (7, 1, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (8, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (9, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (10, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (11, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (12, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (13, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (14, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (15, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (16, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (17, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (18, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (19, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (20, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (21, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (22, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (23, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (24, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (25, 3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (26, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (27, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (28, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (29, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (30, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (31, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (32, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (33, 4, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (34, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (35, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (36, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (37, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (38, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (39, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (40, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (41, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (42, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (43, 5, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (44, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (45, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (46, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (47, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (48, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (49, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (50, 6, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (51, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (52, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (53, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (54, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (55, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (56, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (57, 7, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (58, 8, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (59, 8, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (60, 8, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, 1);
INSERT INTO `pms_sku_images` VALUES (61, 8, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (62, 8, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (63, 9, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (64, 9, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (65, 9, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (66, 9, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, 0);
INSERT INTO `pms_sku_images` VALUES (67, 9, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, 0);

-- ----------------------------
-- Table structure for pms_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_info`;
CREATE TABLE `pms_sku_info`  (
  `sku_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spuId',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `sku_desc` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'sku介绍描述',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所属分类id',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `sku_default_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '默认图片',
  `sku_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `sku_subtitle` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '副标题',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '价格',
  `sale_count` bigint(20) NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'sku信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku_info
-- ----------------------------
INSERT INTO `pms_sku_info` VALUES (1, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', '【6月6日10:08抢购】抢券享24期免息；5G手机至高享24期免息，低至5.1元/天》', 8888.0000, 2654);
INSERT INTO `pms_sku_info` VALUES (2, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐三', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', '【6月6日10:08抢购】抢券享24期免息；5G手机至高享24期免息，低至5.1元/天》', 8888.0000, 1454);
INSERT INTO `pms_sku_info` VALUES (3, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐一', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', '【品牌日限时享24期免息】5000万超感知徕卡四摄；50倍数字变焦；5G手机至高享24期免息，低至5.1元/天》', 5988.0000, 1673);
INSERT INTO `pms_sku_info` VALUES (4, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', '【品牌日限时享24期免息】5000万超感知徕卡四摄；50倍数字变焦；5G手机至高享24期免息，低至5.1元/天》', 5988.0000, 3587);
INSERT INTO `pms_sku_info` VALUES (5, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐三', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', '【品牌日限时享24期免息】5000万超感知徕卡四摄；50倍数字变焦；5G手机至高享24期免息，低至5.1元/天》', 5988.0000, 1235);
INSERT INTO `pms_sku_info` VALUES (6, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐一', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', '【下单立减100/12期免息/咨询可省更多钱】赠原装碎屏险+华为原装无线充+华为智能手环+AI智能蓝牙音箱+硅胶保护壳+保护膜+晒单礼购买P40咨询客服享优惠', 5988.0000, 1456);
INSERT INTO `pms_sku_info` VALUES (7, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐二', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐二 【白条12期免息送碎屏险】华为 P40 Pro 5G手机 分期', '【下单立减100/12期免息/咨询可省更多钱】赠原装碎屏险+华为原装无线充+华为智能手环+AI智能蓝牙音箱+硅胶保护壳+保护膜+晒单礼购买P40咨询客服享优惠', 5988.0000, 2354);
INSERT INTO `pms_sku_info` VALUES (8, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐三', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐三 【白条12期免息送碎屏险】华为 P40 Pro 5G手机 分期', '【下单立减100/12期免息/咨询可省更多钱】赠原装碎屏险+华为原装无线充+华为智能手环+AI智能蓝牙音箱+硅胶保护壳+保护膜+晒单礼购买P40咨询客服享优惠', 5988.0000, 2546);
INSERT INTO `pms_sku_info` VALUES (9, 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐一', NULL, 225, 2, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', '华为 HUAWEI P40 Pro+ 麒麟990 5G  钛空银 套餐一 【白条12期免息送碎屏险】华为 P40 Pro 5G手机 分期', '【保价618，下单立减200，未减咨询，至高可减400】赠原装碎屏险+华为原装智能手环+网红榨汁机+AI蓝牙音箱+硅胶保护壳+保护膜+晒单礼', 4188.0000, 957);

-- ----------------------------
-- Table structure for pms_sku_sale_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_sale_attr_value`;
CREATE TABLE `pms_sku_sale_attr_value`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT 'attr_id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '销售属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '销售属性值',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'sku销售属性&值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku_sale_attr_value
-- ----------------------------
INSERT INTO `pms_sku_sale_attr_value` VALUES (1, 1, 4, '颜色', '流光幻镜', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (2, 1, 6, '套餐', '套餐二', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (3, 2, 4, '颜色', '流光幻镜', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (4, 2, 6, '套餐', '套餐三', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (5, 3, 4, '颜色', '流光幻镜', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (6, 3, 6, '套餐', '套餐一', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (7, 4, 4, '颜色', '霓影紫', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (8, 4, 6, '套餐', '套餐二', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (9, 5, 4, '颜色', '霓影紫', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (10, 5, 6, '套餐', '套餐三', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (11, 6, 4, '颜色', '霓影紫', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (12, 6, 6, '套餐', '套餐一', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (13, 7, 4, '颜色', '钛空银', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (14, 7, 6, '套餐', '套餐二', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (15, 8, 4, '颜色', '钛空银', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (16, 8, 6, '套餐', '套餐三', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (17, 9, 4, '颜色', '钛空银', 0);
INSERT INTO `pms_sku_sale_attr_value` VALUES (18, 9, 6, '套餐', '套餐一', 0);

-- ----------------------------
-- Table structure for pms_spu_comment
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_comment`;
CREATE TABLE `pms_spu_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名字',
  `member_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `star` tinyint(1) NULL DEFAULT NULL COMMENT '星级',
  `member_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员ip',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `show_status` tinyint(1) NULL DEFAULT NULL COMMENT '显示状态[0-不显示，1-显示]',
  `spu_attributes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购买时属性组合',
  `likes_count` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `reply_count` int(11) NULL DEFAULT NULL COMMENT '回复数',
  `resources` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `member_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `comment_type` tinyint(4) NULL DEFAULT NULL COMMENT '评论类型[0 - 对商品的直接评论，1 - 对评论的回复]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品评价' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu_images
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_images`;
CREATE TABLE `pms_spu_images`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `img_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `default_img` tinyint(4) NULL DEFAULT NULL COMMENT '是否默认图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'spu图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_images
-- ----------------------------
INSERT INTO `pms_spu_images` VALUES (17, 3, '图集1.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (18, 3, '图集2.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (19, 3, '图集3.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/ceb40c90-cce6-44fb-83ca-0de9420e88ac_图集3.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (20, 3, '图集4.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/4d2ed8b5-5504-4bad-8620-75db9c29d3ea_图集4.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (21, 3, '图集5.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/72b3962b-6a79-4d16-8c83-2f3f61dba176_图集5.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (22, 3, '图集6.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (23, 3, '图集7.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 0, NULL);
INSERT INTO `pms_spu_images` VALUES (24, 3, '图集8.jpg', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/93b64468-46f1-4257-8b5f-58b8f8375081_图集8.jpg', 0, NULL);

-- ----------------------------
-- Table structure for pms_spu_info
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_info`;
CREATE TABLE `pms_spu_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `spu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `spu_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品描述',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所属分类id',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `weight` decimal(18, 4) NULL DEFAULT NULL,
  `publish_status` tinyint(4) NULL DEFAULT NULL COMMENT '上架状态[0 - 下架，1 - 上架]',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'spu信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_info
-- ----------------------------
INSERT INTO `pms_spu_info` VALUES (3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', '华为 HUAWEI P40 Pro+ 麒麟990 5G ', 225, 2, 0.5700, 1, '2020-06-06 10:40:25', '2020-06-15 02:39:01');

-- ----------------------------
-- Table structure for pms_spu_info_desc
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_info_desc`;
CREATE TABLE `pms_spu_info_desc`  (
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `decript` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '商品介绍',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'spu信息介绍' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_info_desc
-- ----------------------------
INSERT INTO `pms_spu_info_desc` VALUES (3, 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/5fcc807c-1a0c-431f-8635-ac75fe2b2a72_华为2.jpg,https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/5952be7a-bca4-4794-8a3f-8612b45bffb0_华为1.jpg,https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/fad57e61-8650-4d5c-87d1-064bfff90553_华为3.jpg,https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/344445d8-346b-4d04-8de1-4e31f6db4336_华为4.jpg,https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/7d36cce9-94dc-49a4-8069-0ae66f57cdc3_华为5.jpg');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

* JXmall_sms.sql

```mysql
CREATE DATABASE `jxmall_sms`;

USE `jxmall_sms`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_type` tinyint(1) NULL DEFAULT NULL COMMENT '优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]',
  `coupon_img` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优惠券图片',
  `coupon_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优惠卷名字',
  `num` int(11) NULL DEFAULT NULL COMMENT '数量',
  `amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '金额',
  `per_limit` int(11) NULL DEFAULT NULL COMMENT '每人限领张数',
  `min_point` decimal(18, 4) NULL DEFAULT NULL COMMENT '使用门槛',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `use_type` tinyint(1) NULL DEFAULT NULL COMMENT '使用类型[0->全场通用；1->指定分类；2->指定商品]',
  `note` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `publish_count` int(11) NULL DEFAULT NULL COMMENT '发行数量',
  `use_count` int(11) NULL DEFAULT NULL COMMENT '已使用数量',
  `receive_count` int(11) NULL DEFAULT NULL COMMENT '领取数量',
  `enable_start_time` datetime(0) NULL DEFAULT NULL COMMENT '可以领取的开始日期',
  `enable_end_time` datetime(0) NULL DEFAULT NULL COMMENT '可以领取的结束日期',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优惠码',
  `member_level` tinyint(1) NULL DEFAULT NULL COMMENT '可以领取的会员等级[0->不限等级，其他-对应等级]',
  `publish` tinyint(1) NULL DEFAULT NULL COMMENT '发布状态[0-未发布，1-已发布]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠券信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员id',
  `member_nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员名字',
  `get_type` tinyint(1) NULL DEFAULT NULL COMMENT '获取方式[0->后台赠送；1->主动领取]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `use_type` tinyint(1) NULL DEFAULT NULL COMMENT '使用状态[0->未使用；1->已使用；2->已过期]',
  `use_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `order_sn` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠券领取历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_spu_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_category_relation`;
CREATE TABLE `sms_coupon_spu_category_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券id',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '产品分类id',
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '产品分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠券分类关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_spu_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_relation`;
CREATE TABLE `sms_coupon_spu_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠券与产品关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_home_adv
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_adv`;
CREATE TABLE `sms_home_adv`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名字',
  `pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `click_count` int(11) NULL DEFAULT NULL COMMENT '点击数',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '广告详情连接地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `publisher_id` bigint(20) NULL DEFAULT NULL COMMENT '发布者',
  `auth_id` bigint(20) NULL DEFAULT NULL COMMENT '审核者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '首页轮播广告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_home_subject
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_subject`;
CREATE TABLE `sms_home_subject`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专题名字',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专题标题',
  `sub_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专题副标题',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '显示状态',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详情连接',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专题图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_home_subject_spu
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_subject_spu`;
CREATE TABLE `sms_home_subject_spu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专题名字',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '专题id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '专题商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_member_price
-- ----------------------------
DROP TABLE IF EXISTS `sms_member_price`;
CREATE TABLE `sms_member_price`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `member_level_id` bigint(20) NULL DEFAULT NULL COMMENT '会员等级id',
  `member_level_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员等级名',
  `member_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '会员对应价格',
  `add_other` tinyint(1) NULL DEFAULT NULL COMMENT '可否叠加其他优惠[0-不可叠加优惠，1-可叠加]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品会员价格' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_member_price
-- ----------------------------
INSERT INTO `sms_member_price` VALUES (1, 1, 2, '铜牌会员', 8788.0000, 1);
INSERT INTO `sms_member_price` VALUES (2, 1, 3, '银牌会员', 8688.0000, 1);
INSERT INTO `sms_member_price` VALUES (3, 1, 4, '金牌会员', 8588.0000, 1);
INSERT INTO `sms_member_price` VALUES (4, 1, 5, '钻石会员', 8288.0000, 1);
INSERT INTO `sms_member_price` VALUES (5, 2, 2, '铜牌会员', 8766.0000, 1);
INSERT INTO `sms_member_price` VALUES (6, 2, 3, '银牌会员', 8666.0000, 1);
INSERT INTO `sms_member_price` VALUES (7, 2, 4, '金牌会员', 8566.0000, 1);
INSERT INTO `sms_member_price` VALUES (8, 2, 5, '钻石会员', 8266.0000, 1);
INSERT INTO `sms_member_price` VALUES (9, 3, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (10, 3, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (11, 3, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (12, 3, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (13, 4, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (14, 4, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (15, 4, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (16, 4, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (17, 5, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (18, 5, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (19, 5, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (20, 5, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (21, 6, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (22, 6, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (23, 6, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (24, 6, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (25, 7, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (26, 7, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (27, 7, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (28, 7, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (29, 8, 2, '铜牌会员', 5888.0000, 1);
INSERT INTO `sms_member_price` VALUES (30, 8, 3, '银牌会员', 5788.0000, 1);
INSERT INTO `sms_member_price` VALUES (31, 8, 4, '金牌会员', 5688.0000, 1);
INSERT INTO `sms_member_price` VALUES (32, 8, 5, '钻石会员', 5588.0000, 1);
INSERT INTO `sms_member_price` VALUES (33, 9, 2, '铜牌会员', 4088.0000, 1);
INSERT INTO `sms_member_price` VALUES (34, 9, 3, '银牌会员', 3988.0000, 1);
INSERT INTO `sms_member_price` VALUES (35, 9, 4, '金牌会员', 3888.0000, 1);
INSERT INTO `sms_member_price` VALUES (36, 9, 5, '钻石会员', 3288.0000, 1);

-- ----------------------------
-- Table structure for sms_seckill_promotion
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_promotion`;
CREATE TABLE `sms_seckill_promotion`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动标题',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '上下线状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_seckill_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_session`;
CREATE TABLE `sms_seckill_session`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '场次名称',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '每日开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '每日结束时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀活动场次' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_seckill_session
-- ----------------------------
INSERT INTO `sms_seckill_session` VALUES (1, '谷粒秒杀', '2020-07-10 00:15:00', '2020-07-10 15:59:59', 1, '2020-07-09 15:14:21');
INSERT INTO `sms_seckill_session` VALUES (2, '谷粒秒杀', '2020-07-09 05:15:00', '2020-07-09 15:59:59', 1, '2020-07-09 05:14:47');

-- ----------------------------
-- Table structure for sms_seckill_sku_notice
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_notice`;
CREATE TABLE `sms_seckill_sku_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `session_id` bigint(20) NULL DEFAULT NULL COMMENT '活动场次id',
  `subcribe_time` datetime(0) NULL DEFAULT NULL COMMENT '订阅时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `notice_type` tinyint(1) NULL DEFAULT NULL COMMENT '通知方式[0-短信，1-邮件]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀商品通知订阅' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_seckill_sku_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_relation`;
CREATE TABLE `sms_seckill_sku_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promotion_id` bigint(20) NULL DEFAULT NULL COMMENT '活动id',
  `promotion_session_id` bigint(20) NULL DEFAULT NULL COMMENT '活动场次id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '秒杀价格',
  `seckill_count` decimal(10, 0) NULL DEFAULT NULL COMMENT '秒杀总量',
  `seckill_limit` decimal(10, 0) NULL DEFAULT NULL COMMENT '每人限购数量',
  `seckill_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀活动商品关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_seckill_sku_relation
-- ----------------------------
INSERT INTO `sms_seckill_sku_relation` VALUES (1, NULL, 1, 1, 4888, 100, 1, 0);
INSERT INTO `sms_seckill_sku_relation` VALUES (2, NULL, 2, 1, 6666, 200, 1, 0);

-- ----------------------------
-- Table structure for sms_sku_full_reduction
-- ----------------------------
DROP TABLE IF EXISTS `sms_sku_full_reduction`;
CREATE TABLE `sms_sku_full_reduction`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `full_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '满多少',
  `reduce_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '减多少',
  `add_other` tinyint(1) NULL DEFAULT NULL COMMENT '是否参与其他优惠',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品满减信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_sku_full_reduction
-- ----------------------------
INSERT INTO `sms_sku_full_reduction` VALUES (1, 1, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (2, 2, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (3, 3, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (4, 4, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (5, 5, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (6, 6, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (7, 7, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (8, 8, 10000.0000, 80.0000, NULL);
INSERT INTO `sms_sku_full_reduction` VALUES (9, 9, 6666.0000, 200.0000, NULL);

-- ----------------------------
-- Table structure for sms_sku_ladder
-- ----------------------------
DROP TABLE IF EXISTS `sms_sku_ladder`;
CREATE TABLE `sms_sku_ladder`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `full_count` int(11) NULL DEFAULT NULL COMMENT '满几件',
  `discount` decimal(4, 2) NULL DEFAULT NULL COMMENT '打几折',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '折后价',
  `add_other` tinyint(1) NULL DEFAULT NULL COMMENT '是否叠加其他优惠[0-不可叠加，1-可叠加]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品阶梯价格' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_sku_ladder
-- ----------------------------
INSERT INTO `sms_sku_ladder` VALUES (1, 1, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (2, 1, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (3, 2, 5, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (4, 2, 5, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (5, 3, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (6, 3, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (7, 4, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (8, 4, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (9, 5, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (10, 5, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (11, 6, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (12, 6, 3, 0.92, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (13, 7, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (14, 7, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (15, 8, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (16, 8, 3, 0.92, NULL, 1);
INSERT INTO `sms_sku_ladder` VALUES (17, 9, 4, 0.80, NULL, 0);
INSERT INTO `sms_sku_ladder` VALUES (18, 9, 4, 0.80, NULL, 0);

-- ----------------------------
-- Table structure for sms_spu_bounds
-- ----------------------------
DROP TABLE IF EXISTS `sms_spu_bounds`;
CREATE TABLE `sms_spu_bounds`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) NULL DEFAULT NULL,
  `grow_bounds` decimal(18, 4) NULL DEFAULT NULL COMMENT '成长积分',
  `buy_bounds` decimal(18, 4) NULL DEFAULT NULL COMMENT '购物积分',
  `work` tinyint(1) NULL DEFAULT NULL COMMENT '优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品spu积分设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_spu_bounds
-- ----------------------------
INSERT INTO `sms_spu_bounds` VALUES (1, 1, 500.0000, 2000.0000, NULL);
INSERT INTO `sms_spu_bounds` VALUES (2, 2, 500.0000, 2000.0000, NULL);
INSERT INTO `sms_spu_bounds` VALUES (3, 3, 500.0000, 2000.0000, NULL);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

* JXmall_ums.sql

```mysql
CREATE DATABASE `jxmall_ums`;

USE `jxmall_ums`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_growth_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_growth_change_history`;
CREATE TABLE `ums_growth_change_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) NULL DEFAULT NULL COMMENT '改变的值（正负计数）',
  `note` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '积分来源[0-购物，1-管理员修改]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '成长值变化历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_integration_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_integration_change_history`;
CREATE TABLE `ums_integration_change_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) NULL DEFAULT NULL COMMENT '变化的值',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `source_tyoe` tinyint(4) NULL DEFAULT NULL COMMENT '来源[0->购物；1->管理员修改;2->活动]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '积分变化历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) NULL DEFAULT NULL COMMENT '会员等级id',
  `username` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `header` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别',
  `birth` date NULL DEFAULT NULL COMMENT '生日',
  `city` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职业',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个性签名',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '用户来源',
  `integration` int(11) NULL DEFAULT NULL COMMENT '积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '成长值',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `social_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交账号ID',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交账号Token',
  `expires_in` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交账号Token过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1, 1, 'firenay', '$2a$10$uDXc05.IsjGI//f7HC8/lOGys0oRiFZX59olzqVqTl8IKxR8iBVGy', 'firenay', '18173516208', 'xxx@gmail.com', NULL, 0, '2020-06-25', '湖南 长沙', 'JAVA', NULL, NULL, NULL, NULL, 0, '2020-06-25 13:09:14', NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (2, 1, 'sentinel', '$2a$10$j5XRpUeGq7AYIFk7pqdvyebK.Bo5MvasCxk.8RuBWsHFcq5RzXKEC', 'sentinel', '18173516102', 'xxx@gmail.com', NULL, 1, '2020-06-25', '湖南 长沙', 'JAVA', NULL, NULL, NULL, NULL, 0, '2020-06-25 13:15:33', NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (3, 1, 'firenayfly', '$2a$10$UvvfpBagTqbalI6UTnq5nOiPheEdbKLO64fozWMx1lUeK9p2tM366', 'firenayfly', '18467894965', 'xxx@gmail.com', NULL, 1, '2020-06-25', '湖南 长沙', 'JAVA', NULL, NULL, NULL, NULL, 0, '2020-06-25 13:18:32', NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (4, 1, '汀西氟的我是你', '$2a$10$uDXc05.IsjGI//f7HC8/lOGys0oRiFZX59olzqVqTl8IKxR8iBVGy', '汀西氟的我是你', '18467894965', 'xxx@gmail.com', NULL, 1, '2020-06-26', '湖南 长沙', '自媒体', NULL, NULL, NULL, NULL, 0, '2020-06-26 09:36:00', '5605937365', '2.00b5w4HGMwxc6B0e3d62c666DlN1DD', '157679999');

-- ----------------------------
-- Table structure for ums_member_collect_spu
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_collect_spu`;
CREATE TABLE `ums_member_collect_spu`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_img',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员收藏的商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member_collect_subject
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_collect_subject`;
CREATE TABLE `ums_member_collect_subject`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT 'subject_id',
  `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'subject_name',
  `subject_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'subject_img',
  `subject_urll` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员收藏的专题活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member_level
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_level`;
CREATE TABLE `ums_member_level`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '等级名称',
  `growth_point` int(11) NULL DEFAULT NULL COMMENT '等级需要的成长值',
  `default_status` tinyint(4) NULL DEFAULT NULL COMMENT '是否为默认等级[0->不是；1->是]',
  `free_freight_point` decimal(18, 4) NULL DEFAULT NULL COMMENT '免运费标准',
  `comment_growth_point` int(11) NULL DEFAULT NULL COMMENT '每次评价获取的成长值',
  `priviledge_free_freight` tinyint(4) NULL DEFAULT NULL COMMENT '是否有免邮特权',
  `priviledge_member_price` tinyint(4) NULL DEFAULT NULL COMMENT '是否有会员价格特权',
  `priviledge_birthday` tinyint(4) NULL DEFAULT NULL COMMENT '是否有生日特权',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员等级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member_level
-- ----------------------------
INSERT INTO `ums_member_level` VALUES (1, '普通会员', 0, 1, 188.0000, 10, 0, 0, 1, '初级会员');
INSERT INTO `ums_member_level` VALUES (2, '铜牌会员', 2000, 0, 159.0000, 20, 0, 1, 1, '铜牌会员');
INSERT INTO `ums_member_level` VALUES (3, '银牌会员', 5000, 0, 129.0000, 50, 0, 1, 1, '银牌会员');
INSERT INTO `ums_member_level` VALUES (4, '金牌会员', 8000, 0, 88.0000, 0, 1, 1, 1, '金牌会员');
INSERT INTO `ums_member_level` VALUES (5, '钻石会员', 12000, 0, 48.0000, 80, 1, 1, 1, '钻石会员');

-- ----------------------------
-- Table structure for ums_member_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_login_log`;
CREATE TABLE `ums_member_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'ip',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'city',
  `login_type` tinyint(1) NULL DEFAULT NULL COMMENT '登录类型[1-web，2-app]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member_receive_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_receive_address`;
CREATE TABLE `ums_member_receive_address`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址(街道)',
  `areacode` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区代码',
  `default_status` tinyint(1) NULL DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员收货地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member_receive_address
-- ----------------------------
INSERT INTO `ums_member_receive_address` VALUES (1, 1, 'firenay', '18173516208', NULL, '湖南', '长沙', NULL, '望城区', NULL, 1);
INSERT INTO `ums_member_receive_address` VALUES (2, 2, 'sentinel', '18173516102', NULL, '湖南', '长沙', NULL, '雨花区', NULL, 1);
INSERT INTO `ums_member_receive_address` VALUES (3, 3, 'firenayfly', '15421564125', NULL, '陕西', '西安', NULL, '新城区', NULL, 1);

-- ----------------------------
-- Table structure for ums_member_statistics_info
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_statistics_info`;
CREATE TABLE `ums_member_statistics_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员id',
  `consume_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计消费金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '累计优惠金额',
  `order_count` int(11) NULL DEFAULT NULL COMMENT '订单数量',
  `coupon_count` int(11) NULL DEFAULT NULL COMMENT '优惠券数量',
  `comment_count` int(11) NULL DEFAULT NULL COMMENT '评价数',
  `return_order_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `login_count` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `attend_count` int(11) NULL DEFAULT NULL COMMENT '关注数量',
  `fans_count` int(11) NULL DEFAULT NULL COMMENT '粉丝数量',
  `collect_product_count` int(11) NULL DEFAULT NULL COMMENT '收藏的商品数量',
  `collect_subject_count` int(11) NULL DEFAULT NULL COMMENT '收藏的专题活动数量',
  `collect_comment_count` int(11) NULL DEFAULT NULL COMMENT '收藏的评论数量',
  `invite_friend_count` int(11) NULL DEFAULT NULL COMMENT '邀请的朋友数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员统计信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

* JXmall_wms.sql

```mysql
CREATE DATABASE `jxmall_wms`;

USE `jxmall_wms`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint(20) NULL DEFAULT NULL,
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `priority` int(4) NULL DEFAULT NULL,
  `status` int(4) NULL DEFAULT NULL,
  `ware_id` bigint(20) NULL DEFAULT NULL,
  `amount` decimal(18, 4) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (1, 2, 'fireflynay', '18156475879', 1, 3, 1, 149700.0000, '2020-06-07 00:34:32', '2020-06-07 15:55:06');
INSERT INTO `wms_purchase` VALUES (2, 1, 'admin', '18173516309', 1, 3, 1, 177760.0000, '2020-06-07 00:55:43', '2020-06-07 14:14:47');
INSERT INTO `wms_purchase` VALUES (3, 1, 'admin', '18173516309', 1, 3, 1, 297520.0000, '2020-06-07 13:33:08', '2020-06-07 15:21:43');
INSERT INTO `wms_purchase` VALUES (4, 2, 'fireflynay', '18156475879', 1, 3, 1, 179640.0000, '2020-06-07 14:01:10', '2020-06-07 15:18:35');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (1, 1, 2, 10, 88880.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (2, 3, 2, 20, 177760.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (3, 3, 3, 5, 29940.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (4, 3, 3, 15, 89820.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (5, 4, 4, 30, 179640.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (6, 1, 5, 25, 149700.0000, 1, 3);

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '1号仓库', '长沙市', '410000');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) NULL DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (1, NULL, '202007101511435951281486241929375746', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (2, NULL, '202007102108315951281576033585246209', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `lock_status` int(1) NULL DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (1, 2, '', 1, 1, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (2, 2, '', 1, 2, 1, 2);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) NULL DEFAULT 0 COMMENT '锁定库存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id`(`sku_id`) USING BTREE,
  INDEX `ware_id`(`ware_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (3, 2, 1, 50, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐三', 0);
INSERT INTO `wms_ware_sku` VALUES (4, 3, 1, 35, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐一', 0);
INSERT INTO `wms_ware_sku` VALUES (5, 4, 1, 60, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二', 0);
INSERT INTO `wms_ware_sku` VALUES (6, 5, 1, 125, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐三', 0);

SET FOREIGN_KEY_CHECKS = 1;
```

