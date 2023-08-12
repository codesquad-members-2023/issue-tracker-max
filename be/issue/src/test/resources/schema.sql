DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS issues;
DROP TABLE IF EXISTS labels;
DROP TABLE IF EXISTS milestones;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS assignees;
DROP TABLE IF EXISTS issues_labels;
DROP TABLE IF EXISTS tokens;

CREATE TABLE IF NOT EXISTS users (
                                     id	bigint	NOT NULL auto_increment,
                                     email	varchar(50)	NOT NULL unique,
    password	varchar(500)	NULL,
    profile_img	varchar(1000)	NULL,
    name	varchar(50)	NOT NULL,
    login_type	varchar(20)	NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS issues (
                                      id	bigint	NOT NULL auto_increment,
                                      milestone_id	bigint	NULL,
                                      user_id	bigint	NOT NULL,
                                      title	varchar(50)	NOT NULL,
    content	text	NULL,
    created_at	datetime	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    is_closed	boolean	NOT NULL	DEFAULT false,
    is_deleted	boolean	NOT NULL	DEFAULT false,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS labels (
                                      id	bigint	NOT NULL auto_increment,
                                      name	varchar(30)	NOT NULL,
    description	varchar(200)	NULL,
    background_color	varchar(30)	NOT NULL,
    text_color	varchar(30)	NOT NULL,
    is_deleted	boolean	NOT NULL	DEFAULT false,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS milestones (
                                          id	bigint	NOT NULL auto_increment,
                                          name	varchar(50)	NOT NULL,
    description	text	NULL,
    done_date	date	NULL,
    is_closed	boolean	NOT NULL	DEFAULT false,
    is_deleted	boolean	NOT NULL	DEFAULT false,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS comments (
                                        id	bigint	NOT NULL auto_increment,
                                        user_id	bigint	NOT NULL,
                                        issue_id	bigint	NOT NULL,
                                        content	text	NOT NULL,
                                        created_at	datetime	NULL	DEFAULT CURRENT_TIMESTAMP,
                                        is_deleted	boolean	NOT NULL DEFAULT false,
                                        PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS assignees (
                                         id	bigint	NOT NULL	auto_increment,
                                         user_id	bigint	NOT NULL,
                                         issue_id	bigint	NOT NULL,
                                         PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS issues_labels (
                                             id	bigint	NOT NULL auto_increment,
                                             issue_id	bigint	NOT NULL,
                                             label_id	bigint	NOT NULL,
                                             PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS tokens (
                                      id	bigint	NOT NULL auto_increment,
                                      user_id bigint NOT NULL ,
                                      refresh_token	varchar(1000)	NOT NULL,
    PRIMARY KEY(id)
    );