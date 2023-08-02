drop table if exists member;
drop table if exists token;

create table member (
                        id bigint NOT NULL auto_increment,
                        login_id varchar(255),
                        password varchar(255),
                        nick_name varchar(255),
                        login_type varchar(255),
                        primary key (id)
);

create table token (
                       id bigint NOT NULL auto_increment,
                       refresh_token  varchar(255),
                       member_id varchar(255),
                       primary key (id)
);
