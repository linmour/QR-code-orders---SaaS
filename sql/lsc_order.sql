/*
 Navicat Premium Data Transfer

 Source Server         : 120.79.7.243
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 120.79.7.243:3306
 Source Schema         : lsc_order

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 27/03/2024 10:03:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '微信唯一id',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '桌号',
  `pay_type` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '支付方式 1支付宝 2微信 3积分 4线下',
  `pay_status` int(10) UNSIGNED NULL DEFAULT 2 COMMENT '支付状态 1已支付 2未支付 3已取消',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `order_status` int(10) UNSIGNED NULL DEFAULT 3 COMMENT '订单状态 1已完成 2进行中 3待处理',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `shop_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `deleted` tinyint(3) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1772654011485257728', 'oIhII43o-BEydV8TmNDRTvFy_Lx0', 2, 4, 1, 90.00, 3, '', 1, '2024-03-27 00:00:44', '2024-03-27 00:00:44', 1, 1, 0);
INSERT INTO `order_info` VALUES ('1772658180719902720', 'oIhII43o-BEydV8TmNDRTvFy_Lx0', 2, 4, 1, 50.00, 3, '', 1, '2024-03-27 00:14:13', '2024-03-27 00:14:13', 1, 1, 0);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `order_id` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
  `quantity` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '数量',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort_id` bigint(20) NULL DEFAULT NULL,
  `props_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `shop_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '所属商户',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `update_by` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `deleted` int(10) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1772658322948833286 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1772654952921972737, '汉堡', '1772654011485257728', 1, NULL, 1, NULL, '主食', 18.00, 3, 1, '2024-03-27 00:00:50', '2024-03-27 00:00:50', 1, 1, 0);
INSERT INTO `order_item` VALUES (1772654952921972738, '薯条', '1772654011485257728', 1, NULL, 2, NULL, '饮品', 22.00, 4, 1, '2024-03-27 00:00:50', '2024-03-27 00:00:50', 1, 1, 0);
INSERT INTO `order_item` VALUES (1772658322948833282, '汉堡', '1772658180719902720', 1, NULL, 1, NULL, '主食', 18.00, 3, 1, '2024-03-27 00:14:13', '2024-03-27 00:14:13', 1, 1, 0);
INSERT INTO `order_item` VALUES (1772658322948833283, '薯条', '1772658180719902720', 1, NULL, 2, NULL, '饮品', 22.00, 4, 1, '2024-03-27 00:14:13', '2024-03-27 00:14:13', 1, 1, 0);
INSERT INTO `order_item` VALUES (1772658322948833284, '芬达', '1772658180719902720', 1, NULL, 2, '小瓶,樱桃', '饮品', 3.00, 15, 1, '2024-03-27 00:14:13', '2024-03-27 00:14:13', 1, 1, 0);
INSERT INTO `order_item` VALUES (1772658322948833285, '芬达', '1772658180719902720', 1, NULL, 2, '大瓶,苹果', '饮品', 7.00, 15, 1, '2024-03-27 00:14:13', '2024-03-27 00:14:13', 1, 1, 0);

-- ----------------------------
-- Table structure for order_sale
-- ----------------------------
DROP TABLE IF EXISTS `order_sale`;
CREATE TABLE `order_sale`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `openid` bigint(20) UNSIGNED NOT NULL COMMENT '微信唯一id',
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
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(20) NOT NULL COMMENT '分支事务ID',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '全局事务ID',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上下文',
  `rollback_info` longblob NOT NULL COMMENT '回滚信息',
  `log_status` int(11) NOT NULL COMMENT '状态，0正常，1全局已完成',
  `log_created` datetime(6) NOT NULL COMMENT '创建时间',
  `log_modified` datetime(6) NOT NULL COMMENT '修改时间',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
