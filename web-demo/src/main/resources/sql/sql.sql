
/**
 * 1.用户
 */
DROP TABLE if EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `birthday` DATETIME DEFAULT NULL COMMENT '生日',
  `fan_count` INT DEFAULT 0 COMMENT '粉丝数',
  `follow_count` INT DEFAULT 0 COMMENT '关注数',
  `created_at` DATETIME DEFAULT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_NAME`(`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';




/**
 * 2.粉丝关注
 */
DROP TABLE if EXISTS `fan`;
CREATE TABLE `fan` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL COMMENT '(被关注)用户ID',
  `fan_user_id` BIGINT(20) NOT NULL COMMENT '粉丝用户ID',
  `del_flag` TINYINT(4) DEFAULT 0 COMMENT '是否删除0-未删除;1-已删除',
  `created_at` DATETIME DEFAULT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_USER_ID_FAN_USER_ID`(`user_id`,`fan_user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='粉丝关注';

