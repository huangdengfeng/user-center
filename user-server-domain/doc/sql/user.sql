CREATE SCHEMA `user_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

CREATE TABLE `t_user` (
  `uid` bigint NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `status` tinyint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_oauth` (
  `uid` bigint NOT NULL,
  `oauth_type` tinyint NOT NULL COMMENT 'auth类型',
  `oauth_id` varchar(100) NOT NULL,
  `union_id` varchar(100) DEFAULT NULL,
  `status` tinyint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uid`,`oauth_type`,`oauth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_relation` (
  `uid` bigint NOT NULL,
  `relation_type` tinyint NOT NULL,
  `relation_value` varchar(100) NOT NULL,
  `status` tinyint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uid`,`relation_type`,`relation_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_user_profile` (
  `uid` bigint NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
