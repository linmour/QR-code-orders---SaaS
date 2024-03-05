/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_restaurant

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/11/2023 22:12:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for restaurant_table
-- ----------------------------
DROP TABLE IF EXISTS `restaurant_table`;
CREATE TABLE `restaurant_table`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '餐桌名',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0没人 1有人 2预定',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `qr_code_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of restaurant_table
-- ----------------------------
INSERT INTO `restaurant_table` VALUES (1, '1号桌', 0, 1, 'http://120.79.7.243:9000/linmourscanorder/QR/1/1/1/1.png');
INSERT INTO `restaurant_table` VALUES (2, '2号桌', 0, 1, 'http://120.79.7.243:9000/linmourscanorder/QR/1/1/2/2.png');
INSERT INTO `restaurant_table` VALUES (3, '3号桌', 0, 1, 'http://120.79.7.243:9000/linmourscanorder/QR/1/1/3/3.png');
INSERT INTO `restaurant_table` VALUES (4, '4号桌', 0, 1, 'http://120.79.7.243:9000/linmourscanorder/QR/1/1/4/4.png');

SET FOREIGN_KEY_CHECKS = 1;
