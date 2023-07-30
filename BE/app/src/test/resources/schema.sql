DROP TABLE IF EXISTS assignees;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS issue_comment;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS issue_label_mapping;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS label;

CREATE TABLE assignee (
 id         bigint NOT NULL AUTO_INCREMENT,
 issue_id   bigint NOT NULL,
 member_id  bigint NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE issue (
 id             bigint NOT      NULL AUTO_INCREMENT,
 title          varchar(100)    NOT NULL,
 content        varchar(3000),
 is_open        tinyint(1)      NOT NULL,
 create_at      datetime        NOT NULL,
 milestone_id   bigint          NOT NULL,
 author_id      bigint          NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE issue_comment (
 id             bigint          NOT NULL AUTO_INCREMENT,
 content        varchar(3000)   NOT NULL,
 create_at      datetime        NOT NULL,
 author_id      bigint          NOT NULL,
 issue_id       bigint          NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE issue_label_mapping (
 id           bigint    NOT NULL AUTO_INCREMENT,
 issue_id     bigint    NOT NULL,
 label_id     bigint    NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE label (
 id           bigint        NOT NULL AUTO_INCREMENT,
 title        varchar(50)   NOT NULL,
 description  varchar(100)  DEFAULT NULL,
 color        varchar(7)    NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE member (
 id                bigint           NOT NULL AUTO_INCREMENT,
 email             varchar(200)     NOT NULL,
 password          varchar(12)      NOT NULL,
 nickname          varchar(16)      NOT NULL,
 profile_image_url varchar(1000),
 PRIMARY KEY (id)
);

CREATE TABLE milestone (
 id             bigint          NOT NULL AUTO_INCREMENT,
 title          varchar(50)     NOT NULL,
 description    varchar(100),
 deadline       date            NOT NULL,
 is_open        tinyint(1)      NOT NULL,
 PRIMARY KEY (id)
);

