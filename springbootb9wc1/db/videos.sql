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

 Date: 21/06/2024 14:00:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES (0, '2024-06-21 13:19:15', '3c41bce4c95258d4ef646f822d180805.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885579009_3c41bce4c95258d4ef646f822d180805.mp4', '被艾特看双重晚霞真的是一件浪漫且幸福的事', '2024-06-20', 1717298167268, 0000000001);
INSERT INTO `videos` VALUES (1, '2024-06-21 13:19:15', '7cb3dbb1cea5f2bf4c35c39052895114.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885649682_7cb3dbb1cea5f2bf4c35c39052895114.mp4', '一不小心误入了仙境', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (2, '2024-06-21 13:19:15', '0434d938e8689792641191cc4af9b96d.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718885704988_0434d938e8689792641191cc4af9b96d.mp4', '一不小心误入了仙境', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (3, '2024-06-21 13:19:15', '220114_01_Drone_4k_017.mp4', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718886170945_220114_01_Drone_4k_017.mp4', '简单描述一下你的光', '2024-06-20', 1717298167268, 0000000000);
INSERT INTO `videos` VALUES (4, '2024-06-21 13:19:15', '327-1_327-0525.mov', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718884693632_1.jpg', 'https://fake-tiktok.oss-cn-beijing.aliyuncs.com/1718886213603_327-1_327-0525.mov', '美丽的风景', '2024-06-20', 1717298167268, 0000000000);

SET FOREIGN_KEY_CHECKS = 1;
