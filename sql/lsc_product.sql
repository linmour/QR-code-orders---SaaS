/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_product

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/11/2023 22:12:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for non_value_spec
-- ----------------------------
DROP TABLE IF EXISTS `non_value_spec`;
CREATE TABLE `non_value_spec`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sort_id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of non_value_spec
-- ----------------------------
INSERT INTO `non_value_spec` VALUES (1, 3, '少汤', 0);
INSERT INTO `non_value_spec` VALUES (2, 3, '多汤', 0);
INSERT INTO `non_value_spec` VALUES (3, 4, '艾草', 0);
INSERT INTO `non_value_spec` VALUES (4, 4, '当归', 0);

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '/*、',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名字',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `value_spec` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否有规格',
  `non_value_spec` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否有规格',
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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '小春鸡', 1, '好吃', 1, 1, 0, 5, 'http://120.79.7.243:9000/linmourscanorder/product/1/1/e76dfa62585049bba88a7f802e121118.jpg', 32.00, '2023-09-17 17:53:52', '2023-09-17 17:53:52', 0, 1, 1);
INSERT INTO `product_info` VALUES (3, '1', 1, '111', 0, 0, 1, 1, 'http://120.79.7.243:9000/linmourscanorder/product/1/1/51b9dcf6c7a74c9baef30fd26d7de803.jpg,http://120.79.7.243:9000/linmourscanorder/product/1/1/da25aa04ae4b4f4582316e4e350ca6b4.png,http://120.79.7.243:9000/linmourscanorder/product/1/1/93b172bff7174fd2b08cc49dca8ed927.png,http://120.79.7.243:9000/linmourscanorder/product/1/1/ec498ffae0704fe1a050e28e1962e356.jpg', 1111.00, '2023-10-14 15:48:21', '2023-10-14 15:48:21', 0, 1, 1);
INSERT INTO `product_info` VALUES (4, '2', 1, '22', 0, 0, 1, 2, '', 22.00, '2023-10-14 15:57:42', '2023-10-14 15:57:42', 0, 1, 1);
INSERT INTO `product_info` VALUES (5, '3', 1, '22', 0, 0, 1, 3, 'http://120.79.7.243:9000/linmourscanorder/product/1/1/d0851fe988a24e9aa735a37c25aef84d.jpg', 22.00, '2023-10-14 15:59:18', '2023-10-15 08:22:45', 0, 1, 1);
INSERT INTO `product_info` VALUES (6, '4', 1, '66', 0, 0, 1, 4, 'http://120.79.7.243:9000/linmourscanorder/product/1/1/3bf40d69f6ab48438e6f1799d8b88e40.png', 66.00, '2023-10-14 16:30:55', '2023-10-15 08:23:08', 0, 1, 1);
INSERT INTO `product_info` VALUES (7, '5', 1, '66', 0, 0, 1, 5, 'http://120.79.7.243:9000/linmourscanorder/product/1/1/b4ab47dd30494e51a32e2d78eaa1acf8.jpg,http://120.79.7.243:9000/linmourscanorder/product/1/1/436392d1d00a440eaf2ab9d92a40f4af.jpg,http://120.79.7.243:9000/linmourscanorder/product/1/1/56d49d573321430ca646976e8ac21ccb.png', 666.00, '2023-10-14 16:33:04', '2023-10-14 16:33:04', 0, 1, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_inventory
-- ----------------------------
INSERT INTO `product_inventory` VALUES (1, '鸡', 1, '只', 1, 1, 0, 0, '2023-09-17 17:53:56', '2023-09-17 17:53:56', 1, 1, 0);

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
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_sort
-- ----------------------------
INSERT INTO `product_sort` VALUES (1, '主食', 1, '2023-07-28 06:21:36', '2023-07-28 06:21:36', 1, 1, 0);
INSERT INTO `product_sort` VALUES (2, '饮品', 1, '2023-07-28 08:51:20', '2023-07-28 08:51:20', 1, 1, 0);
INSERT INTO `product_sort` VALUES (3, '甜点', 1, '2023-07-28 08:51:49', '2023-07-28 08:51:49', 1, 1, 0);
INSERT INTO `product_sort` VALUES (4, '本店招牌', 1, '2023-07-28 08:53:22', '2023-07-28 08:53:22', 1, 1, 0);
INSERT INTO `product_sort` VALUES (5, '热门产品', 1, '2023-07-28 08:58:45', '2023-07-28 08:58:45', 1, 1, 0);

-- ----------------------------
-- Table structure for r_product_non_value_spec
-- ----------------------------
DROP TABLE IF EXISTS `r_product_non_value_spec`;
CREATE TABLE `r_product_non_value_spec`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) UNSIGNED NOT NULL,
  `non_value_id` bigint(20) UNSIGNED NOT NULL,
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_product_non_value_spec
-- ----------------------------
INSERT INTO `r_product_non_value_spec` VALUES (1, 1, 1, 0);
INSERT INTO `r_product_non_value_spec` VALUES (2, 1, 2, 0);
INSERT INTO `r_product_non_value_spec` VALUES (3, 1, 3, 0);
INSERT INTO `r_product_non_value_spec` VALUES (4, 1, 4, 0);

-- ----------------------------
-- Table structure for r_product_value_spec
-- ----------------------------
DROP TABLE IF EXISTS `r_product_value_spec`;
CREATE TABLE `r_product_value_spec`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) UNSIGNED NOT NULL,
  `value_spec_id` bigint(20) UNSIGNED NOT NULL,
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_product_value_spec
-- ----------------------------
INSERT INTO `r_product_value_spec` VALUES (1, 1, 1, 0);
INSERT INTO `r_product_value_spec` VALUES (2, 1, 2, 0);
INSERT INTO `r_product_value_spec` VALUES (3, 1, 3, 0);
INSERT INTO `r_product_value_spec` VALUES (4, 1, 4, 0);

-- ----------------------------
-- Table structure for spec_sort
-- ----------------------------
DROP TABLE IF EXISTS `spec_sort`;
CREATE TABLE `spec_sort`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spec_sort
-- ----------------------------
INSERT INTO `spec_sort` VALUES (1, '大小', 0);
INSERT INTO `spec_sort` VALUES (2, '种类', 0);
INSERT INTO `spec_sort` VALUES (3, '汤汁', 0);
INSERT INTO `spec_sort` VALUES (4, '配料', 0);

-- ----------------------------
-- Table structure for value_spec
-- ----------------------------
DROP TABLE IF EXISTS `value_spec`;
CREATE TABLE `value_spec`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sort_id` bigint(20) UNSIGNED NOT NULL COMMENT '属于那种选项',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选择名',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '选项价格',
  `deleted` int(1) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of value_spec
-- ----------------------------
INSERT INTO `value_spec` VALUES (1, 1, '大份', 5.00, 0);
INSERT INTO `value_spec` VALUES (2, 1, '小份', 3.00, 0);
INSERT INTO `value_spec` VALUES (3, 2, '乌鸡', 18.00, 0);
INSERT INTO `value_spec` VALUES (4, 2, '普通鸡', 13.00, 0);

SET FOREIGN_KEY_CHECKS = 1;
