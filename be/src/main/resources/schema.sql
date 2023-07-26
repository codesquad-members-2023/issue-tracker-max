DROP Table if exists comment;
DROP Table if exists issue_label;
DROP Table if exists issue;
DROP Table if exists label;
DROP Table if exists milestone;
DROP Table if exists member;
DROP Table if exists file;

CREATE TABLE member
(
    id       bigint AUTO_INCREMENT,
    email    varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE milestone
(
    id          bigint AUTO_INCREMENT,
    name        varchar(50) NOT NULL,
    due_date    date        NOT NULL,
    description varchar(255),
    status      varchar(20) NOT NULL default 'open',
    deleted     boolean     NOT NULL default FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE label
(
    id               bigint AUTO_INCREMENT,
    name             varchar(50) NOT NULL,
    description      varchar(255),
    background_color varchar(20) NOT NULL,
    text_color       varchar(20) NOT NULL,
    deleted          boolean     NOT NULL default FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE issue_label
(
    id       bigint AUTO_INCREMENT,
    issue_id bigint NOT NULL,
    label_id bigint NOT NULL,
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
    id             bigint AUTO_INCREMENT,
    member_id      bigint      NOT NULL,
    PIC_id         bigint,
    milestone_id   bigint,
    title          varchar(50) NOT NULL,
    status         varchar(20) NOT NULL default 'open',
    deleted        boolean     NOT NULL default FALSE,
    created_at     timestamp            default current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id         bigint AUTO_INCREMENT,
    issue_id   bigint       NOT NULL,
    member_id  bigint       NOT NULL,
    file_id    bigint,
    contents   varchar(255) NOT NULL,
    deleted    boolean      NOT NULL default FALSE,
    created_at timestamp             default CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE issue
    ADD CONSTRAINT fk_issue_member_id FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE issue
    ADD CONSTRAINT fk_issue_PIC_id FOREIGN KEY (PIC_id) REFERENCES member (id);

ALTER TABLE issue
    ADD CONSTRAINT fk_issue_milestone_id FOREIGN KEY (milestone_id) REFERENCES milestone (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_member_id FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_file_id FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE issue_label
    ADD CONSTRAINT fk_issue_label_issue_id FOREIGN KEY (issue_id) REFERENCES issue (id);

ALTER TABLE issue_label
    ADD CONSTRAINT fk_issue_label_label_id FOREIGN KEY (label_id) REFERENCES label (id);
