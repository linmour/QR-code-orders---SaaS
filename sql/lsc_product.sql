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

 Date: 05/05/2024 16:22:23
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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '小春鸡', 1, '好吃', 1, 5, '', 31.00, '2023-09-17 17:53:52', '2024-04-12 12:58:14', 0, 1, 1);
INSERT INTO `product_info` VALUES (3, '汉堡', 1, '不错', 1, 1, '', 18.00, '2023-10-14 15:48:21', '2024-04-27 16:21:42', 0, 1, 1);
INSERT INTO `product_info` VALUES (4, '薯条', 1, '很好', 1, 2, '', 22.00, '2023-10-14 15:57:42', '2024-04-27 14:46:05', 0, 1, 1);
INSERT INTO `product_info` VALUES (5, '可乐', 1, '一般', 1, 3, 'http://43.136.73.159:9000/linmourscanorder/product/1/1/d0851fe988a24e9aa735a37c25aef84d.jpg', 2.00, '2023-10-14 15:59:18', '2023-10-15 08:22:45', 0, 1, 1);
INSERT INTO `product_info` VALUES (6, '苹果', 1, '可以', 1, 4, '', 6.80, '2023-10-14 16:30:55', '2024-04-27 14:46:22', 0, 1, 1);
INSERT INTO `product_info` VALUES (7, '菠萝', 1, '挺好', 1, 5, '', 11.00, '2023-10-14 16:33:04', '2024-03-22 10:01:41', 1, 1, 1);
INSERT INTO `product_info` VALUES (13, '田鸡', 1, '不错', 1, 3, '', 69.00, '2024-03-25 18:35:29', '2024-03-25 18:35:29', 0, 1, 1);
INSERT INTO `product_info` VALUES (15, '芬达', 1, '气多', 1, 2, '', 3.00, '2024-03-25 19:48:38', '2024-04-12 13:13:14', 0, 1, 1);
INSERT INTO `product_info` VALUES (16, '321', 1, '12321', 1, 1, '', 1.00, '2024-04-27 16:32:03', '2024-04-27 16:32:03', 1, 1, 1);
INSERT INTO `product_info` VALUES (17, '1', 1, '1', 1, 1, '', 1.00, '2024-04-27 16:36:25', '2024-04-27 16:36:25', 1, 1, 1);

-- ----------------------------
-- Table structure for product_inventory
-- ----------------------------
DROP TABLE IF EXISTS `product_inventory`;
CREATE TABLE `product_inventory`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物品名字',
  `threshold` int(255) NULL DEFAULT NULL COMMENT '阈值',
  `num` int(11) NULL DEFAULT NULL COMMENT '所剩个数',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物品的计量单位',
  `shop_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属店铺',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  `deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_inventory
-- ----------------------------
INSERT INTO `product_inventory` VALUES (1, '鸡', 2, 68, '只', 1, '2023-09-17 17:53:56', '2024-04-27 16:42:56', 1, 1, 0);
INSERT INTO `product_inventory` VALUES (2, '鸭', 2, 12, '头', 1, '2024-04-26 15:56:03', '2024-04-27 16:43:02', 1, 1, 0);
INSERT INTO `product_inventory` VALUES (6, '苹果', 2, 76, '个', 1, '2024-04-26 12:56:54', '2024-04-27 16:43:07', 1, 1, 0);
INSERT INTO `product_inventory` VALUES (8, '香蕉', 1, 28, '根', 1, '2024-04-26 19:33:32', '2024-04-27 16:43:13', 1, 1, 0);
INSERT INTO `product_inventory` VALUES (9, '1', 1, 1, '1', 1, '2024-04-26 19:34:22', '2024-04-26 19:34:22', 1, 1, 1);
INSERT INTO `product_inventory` VALUES (10, '2', 2, 2, '2', 1, '2024-04-26 19:38:10', '2024-04-26 19:38:10', 1, 1, 1);
INSERT INTO `product_inventory` VALUES (11, '1', 1, 1, '1', 1, '2024-04-26 21:07:56', '2024-04-26 21:07:56', 1, 1, 1);
INSERT INTO `product_inventory` VALUES (12, '2', 2, 2, '2', 1, '2024-04-26 21:09:10', '2024-04-26 21:09:10', 1, 1, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `product_sort` VALUES (16, '111111', 1, '2024-04-21 10:03:28', '2024-04-21 10:03:28', NULL, NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `product_spec_option` VALUES (12, '香辣', 0.00, 7, 1);
INSERT INTO `product_spec_option` VALUES (13, '清香', 0.00, 7, 1);
INSERT INTO `product_spec_option` VALUES (14, '香辣', 0.00, 8, 1);
INSERT INTO `product_spec_option` VALUES (15, '清香', 0.00, 8, 1);
INSERT INTO `product_spec_option` VALUES (16, '乌骨鸡', 0.00, 9, 0);
INSERT INTO `product_spec_option` VALUES (17, '大公鸡', 0.00, 9, 0);
INSERT INTO `product_spec_option` VALUES (18, '2', 0.00, 10, 1);
INSERT INTO `product_spec_option` VALUES (19, 'gfh', 0.00, 11, 1);
INSERT INTO `product_spec_option` VALUES (20, '4324', 0.00, 11, 1);
INSERT INTO `product_spec_option` VALUES (21, '1', 0.00, 12, 1);
INSERT INTO `product_spec_option` VALUES (22, '2', 0.00, 13, 1);
INSERT INTO `product_spec_option` VALUES (23, '3', 0.00, 14, 1);
INSERT INTO `product_spec_option` VALUES (24, '4', 0.00, 15, 1);
INSERT INTO `product_spec_option` VALUES (25, '5', 0.00, 16, 1);
INSERT INTO `product_spec_option` VALUES (26, 'sdf', 0.00, 17, 1);
INSERT INTO `product_spec_option` VALUES (27, '菠萝', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (28, '西瓜', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (29, '桃子', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (30, '1', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (31, '2', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (32, '3', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (33, '4', 0.00, 5, 0);
INSERT INTO `product_spec_option` VALUES (34, '5', 0.00, 5, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_spec_sort
-- ----------------------------
INSERT INTO `product_spec_sort` VALUES (1, '分量', 13, 1);
INSERT INTO `product_spec_sort` VALUES (4, '分量', 15, 0);
INSERT INTO `product_spec_sort` VALUES (5, '口味', 15, 0);
INSERT INTO `product_spec_sort` VALUES (6, '口味', 1, 0);
INSERT INTO `product_spec_sort` VALUES (7, '口味', 1, 1);
INSERT INTO `product_spec_sort` VALUES (8, '口味', 1, 1);
INSERT INTO `product_spec_sort` VALUES (9, '品种', 1, 0);
INSERT INTO `product_spec_sort` VALUES (10, '2', 1, 1);
INSERT INTO `product_spec_sort` VALUES (11, 'fds', 3, 1);
INSERT INTO `product_spec_sort` VALUES (12, '1', 1, 1);
INSERT INTO `product_spec_sort` VALUES (13, '2', 1, 1);
INSERT INTO `product_spec_sort` VALUES (14, '3', 1, 1);
INSERT INTO `product_spec_sort` VALUES (15, '4', 1, 1);
INSERT INTO `product_spec_sort` VALUES (16, '5', 1, 1);
INSERT INTO `product_spec_sort` VALUES (17, 'asdf', 1, 1);

-- ----------------------------
-- Table structure for r_product_inventoty
-- ----------------------------
DROP TABLE IF EXISTS `r_product_inventoty`;
CREATE TABLE `r_product_inventoty`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) NULL DEFAULT NULL,
  `inventory_id` bigint(11) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL COMMENT '所需个数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_product_inventoty
-- ----------------------------
INSERT INTO `r_product_inventoty` VALUES (7, 4, 2, 1);
INSERT INTO `r_product_inventoty` VALUES (8, 6, 2, 1);
INSERT INTO `r_product_inventoty` VALUES (9, 3, 6, 10);
INSERT INTO `r_product_inventoty` VALUES (10, 3, 8, 10);

SET FOREIGN_KEY_CHECKS = 1;
