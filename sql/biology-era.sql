create database if not exists biology;

use biology;

## 用户相关表 ##
create table if not exists person_info
(
    `id`            bigint auto_increment primary key,
    `username`      varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci     null comment '用户名',
    `password`      varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '密码',
    `phone`         varchar(16) character set utf8mb4 collate utf8mb4_0900_ai_ci comment '手机号',
    `avatar`        varchar(1024) character set utf8mb4 collate utf8mb4_0900_ai_ci    not null default '/assets/default.svg' comment '头像url',
    `type`          int check ( `type` = 0 or `type` = 1) not null comment '用户类型 | 0 : 用户 | 1 : 管理员',
    `state`         int check ( `state` = 0 or `state` = 1 )                          not null default 0 comment '状态 | 0 : 正常 | 1 : 禁用',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `create_person` bigint                                                            null comment '创建人',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `update_person` bigint                                                            null comment '修改人',
    `delete` int                                                                      not null default 1 comment '0代表已删除，1代表未删除',
    foreign key (`create_person`) references person_info (`id`),
    foreign key (`update_person`) references person_info (`id`)
) engine = innodb
  auto_increment = 1000
  character set = utf8mb4
  collate utf8mb4_0900_ai_ci
    comment '用户表';
INSERT INTO person_info(username, password, phone, type) VALUES("admin","e10adc3949ba59abbe56e057f20f883e","15055530638","1");
## 系统角色表 ##
create table if not exists system_role
(
    `id`            bigint auto_increment primary key,
    `name`          varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci     null comment '角色名',
    `describe`      varchar(1024) character set utf8mb4 collate utf8mb4_0900_ai_ci    not null comment '描述',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `create_person` bigint                                                            null comment '创建人',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `update_person` bigint                                                            null comment '修改人',
    `delete` int                                                                      not null default 1 comment '0代表已删除，1代表未删除',
    foreign key (`create_person`) references system_role (`id`),
    foreign key (`update_person`) references system_role (`id`)
) engine = innodb
  auto_increment = 1000
  character set = utf8mb4
  collate utf8mb4_0900_ai_ci
    comment '系统角色表';
insert into system_role(name, `describe`)values ("用户", "用户");
insert into system_role(name,`describe`) values ("管理员","管理员");
insert into system_role(name,`describe`) values ("超级管理员","行天之道总司一切");
## 用户权限对应表 ##
create table if not exists person_role
(
    `person_id`     bigint                                                            not null comment '用户id',
    `role_id`       bigint                                                            not null comment '角色id',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `delete` int                                                                      not null default 1 comment '0代表已删除，1代表未删除',
    foreign key (`person_id`) references person_info (`id`),
    foreign key (`role_id`) references system_role (`id`)
) engine = innodb
  auto_increment = 1000
  character set = utf8mb4
  collate utf8mb4_0900_ai_ci
    comment '用户权限对应表';
insert into person_role(person_id, role_id) VALUES (1000,1002);
## 系统资源表 ##
create table if not exists system_resource
(
    `id`            bigint auto_increment primary key,
    `name`          varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci     null comment '名称',
    `url`           varchar(1024) character set utf8mb4 collate utf8mb4_0900_ai_ci    not null comment '地址',
    `identity`      varchar(1024) character set utf8mb4 collate utf8mb4_0900_ai_ci    not null comment '标识',
    `request_method`varchar(1024) character set utf8mb4 collate utf8mb4_0900_ai_ci    not null comment '请求方式',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `create_person` bigint                                                            null comment '创建人',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `update_person` bigint                                                            null comment '修改人',
    `delete` int                                                                      not null default 1 comment '0代表已删除，1代表未删除',
    foreign key (`create_person`) references system_role (`id`),
    foreign key (`update_person`) references system_role (`id`)
) engine = innodb
  auto_increment = 1000
  character set = utf8mb4
  collate utf8mb4_0900_ai_ci
    comment '系统资源表';
## 角色资源表 ##
create table if not exists system_role_resource
(
    `role_id`       bigint                                                            not null comment '角色id',
    `resource_id`   bigint                                                            not null comment '资源id',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `delete` int                                                                      not null default 1 comment '0代表已删除，1代表未删除',
    foreign key (`role_id`) references system_role (`id`),
    foreign key (`resource_id`) references system_resource (`id`)
) engine = innodb
  auto_increment = 1000
  character set = utf8mb4
  collate utf8mb4_0900_ai_ci
    comment '角色资源表';
# ai房间表
CREATE TABLE IF NOT EXISTS chat_room (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `person_id` BIGINT NOT NULL,
    `create_time`   datetime                                                          not null default current_timestamp comment '创建日期',
    `create_person` bigint                                                            null comment '创建人',
    `update_time`   datetime                                                          not null default current_timestamp on update current_timestamp comment '修改日期',
    `update_person` bigint                                                            null comment '修改人',
    foreign key (`person_id`) references person_info (`id`),
    foreign key (`create_person`) references system_role (`id`),
    foreign key (`update_person`) references system_role (`id`)
) ENGINE =innodb
  auto_increment = 1000
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci
    comment 'ai对话表';
# ai对话信息表
CREATE TABLE IF NOT EXISTS chat_message (
     `id` INT PRIMARY KEY AUTO_INCREMENT,
     `question` TEXT,
     `answer` TEXT,
     `room_id` INT NOT NULL,
     `create_time` DATETIME not null default current_timestamp comment '创建日期',
     foreign key (`room_id`) references chat_room (`id`)
) ENGINE =innodb
  auto_increment = 1000
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci
    comment 'ai对话信息表';
# 3d模型生成信息表
CREATE TABLE IF NOT EXISTS masterpiece_info (
       `id` INT PRIMARY KEY AUTO_INCREMENT,
       `person_id` BIGINT NOT NULL,
       `out_url` VARCHAR(255),
       `create_time` DATETIME not null default current_timestamp comment '创建日期',
       foreign key (`person_id`) references person_info (`id`)
) ENGINE =innodb
  auto_increment = 1000
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci
    comment '3d模型生成信息表';