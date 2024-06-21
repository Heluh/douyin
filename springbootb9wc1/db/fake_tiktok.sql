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

 Date: 21/06/2024 13:57:42
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `video_id` bigint(20) NOT NULL,
  `watch_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `video_id`(`video_id`) USING BTREE,
  INDEX `user_video_users_id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `user_video_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_video_videos_id_fk` FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_video
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1718937478893 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `users` VALUES (1718796999467, '2024-06-19 19:36:38', 'testuser', '$2a$10$JzdwFxJcHrhnNzo.uaXX3uJPXHmPtddrnyPDWbptfGU8O77/GNj8y', 'Test User', 'male', '', '13800138000');
INSERT INTO `users` VALUES (1718797082062, '2024-06-19 19:38:01', 'jiejie', '$2a$10$ryHczT9DSrK2WbrNQRjvPOHD7SPlvArDVfKG1TEjOE0BgGRUaufDG', 'tony', '男', NULL, '13456789000');
INSERT INTO `users` VALUES (1718870215253, '2024-06-20 15:56:54', 'clearlove', '$2a$10$s5KUH6nBYP1ooAAu67LkWu86LFU.ZCfMJ8LJ363IvJhPyiKlGX0nm', 'jackson', '男', NULL, '1662168909');
INSERT INTO `users` VALUES (1718875756025, '2024-06-20 17:29:14', 'meiko', '$2a$10$3hXxMkIjMGlPgcK9a15b0.OYxGmMK4BWM/mhnIvAxJBKW3qgzPuJW', 'clause', '女', NULL, '1662168909');
INSERT INTO `users` VALUES (1718879133795, '2024-06-20 18:25:32', 'jie', '$2a$10$NxzKnJhSYd.8P4KWtmXeguLwewZ/M.zFGSnsvYZ6SIp4rivAStCDq', 'hmx', '男', NULL, '12345676868');
INSERT INTO `users` VALUES (1718937478892, '2024-06-21 10:37:59', 'wsk', '$2a$10$xBJPwZJLhGcIdY7HYhwbhOyscmof4s7NKahmuiYue5zHQLL5dXTTa', 'wsk', '男', NULL, '15713287921');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of video_likes
-- ----------------------------
INSERT INTO `video_likes` VALUES (1, 0, 1718879133795, 1, '2024-06-21 13:48:19');

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
  `artist_id` bigint(20) NULL DEFAULT NULL COMMENT '作者id',
  `like_count` bigint(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '点赞数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1804030613191397378 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES (0, '2024-06-21 13:19:15', '3c41bce4c95258d4ef646f822d180805.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885579009_3c41bce4c95258d4ef646f822d180805.mp4', '被艾特看双重晚霞真的是一件浪漫且幸福的事', '2024-06-20', 1717298167268, 0000000001);
INSERT INTO `videos` VALUES (1, '2024-06-21 13:19:15', '7cb3dbb1cea5f2bf4c35c39052895114.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885649682_7cb3dbb1cea5f2bf4c35c39052895114.mp4', '一不小心误入了仙境', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (2, '2024-06-21 13:19:15', '0434d938e8689792641191cc4af9b96d.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885704988_0434d938e8689792641191cc4af9b96d.mp4', '一不小心误入了仙境', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (3, '2024-06-21 13:19:15', '220114_01_Drone_4k_017.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718886170945_220114_01_Drone_4k_017.mp4', '简单描述一下你的光', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (4, '2024-06-21 13:19:15', '327-1_327-0525.mov', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718886213603_327-1_327-0525.mov', '美丽的风景', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (1804030613191397377, '2024-06-21 13:56:30', '3c41bce4c95258d4ef646f822d180805.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718949389961_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718949388847_3c41bce4c95258d4ef646f822d180805.mp4', '十分美丽的风景', '2024-06-21', 1717298167268, 0000000000);

SET FOREIGN_KEY_CHECKS = 1;
