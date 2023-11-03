/*
 Navicat Premium Data Transfer

 Source Server         : 43.136.73.159
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 43.136.73.159:3306
 Source Schema         : lsc_order

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/11/2023 22:12:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cus_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id，没注册为默认用户0表示',
  `table_id` bigint(20) NOT NULL COMMENT '桌号',
  `pay_type` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '支付方式 1支付宝 2微信 3积分..',
  `pay_status` int(10) UNSIGNED NOT NULL DEFAULT 2 COMMENT '支付状态 1已支付 2未支付 3已取消',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '支付金额',
  `order_status` int(10) UNSIGNED NOT NULL DEFAULT 3 COMMENT '订单状态 1已完成 2进行中 3待处理',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `shop_id` bigint(20) UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1715903496005353472', 0, 1, NULL, 2, 9010.00, 3, '', 1, '2023-10-22 09:30:48', '2023-10-22 10:08:59', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1715903715296149504', 0, 2, NULL, 2, 5424.00, 3, '', 1, '2023-10-22 09:31:40', '2023-10-22 09:36:03', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1715924276684521472', 0, 1, 1, 1, 6724.00, 3, '', 1, '2023-10-22 10:53:23', '2023-10-22 10:53:33', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1715924756605173760', 0, 1, 1, 1, 666.00, 3, '', 1, '2023-10-22 10:55:17', '2023-10-22 10:55:17', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1715929654855991296', 0, 1, 1, 1, 666.00, 3, '', 1, '2023-10-22 11:14:45', '2023-10-22 11:14:45', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1715966004997128192', 0, 2, NULL, 2, 1998.00, 3, '', 1, '2023-10-22 13:39:11', '2023-10-22 13:40:02', 1, 1, 0);

-- ----------------------------
-- Table structure for order_sale
-- ----------------------------
DROP TABLE IF EXISTS `order_sale`;
CREATE TABLE `order_sale`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `cus_id` bigint(20) UNSIGNED NOT NULL COMMENT '顾客id',
  `sale_date` datetime NOT NULL COMMENT '销售日期',
  `sale_quantity` int(10) UNSIGNED NOT NULL COMMENT '销售数量',
  `shop_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属店铺',
  `sale_amount` decimal(18, 4) NOT NULL COMMENT '销售金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  `deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_sale
-- ----------------------------

-- ----------------------------
-- Table structure for r_order_product
-- ----------------------------
DROP TABLE IF EXISTS `r_order_product`;
CREATE TABLE `r_order_product`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单id\r\n',
  `quantity` int(10) UNSIGNED NOT NULL,
  `product_id` bigint(20) UNSIGNED NOT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `update_by` bigint(20) UNSIGNED NOT NULL,
  `deleted` int(10) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_order_product
-- ----------------------------
INSERT INTO `r_order_product` VALUES (1, '1715903496005353472', 1, 7, '2023-10-22 09:30:48', '2023-10-22 09:30:48', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (2, '1715903496005353472', 2, 1, '2023-10-22 09:30:48', '2023-10-22 09:30:48', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (3, '1715903496005353472', 1, 7, '2023-10-22 09:31:13', '2023-10-22 09:31:13', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (4, '1715903496005353472', 1, 1, '2023-10-22 09:31:13', '2023-10-22 09:31:13', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (5, '1715903496005353472', 1, 7, '2023-10-22 09:31:24', '2023-10-22 09:31:24', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (6, '1715903715296149504', 1, 7, '2023-10-22 09:31:40', '2023-10-22 09:31:40', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (7, '1715903715296149504', 2, 7, '2023-10-22 09:31:56', '2023-10-22 09:31:56', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (8, '1715903715296149504', 2, 1, '2023-10-22 09:31:56', '2023-10-22 09:31:56', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (9, '1715903496005353472', 1, 7, '2023-10-22 09:32:48', '2023-10-22 09:32:48', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (10, '1715903496005353472', 1, 1, '2023-10-22 09:32:51', '2023-10-22 09:32:51', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (11, '1715903715296149504', 1, 7, '2023-10-22 09:33:06', '2023-10-22 09:33:06', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (12, '1715903715296149504', 1, 1, '2023-10-22 09:33:09', '2023-10-22 09:33:09', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (13, '1715903715296149504', 2, 7, '2023-10-22 09:33:15', '2023-10-22 09:33:15', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (14, '1715903496005353472', 1, 7, '2023-10-22 09:33:23', '2023-10-22 09:33:23', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (15, '1715903496005353472', 1, 1, '2023-10-22 09:33:32', '2023-10-22 09:33:32', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (16, '1715903496005353472', 1, 1, '2023-10-22 09:35:56', '2023-10-22 09:35:56', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (17, '1715903715296149504', 1, 7, '2023-10-22 09:36:00', '2023-10-22 09:36:00', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (18, '1715903715296149504', 1, 7, '2023-10-22 09:36:03', '2023-10-22 09:36:03', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (19, '1715903496005353472', 3, 1, '2023-10-22 09:36:08', '2023-10-22 09:36:08', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (20, '1715903496005353472', 2, 7, '2023-10-22 09:59:15', '2023-10-22 09:59:15', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (21, '1715903496005353472', 1, 1, '2023-10-22 09:59:15', '2023-10-22 09:59:15', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (22, '1715903496005353472', 1, 7, '2023-10-22 10:02:46', '2023-10-22 10:02:46', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (23, '1715903496005353472', 1, 7, '2023-10-22 10:03:44', '2023-10-22 10:03:44', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (24, '1715903496005353472', 1, 1, '2023-10-22 10:06:45', '2023-10-22 10:06:45', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (25, '1715903496005353472', 1, 7, '2023-10-22 10:06:45', '2023-10-22 10:06:45', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (26, '1715903496005353472', 1, 7, '2023-10-22 10:07:13', '2023-10-22 10:07:13', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (27, '1715903496005353472', 1, 7, '2023-10-22 10:07:19', '2023-10-22 10:07:19', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (28, '1715903496005353472', 1, 7, '2023-10-22 10:08:59', '2023-10-22 10:08:59', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (29, '1715924276684521472', 1, 1, '2023-10-22 10:53:23', '2023-10-22 10:53:23', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (30, '1715924276684521472', 5, 7, '2023-10-22 10:53:23', '2023-10-22 10:53:23', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (31, '1715924276684521472', 1, 1, '2023-10-22 10:53:33', '2023-10-22 10:53:33', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (32, '1715924276684521472', 5, 7, '2023-10-22 10:53:33', '2023-10-22 10:53:33', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (33, '1715924756605173760', 1, 7, '2023-10-22 10:55:17', '2023-10-22 10:55:17', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (34, '1715929654855991296', 1, 7, '2023-10-22 11:14:45', '2023-10-22 11:14:45', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (35, '1715966004997128192', 2, 7, '2023-10-22 13:39:12', '2023-10-22 13:39:12', 1, 1, 0);
INSERT INTO `r_order_product` VALUES (36, '1715966004997128192', 1, 7, '2023-10-22 13:40:02', '2023-10-22 13:40:02', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
