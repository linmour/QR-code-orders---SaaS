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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of restaurant_table
-- ----------------------------
INSERT INTO `restaurant_table` VALUES (1, 'q', 0, 0, 0, 2, 'http://127.0.0.1:12800/file/QR/user_1/shop_2/1.png', 0);

SET FOREIGN_KEY_CHECKS = 1;
