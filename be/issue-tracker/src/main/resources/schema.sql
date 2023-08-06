SET foreign_key_checks = 0;
DROP TABLE IF EXISTS issue_label;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS assignee;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS user;
SET foreign_key_checks = 1;

CREATE TABLE `user` (
    `user_id` bigint  NOT NULL AUTO_INCREMENT ,
    `login_id` varchar(10) NOT NULL UNIQUE ,
    `password` varchar(50)  NOT NULL ,
    `image` varchar(200)  NOT NULL ,
    PRIMARY KEY (
                 `user_id`
        )
);

CREATE TABLE `milestone` (
    `milestone_id` bigint  NOT NULL AUTO_INCREMENT,
    `name` varchar(50)  NOT NULL ,
    `deadline` datetime ,
    `description` varchar(200) ,
    `status` varchar(10)  NOT NULL DEFAULT 'open' ,
    PRIMARY KEY (
              `milestone_id`
     )
);

CREATE TABLE `label` (
    `label_id` bigint  NOT NULL AUTO_INCREMENT,
    `name` varchar(50)  NOT NULL ,
    `description` varchar(200) ,
    `background_color` varchar(10)  NOT NULL ,
    `text_color` varchar(10)  NOT NULL ,
    `is_deleted` boolean  NOT NULL DEFAULT false ,
    PRIMARY KEY (
              `label_id`
     )
);

CREATE TABLE `issue` (
    `issue_id` bigint  NOT NULL AUTO_INCREMENT ,
    `author_id` bigint  NOT NULL ,
    `milestone_id` bigint ,
    `title` varchar(50)  NOT NULL ,
    `contents` text ,
    `created_at` datetime  NOT NULL DEFAULT NOW() ,
    `status` varchar(10)  NOT NULL DEFAULT 'open' ,
    PRIMARY KEY (
              `issue_id`
     ) ,
    FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`),
    FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`milestone_id`)
);

CREATE TABLE `assignee` (
    `assignee_id` bigint NOT NULL AUTO_INCREMENT,
    `issue_id` bigint  NOT NULL ,
    `user_id` bigint  NOT NULL ,
    PRIMARY KEY (
                 `assignee_id`
        ) ,
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`issue_id`) ,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `comment` (
    `comment_id` bigint  NOT NULL AUTO_INCREMENT,
    `issue_id` bigint  NOT NULL ,
    `author_id` bigint  NOT NULL ,
    `contents` text  NOT NULL ,
    `created_at` datetime  NOT NULL DEFAULT NOW() ,
    `is_deleted` boolean  NOT NULL DEFAULT false ,
    PRIMARY KEY (
                `comment_id`
       ) ,
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`issue_id`) ,
    FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `issue_label` (
    `issue_label_id` bigint NOT NULL AUTO_INCREMENT,
    `issue_id` bigint  NOT NULL ,
    `label_id` bigint  NOT NULL ,
    PRIMARY KEY (
                `issue_label_id`
       ) ,
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`issue_id`) ,
    FOREIGN KEY (`label_id`) REFERENCES `label` (`label_id`)
);