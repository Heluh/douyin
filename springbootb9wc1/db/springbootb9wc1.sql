/*
 Navicat Premium Data Transfer

 Source Server         : Mysql8.1
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : localhost:3306
 Source Schema         : springbootb9wc1

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 02/06/2024 13:14:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint NOT NULL COMMENT '关联表id',
  `userid` bigint NOT NULL COMMENT '用户id',
  `nickname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `reply` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息评论表' ROW_FORMAT = Dynamic;

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
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint NOT NULL COMMENT '用户id',
  `refid` bigint NULL DEFAULT NULL COMMENT '收藏id',
  `tablename` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏名称',
  `picture` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏图片',
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '类型(1:收藏,21:赞,22:踩)',
  `inteltype` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storeup
-- ----------------------------

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint NOT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `tablename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `role` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'token表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of token
-- ----------------------------
INSERT INTO `token` VALUES (2, 11, '123456', 'users', '用户', 'l3gxto4717og06h918rbb1ydwam4obs3', '2024-05-30 14:58:59', '2024-05-30 15:58:59');
INSERT INTO `token` VALUES (3, 1717062169437, '12345', 'users', '用户', 'd69gpaw5z7z7hxu0rwacqpck56h020yi', '2024-05-30 17:44:30', '2024-05-30 19:15:22');
INSERT INTO `token` VALUES (4, 1717298167268, '1234567', 'users', '用户', 'jx8y1n5z9go5zfnwgk34a1hs70ibe48g', '2024-06-02 11:18:43', '2024-06-02 12:18:44');

-- ----------------------------
-- Table structure for user_video
-- ----------------------------
DROP TABLE IF EXISTS `user_video`;
CREATE TABLE `user_video`  (
  `user_id` bigint NOT NULL,
  `video_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `video_id`) USING BTREE,
  INDEX `video_id`(`video_id` ASC) USING BTREE,
  CONSTRAINT `user_video_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_video_ibfk_2` FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_video
-- ----------------------------
INSERT INTO `user_video` VALUES (11, 31);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `yonghuming`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717298167269 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (11, '2022-07-27 21:35:18', '123456', '123456', '姓名1', '男', 'upload/yonghu_touxiang1.jpg', '13823888881');
INSERT INTO `users` VALUES (12, '2022-07-27 21:35:18', '1234567', '1234567', '姓名2', '女', 'upload/yonghu_touxiang2.jpg', '13823888882');
INSERT INTO `users` VALUES (1717298167268, '2024-06-02 10:44:14', 'chongbai', 'cblove1314', '张三', '男', 'upload/6e90ed56-d9c0-4b64-8d70-dfa22c314ad0/2023-05-19/c3b9e64b-8416-41ed-b198-c4d303e6c89b.jpg', '19900000000');
INSERT INTO `users` VALUES (1717298167269, '2024-06-05 09:58:08', '9r0mio1j', '1wlnph89', '李四', '男', 'upload/6e90ed56-d9c0-4b64-8d70-dfa22c314ad0/2024-06-05/8b8f1bc7-4ba0-4bde-9440-7f8765a1c484.png', '18523261153');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `file` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件',
  `clicktime` bigint NULL DEFAULT 0 COMMENT '点击量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717298991141 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES (31, '2022-07-27 21:35:18', '123456', '123456', 'upload/video1.mp4', 0);
INSERT INTO `videos` VALUES (32, '2022-07-27 21:35:18', '标题', '内容', '文件', 0);
INSERT INTO `videos` VALUES (1717298991140, '2024-06-06 09:25:52', '123', '123', 'abc', 0);
INSERT INTO `videos` VALUES (1717298991141, '2024-06-06 13:55:23', '123', '123', 'upload/6e90ed56-d9c0-4b64-8d70-dfa22c314ad0/2024-06-06/26f9a1f2-9fe4-49ff-9b06-feb5f0bcf982.mp4', 0);

SET FOREIGN_KEY_CHECKS = 1;
