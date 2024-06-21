/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50550
 Source Host           : 60.205.233.141:3306
 Source Schema         : fake_tiktok

 Target Server Type    : MySQL
 Target Server Version : 50550
 File Encoding         : 65001

 Date: 21/06/2024 15:19:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_video
-- ----------------------------
DROP TABLE IF EXISTS `user_video`;
CREATE TABLE `user_video`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `video_id` bigint(20) NOT NULL,
  `watch_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `video_id`(`video_id`) USING BTREE,
  INDEX `user_video_users_id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `user_video_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_video_videos_id_fk` FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1804051307702456323 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_video
-- ----------------------------
INSERT INTO `user_video` VALUES (1804051076449505281, 1718879133795, 2, '2024-06-21 15:17:48');
INSERT INTO `user_video` VALUES (1804051091247009793, 1718879133795, 4, '2024-06-21 15:17:52');
INSERT INTO `user_video` VALUES (1804051096389226498, 1718879133795, 2, '2024-06-21 15:17:53');
INSERT INTO `user_video` VALUES (1804051307702456322, 1718879133795, 3, '2024-06-21 15:18:44');

SET FOREIGN_KEY_CHECKS = 1;
