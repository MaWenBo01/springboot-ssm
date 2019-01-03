
CREATE TABLE t_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_name varchar(64) NOT NULL COMMENT 'user名',
  user_password varchar(64) NOT NULL COMMENT 'user密码',
  user_address varchar(64) NOT NULL COMMENT 'user地址',
  `description` varchar(256) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(64) NOT NULL COMMENT '最后修改者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modified_time` datetime NOT NULL COMMENT '最后修改时间',
  `last_modified_db_time` datetime COMMENT '数据库最后修改时间',
  PRIMARY KEY (id),
  KEY `idx_codetable_code` (`user_name`)
);


INSERT INTO  t_user VALUES (null,'张明','123','aa','','管理员','管理员',now(),now(),now());