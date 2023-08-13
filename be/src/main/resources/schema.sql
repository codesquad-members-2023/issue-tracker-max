DROP Table if exists comment;
DROP Table if exists issue_assignee;
DROP Table if exists issue_label;
DROP Table if exists issue;
DROP Table if exists label;
DROP Table if exists milestone;
DROP Table if exists refresh_token;
DROP Table if exists member;
DROP Table if exists file;

CREATE TABLE member
(
    id       bigint AUTO_INCREMENT,
    email    varchar(255) NOT NULL UNIQUE,
    name     varchar(50)  NOT NULL,
    password varchar(50)  NOT NULL,
    profile  varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE milestone
(
    id          bigint AUTO_INCREMENT,
    name        varchar(50) NOT NULL,
    due_date    date        NOT NULL,
    description varchar(2000),
    is_open     boolean     NOT NULL default TRUE,
    is_deleted  boolean     NOT NULL default FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE label
(
    id               bigint AUTO_INCREMENT,
    name             varchar(50) NOT NULL,
    description      varchar(255),
    background_color varchar(20) NOT NULL,
    text_color       varchar(20) NOT NULL,
    is_deleted       boolean     NOT NULL default FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE issue_label
(
    id       bigint AUTO_INCREMENT,
    issue_id bigint NOT NULL,
    label_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE issue_assignee
(
    id          bigint AUTO_INCREMENT,
    issue_id    bigint NOT NULL,
    assignee_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE file
(
    id  bigint AUTO_INCREMENT,
    url varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE issue
(
    id           bigint AUTO_INCREMENT,
    author_id    bigint      NOT NULL,
    milestone_id bigint,
    title        varchar(50) NOT NULL,
    is_open      boolean     NOT NULL default TRUE,
    is_deleted   boolean     NOT NULL default FALSE,
    created_at   timestamp            default current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id         bigint AUTO_INCREMENT,
    issue_id   bigint       NOT NULL,
    author_id  bigint       NOT NULL,
    file_id    bigint,
    contents   varchar(255) NOT NULL,
    is_deleted boolean      NOT NULL default FALSE,
    created_at timestamp             default CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE refresh_token
(
    id               bigint AUTO_INCREMENT,
    refresh_token    varchar(2000),
    member_id        bigint,
    PRIMARY KEY (id)
);

ALTER TABLE issue
    ADD CONSTRAINT fk_issue_author_id FOREIGN KEY (author_id) REFERENCES member (id);

ALTER TABLE issue
    ADD CONSTRAINT fk_issue_milestone_id FOREIGN KEY (milestone_id) REFERENCES milestone (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_author_id FOREIGN KEY (author_id) REFERENCES member (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_file_id FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE issue_label
    ADD CONSTRAINT fk_issue_label_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE issue_label
    ADD CONSTRAINT fk_issue_label_label_id FOREIGN KEY (label_id) REFERENCES label (id);

ALTER TABLE issue_assignee
    ADD CONSTRAINT fk_issue_assignee_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE issue_assignee
    ADD CONSTRAINT fk_issue_assignee_assignee_id FOREIGN KEY (assignee_id) REFERENCES member (id);

ALTER TABLE refresh_token
    ADD CONSTRAINT fk_refresh_token_member_id FOREIGN KEY (member_id) REFERENCES member (id);
