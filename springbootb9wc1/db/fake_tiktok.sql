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

 Date: 19/06/2024 01:50:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint(20) NOT NULL COMMENT '关联表id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `nickname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `reply` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频信息评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (61, '2022-07-27 21:35:18', 1, 1, '用户名1', '评论内容1', '回复内容1');
INSERT INTO `comment` VALUES (62, '2022-07-27 21:35:18', 2, 2, '用户名2', '评论内容2', '回复内容2');
INSERT INTO `comment` VALUES (63, '2022-07-27 21:35:18', 3, 3, '用户名3', '评论内容3', '回复内容3');
INSERT INTO `comment` VALUES (64, '2022-07-27 21:35:18', 4, 4, '用户名4', '评论内容4', '回复内容4');
INSERT INTO `comment` VALUES (65, '2022-07-27 21:35:18', 5, 5, '用户名5', '评论内容5', '回复内容5');
INSERT INTO `comment` VALUES (66, '2022-07-27 21:35:18', 6, 6, '用户名6', '评论内容6', '回复内容6');

-- ----------------------------
-- Table structure for storeup
-- ----------------------------
DROP TABLE IF EXISTS `storeup`;
CREATE TABLE `storeup`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `refid` bigint(20) NULL DEFAULT NULL COMMENT '收藏id',
  `tablename` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏名称',
  `picture` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏图片',
  `type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '类型(1:收藏,21:赞,22:踩)',
  `inteltype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of storeup
-- ----------------------------

-- ----------------------------
-- Table structure for user_video
-- ----------------------------
DROP TABLE IF EXISTS `user_video`;
CREATE TABLE `user_video`  (
  `user_id` bigint(20) NOT NULL,
  `video_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `video_id`) USING BTREE,
  INDEX `video_id`(`video_id`) USING BTREE,
  CONSTRAINT `user_video_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_video_ibfk_2` FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_video
-- ----------------------------
INSERT INTO `user_video` VALUES (11, 31);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `yonghuming`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717323412959 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (11, '2022-07-27 21:35:18', '123456', '123456', '姓名1', '男', 'upload/yonghu_touxiang1.jpg', '13823888881');
INSERT INTO `users` VALUES (12, '2022-07-27 21:35:18', '用户名2', '123456', '姓名2', '男', 'upload/yonghu_touxiang2.jpg', '13823888882');
INSERT INTO `users` VALUES (13, '2022-07-27 21:35:18', '用户名3', '123456', '姓名3', '男', 'upload/yonghu_touxiang3.jpg', '13823888883');
INSERT INTO `users` VALUES (14, '2022-07-27 21:35:18', '用户名4', '123456', '姓名4', '男', 'upload/yonghu_touxiang4.jpg', '13823888884');
INSERT INTO `users` VALUES (15, '2022-07-27 21:35:18', '用户名5', '123456', '姓名5', '男', 'upload/yonghu_touxiang5.jpg', '13823888885');
INSERT INTO `users` VALUES (16, '2022-07-27 21:35:18', '用户名6', '123456', '姓名6', '男', 'upload/yonghu_touxiang6.jpg', '13823888886');
INSERT INTO `users` VALUES (1717062169437, '2024-05-30 17:42:49', '12345', '$2a$10$ni7s4Z41caXiOKnFdIdbCOrPLFjpwNfosknIyfZZ949qykNkQjUXG', '', '', '', '');
INSERT INTO `users` VALUES (1717298167268, '2024-06-02 11:16:07', '1234567', '$2a$10$PBtV662SavJncJmf8mkVqusQIWlkgozmvNJfR/asps0hXw4LOW4.G', '1', NULL, NULL, NULL);
INSERT INTO `users` VALUES (1717319730576, '2024-06-02 17:15:30', '12345678', '$2a$10$iJ9dTnKZ8agOGiRPZaSgM.wmPBiNy9D4cvZnhAcRNzw3w/6UMI76e', '1', NULL, NULL, NULL);
INSERT INTO `users` VALUES (1717323412958, '2024-06-02 18:16:53', '12345679', '$2a$10$UrGSNilXuygWPpvISsP1MexWRY3E83EkAxV0AhGx/yH1hUg3bY.1S', '12345', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for video_likes
-- ----------------------------
DROP TABLE IF EXISTS `video_likes`;
CREATE TABLE `video_likes`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `liked` tinyint(1) NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `video_id`(`video_id`, `user_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `video_likes_ibfk_1` FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `video_likes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of video_likes
-- ----------------------------

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频名称',
  `cover` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频封面',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频内容',
  `introduction` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频介绍',
  `release_date` date NULL DEFAULT NULL COMMENT '发布日期',
  `click_time` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `artist_id` bigint(20) NULL DEFAULT NULL COMMENT '作者id',
  `like_count` bigint(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '点赞数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES (31, '2024-05-29 17:45:57', '视频名称1', 'upload/shipinxinxi_shipinfengmian1.jpg', '/videos/a.mp4', '视频介绍1', '2022-07-27', '2022-07-27 21:35:18', 11, 0000000021);
INSERT INTO `videos` VALUES (32, '2024-05-29 17:45:58', '视频名称2', 'upload/shipinxinxi_shipinfengmian2.jpg', '/videos/b.mp4', '视频介绍2', '2022-07-27', '2022-07-27 21:35:18', 12, 0000000015);
INSERT INTO `videos` VALUES (33, '2024-05-29 17:45:59', '视频名称3', 'upload/shipinxinxi_shipinfengmian3.jpg', '/videos/c.mp4', '视频介绍3', '2022-07-27', '2022-07-27 21:35:18', 13, 0000000004);
INSERT INTO `videos` VALUES (34, '2024-05-29 17:45:59', '视频名称4', 'upload/shipinxinxi_shipinfengmian4.jpg', '/videos/a.mp4', '视频介绍4', '2022-07-27', '2022-07-27 21:35:18', 14, 0000000004);
INSERT INTO `videos` VALUES (35, '2024-05-29 17:46:00', '视频名称5', 'upload/shipinxinxi_shipinfengmian5.jpg', '/videos/b.mp4', '视频介绍5', '2022-07-27', '2022-07-27 21:35:18', 15, 0000000026);
INSERT INTO `videos` VALUES (36, '2024-06-02 19:50:46', '视频名称6', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000002);
INSERT INTO `videos` VALUES (37, '2024-06-02 19:36:20', '视频名称7', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000002);
INSERT INTO `videos` VALUES (38, '2024-06-02 19:36:15', '视频名称8', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000016);
INSERT INTO `videos` VALUES (39, '2024-06-02 19:36:14', '视频名称9', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (40, '2024-06-02 19:36:12', '视频名称10', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (41, '2024-06-02 19:36:09', '视频名称11', 'upload/shipinxinxi_shipinfengmian6.jpg', '/videos/c.mp4', '视频介绍6', '2022-07-27', '2022-07-27 21:35:18', 1717298167268, 0000000002);

SET FOREIGN_KEY_CHECKS = 1;
