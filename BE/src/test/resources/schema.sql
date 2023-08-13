DROP TABLE IF EXISTS assignee;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS issue_comment;
DROP TABLE IF EXISTS assigned_label;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS milestone;

CREATE TABLE assignee (
    id         bigint NOT NULL AUTO_INCREMENT,
    issue_id   bigint NOT NULL,
    member_id  bigint NOT NULL,
    PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE issue (
   id             bigint NOT      NULL AUTO_INCREMENT,
   title          varchar(100)    NOT NULL,
   content        varchar(3000),
   is_open        tinyint(1)      NOT NULL,
   create_at      datetime        NOT NULL,
   milestone_id   bigint,
   author_id      bigint          NOT NULL,
   is_deleted     tinyint(1)      NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE issue_comment (
   id             bigint          NOT NULL AUTO_INCREMENT,
   content        varchar(3000)   NOT NULL,
   create_at      datetime        NOT NULL,
   author_id      bigint          NOT NULL,
   issue_id       bigint          NOT NULL,
   is_deleted     tinyint(1)      NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE assigned_label (
    id           bigint    NOT NULL AUTO_INCREMENT,
    issue_id     bigint    NOT NULL,
    label_id     bigint    NOT NULL,
    PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE label (
   id           bigint        NOT NULL AUTO_INCREMENT,
   title        varchar(50)   NOT NULL,
   description  varchar(100)  DEFAULT NULL,
   color        varchar(7)    NOT NULL,
   is_deleted     tinyint(1)  NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE member (
    id                bigint           NOT NULL AUTO_INCREMENT,
    email             varchar(200)     NOT NULL,
    password          varchar(12)      NOT NULL,
    nickname          varchar(16)      NOT NULL,
    profile_image_url varchar(1000),
    PRIMARY KEY (id)
)ENGINE=memory;

CREATE TABLE milestone (
   id             bigint          NOT NULL AUTO_INCREMENT,
   title          varchar(50)     NOT NULL,
   description    varchar(100),
   deadline       date            NOT NULL,
   is_open        tinyint(1)      NOT NULL,
   is_deleted     tinyint(1)      NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
)ENGINE=memory;
