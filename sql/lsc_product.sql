/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_product

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 27/03/2024 10:04:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '/*、',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名字',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '商品状态 0下架 1上架',
  `sort_id` bigint(1) UNSIGNED NOT NULL COMMENT '分类id',
  `picture` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '如果商品有规格就显示价格最低的配置',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '小春鸡', 1, '好吃', 1, 5, '', 31.00, '2023-09-17 17:53:52', '2024-03-26 07:58:55', 0, 1, 1);
INSERT INTO `product_info` VALUES (3, '汉堡', 1, '不错', 1, 1, '', 18.00, '2023-10-14 15:48:21', '2023-10-14 15:48:21', 0, 1, 1);
INSERT INTO `product_info` VALUES (4, '薯条', 1, '很好', 1, 2, '', 22.00, '2023-10-14 15:57:42', '2023-10-14 15:57:42', 0, 1, 1);
INSERT INTO `product_info` VALUES (5, '可乐', 1, '一般', 1, 3, 'http://43.136.73.159:9000/linmourscanorder/product/1/1/d0851fe988a24e9aa735a37c25aef84d.jpg', 2.00, '2023-10-14 15:59:18', '2023-10-15 08:22:45', 0, 1, 1);
INSERT INTO `product_info` VALUES (6, '苹果', 1, '可以', 1, 4, 'http://43.136.73.159:9000/linmourscanorder/product/1/1/3bf40d69f6ab48438e6f1799d8b88e40.png', 6.80, '2023-10-14 16:30:55', '2023-10-15 08:23:08', 0, 1, 1);
INSERT INTO `product_info` VALUES (7, '菠萝', 1, '挺好', 1, 5, '', 11.00, '2023-10-14 16:33:04', '2024-03-22 10:01:41', 0, 1, 1);
INSERT INTO `product_info` VALUES (13, '田鸡', 1, '不错', 1, 3, '', 69.00, '2024-03-25 18:35:29', '2024-03-25 18:35:29', 0, 1, 1);
INSERT INTO `product_info` VALUES (15, '芬达', 1, '气多', 1, 2, '', 3.00, '2024-03-25 19:48:38', '2024-03-25 19:48:38', 0, 1, 1);

-- ----------------------------
-- Table structure for product_inventory
-- ----------------------------
DROP TABLE IF EXISTS `product_inventory`;
CREATE TABLE `product_inventory`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物品名字',
  `num` int(10) UNSIGNED NOT NULL COMMENT '物品数量',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物品的计量单位',
  `product_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属物品',
  `shop_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属店铺',
  `quantity` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '库存数量',
  `threshold` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '阈值，提示库存不足',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  `deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_inventory
-- ----------------------------
INSERT INTO `product_inventory` VALUES (1, '鸡', 1, '只', 1, 1, 0, 0, '2023-09-17 17:53:56', '2023-09-17 17:53:56', 1, 1, 0);
INSERT INTO `product_inventory` VALUES (2, '鸡', 1, '只', 1, 1, 0, 0, '2024-03-06 21:26:25', '2024-03-06 21:26:25', 1, 1, 0);

-- ----------------------------
-- Table structure for product_sort
-- ----------------------------
DROP TABLE IF EXISTS `product_sort`;
CREATE TABLE `product_sort`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品分类',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `update_by` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_sort
-- ----------------------------
INSERT INTO `product_sort` VALUES (1, '主食', 1, '2023-07-28 06:21:36', '2023-07-28 06:21:36', 1, 1, 0);
INSERT INTO `product_sort` VALUES (2, '饮品', 1, '2023-07-28 08:51:20', '2023-07-28 08:51:20', 1, 1, 0);
INSERT INTO `product_sort` VALUES (3, '甜点', 1, '2023-07-28 08:51:49', '2023-07-28 08:51:49', 1, 1, 0);
INSERT INTO `product_sort` VALUES (4, '本店招牌', 1, '2023-07-28 08:53:22', '2023-07-28 08:53:22', 1, 1, 0);
INSERT INTO `product_sort` VALUES (5, '热门产品', 1, '2023-07-28 08:58:45', '2023-07-28 08:58:45', 1, 1, 0);
INSERT INTO `product_sort` VALUES (6, '美味佳肴', 1, '2024-03-04 21:41:24', '2024-03-04 21:41:24', NULL, NULL, 0);
INSERT INTO `product_sort` VALUES (11, '上大分', 2, '2024-03-05 09:24:40', '2024-03-05 09:24:40', NULL, NULL, 0);
INSERT INTO `product_sort` VALUES (12, 'fdgsd ', 2, '2024-03-05 20:32:11', '2024-03-05 20:32:11', NULL, NULL, 0);
INSERT INTO `product_sort` VALUES (13, '奢侈美食', 2, '2024-03-22 10:09:18', '2024-03-22 10:09:18', NULL, NULL, 0);
INSERT INTO `product_sort` VALUES (14, '1', 2, '2024-03-22 10:10:04', '2024-03-22 10:10:04', NULL, NULL, 0);
INSERT INTO `product_sort` VALUES (15, '天价美食', 1, '2024-03-22 10:12:33', '2024-03-22 10:12:33', NULL, NULL, 0);

-- ----------------------------
-- Table structure for product_spec_option
-- ----------------------------
DROP TABLE IF EXISTS `product_spec_option`;
CREATE TABLE `product_spec_option`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '默认为0',
  `spec_sort_id` bigint(20) NOT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_spec_option
-- ----------------------------
INSERT INTO `product_spec_option` VALUES (1, '大份', 75.00, 1, 0);
INSERT INTO `product_spec_option` VALUES (2, '小份', 69.00, 1, 0);
INSERT INTO `product_spec_option` VALUES (5, '大瓶', 7.00, 4, 0);
INSERT INTO `product_spec_option` VALUES (6, '小瓶', 3.00, 4, 0);
INSERT INTO `product_spec_option` VALUES (7, '苹果', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (8, '香蕉', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (9, '樱桃', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (10, '香辣', 0.00, 6, 0);
INSERT INTO `product_spec_option` VALUES (11, '清香', 0.00, 6, 0);
INSERT INTO `product_spec_option` VALUES (12, '香辣', 0.00, 7, 0);
INSERT INTO `product_spec_option` VALUES (13, '清香', 0.00, 7, 0);
INSERT INTO `product_spec_option` VALUES (14, '香辣', 0.00, 8, 0);
INSERT INTO `product_spec_option` VALUES (15, '清香', 0.00, 8, 0);

-- ----------------------------
-- Table structure for product_spec_sort
-- ----------------------------
DROP TABLE IF EXISTS `product_spec_sort`;
CREATE TABLE `product_spec_sort`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_spec_sort
-- ----------------------------
INSERT INTO `product_spec_sort` VALUES (1, '分量', 13, 0);
INSERT INTO `product_spec_sort` VALUES (4, '分量', 15, 0);
INSERT INTO `product_spec_sort` VALUES (5, '口味', 15, 0);
INSERT INTO `product_spec_sort` VALUES (6, '口味', 1, 0);
INSERT INTO `product_spec_sort` VALUES (7, '口味', 1, 0);
INSERT INTO `product_spec_sort` VALUES (8, '口味', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
