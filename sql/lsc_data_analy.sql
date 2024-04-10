/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_data_analy

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 10/04/2024 15:38:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_summary_day
-- ----------------------------
DROP TABLE IF EXISTS `order_summary_day`;
CREATE TABLE `order_summary_day`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` date NULL DEFAULT NULL COMMENT '日期',
  `order_num` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '订单数',
  `product_num` int(11) NULL DEFAULT 0 COMMENT '商品数量',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `avg_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '每单平均金额',
  `shop_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_summary_day
-- ----------------------------

-- ----------------------------
-- Table structure for order_summary_time_period
-- ----------------------------
DROP TABLE IF EXISTS `order_summary_time_period`;
CREATE TABLE `order_summary_time_period`  (
  `
id` int(11) NOT NULL,
  `time_period` datetime NULL DEFAULT NULL COMMENT '早餐：07:00 - 11:00\r\n午餐：11:00 - 15:00\r\n晚餐：15:00 - 23:00\r\n深夜：23:00 - 07:00',
  `order_num` int(11) NULL DEFAULT 0,
  `product_num` int(11) NULL DEFAULT 0,
  `price` decimal(10, 2) NULL DEFAULT 0.00,
  `avg_price` decimal(10, 2) NULL DEFAULT 0.00,
  `shop_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`
id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_summary_time_period
-- ----------------------------

-- ----------------------------
-- Table structure for product_summary_day
-- ----------------------------
DROP TABLE IF EXISTS `product_summary_day`;
CREATE TABLE `product_summary_day`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名',
  `num` int(11) NULL DEFAULT 0 COMMENT '商品个数',
  `date` date NULL DEFAULT NULL COMMENT '日期',
  `shop_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_summary_day
-- ----------------------------

-- ----------------------------
-- Triggers structure for table order_summary_day
-- ----------------------------
DROP TRIGGER IF EXISTS `calc_avg_price_before_insert`;
delimiter ;;
CREATE TRIGGER `calc_avg_price_before_insert` BEFORE INSERT ON `order_summary_day` FOR EACH ROW BEGIN  
    IF NEW.order_num > 0 THEN  
        SET NEW.avg_price = NEW.price / NEW.order_num;  
    ELSE  
        SET NEW.avg_price = NULL; -- 或者设置为某个默认值，比如 0.00  
    END IF;  
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table order_summary_day
-- ----------------------------
DROP TRIGGER IF EXISTS `calc_avg_price_before_update`;
delimiter ;;
CREATE TRIGGER `calc_avg_price_before_update` BEFORE UPDATE ON `order_summary_day` FOR EACH ROW BEGIN  
    IF NEW.order_num > 0 THEN  
        SET NEW.avg_price = NEW.price / NEW.order_num;  
    ELSE  
        SET NEW.avg_price = NULL; -- 或者设置为某个默认值，比如 0.00  
    END IF;  
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
