CREATE SCHEMA `distributed_id` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

DROP TABLE IF EXISTS `distributed_id`.`distributed_id`;
CREATE TABLE `distributed_id`.`distributed_id` (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务标识',
  `token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '认证码',
  `max_id` bigint NOT NULL DEFAULT '1' COMMENT '当前值',
  `step` int NOT NULL COMMENT '步长',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `distributed_id.distributed_id` (`biz_tag`,`token`,`max_id`,`step`,`description`,`create_time`,`update_time`) VALUES ('uid',NULL,1000000000,10,'uid','2023-05-21 22:19:49','2023-05-21 22:48:03');
