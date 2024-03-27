/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_restaurant

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 27/03/2024 10:04:29
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
  `dishes` tinyint(4) NULL DEFAULT 0 COMMENT '0没有 1新的菜品',
  `serving` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '0还没上完菜 1已经上完菜',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0没人 1有人 2预定',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `qr_code_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deleted` tinyint(3) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of restaurant_table
-- ----------------------------
INSERT INTO `restaurant_table` VALUES (1, '第一桌', 0, 0, 0, 1, 'http://127.0.0.1:12800/file/QR/user_1/shop_1/1.png', 0);
INSERT INTO `restaurant_table` VALUES (2, '第二桌', 0, 0, 0, 1, 'http://127.0.0.1:12800/file/QR/user_1/shop_1/2.png', 0);
INSERT INTO `restaurant_table` VALUES (3, '第三桌', 0, 0, 0, 1, 'http://127.0.0.1:12800/file/QR/user_1/shop_1/3.png', 0);
INSERT INTO `restaurant_table` VALUES (4, '第四桌', 0, 0, 0, 1, 'http://127.0.0.1:12800/file/QR/user_1/shop_1/4.png', 0);
INSERT INTO `restaurant_table` VALUES (5, '第五桌', 0, 0, 0, 1, 'http://127.0.0.1:12800/file/QR/user_1/shop_1/5.png', 0);

SET FOREIGN_KEY_CHECKS = 1;
