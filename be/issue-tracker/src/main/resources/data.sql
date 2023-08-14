INSERT INTO user (login_id, password, image) VALUES ('ayaan1234', '123456', 'https://avatars.githubusercontent.com/u/57559288?s=80&u=22fcaa63715a65dfa747506fffe592b0acbb2846&v=4');
INSERT INTO user (login_id, password, image) VALUES ('jian1234', '123456', 'https://avatars.githubusercontent.com/u/97204689?s=80&u=34888415e252f727b1d3a849e7f1387a20ce3696&v=4');
INSERT INTO user (login_id, password, image) VALUES ('bono1234', '123456', 'https://avatars.githubusercontent.com/u/70848762?s=80&v=4');
INSERT INTO user (login_id, password, image) VALUES ('hana1234', '123456', 'https://avatars.githubusercontent.com/u/117690393?s=80&u=ba9f18d1ab53f87cbe07a308e103d26d6bcbf221&v=4');
INSERT INTO user (login_id, password, image) VALUES ('khundi1234', '123456', 'https://avatars.githubusercontent.com/u/57666791?v=4');
INSERT INTO user (login_id, password, image) VALUES ('puban1234', '123456', 'https://avatars.githubusercontent.com/u/86706366?s=80&v=4');

INSERT INTO milestone (name, deadline, description) VALUES ('be-milestone1', '2023-08-04T00:00:00', '백엔드');
INSERT INTO milestone (name, deadline, description) VALUES ('be-milestone2', '2023-08-11T00:00:00', '백엔드');
INSERT INTO milestone (name, deadline, description) VALUES ('fe-milestone1', '2023-08-04T00:00:00', '프론트엔드');
INSERT INTO milestone (name, deadline, description) VALUES ('fe-milestone2', '2023-08-11T00:00:00', '프론트엔드');
INSERT INTO milestone (name, deadline) VALUES ('sprint1', NOW());

INSERT INTO label (name, description, background_color, text_color) VALUES ('be', '백엔드', '#ADC151', '#000000');
INSERT INTO label (name, description, background_color, text_color) VALUES ('fe', '프론트엔드', '#5F9EEE', '#000000');
INSERT INTO label (name, description, background_color, text_color) VALUES ('feature', '새로운 기능', '##0075ca', '#ffffff');
INSERT INTO label (name, background_color, text_color) VALUES ('docs', '#662208', '#ffffff');

INSERT INTO issue (author_id, milestone_id, title, contents) VALUES (1, 1, '기능 구현1', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠');
INSERT INTO issue (author_id, milestone_id, title, contents) VALUES (2, 1, '기능 구현2', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠');
INSERT INTO issue (author_id, milestone_id, title, contents, status) VALUES (3, 1, '기능 구현3', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠', 'deleted');
INSERT INTO issue (author_id, milestone_id, title, contents, status) VALUES (4, 1, '기능 구현4', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠', 'closed');
INSERT INTO issue (author_id, title, contents) VALUES (5, '기능 구현5', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠');
INSERT INTO issue (author_id, title, contents) VALUES (6, '기능 구현6', '내용 컨텐츠 내용 컨텐츠 내용 컨텐츠');

INSERT INTO assignee (issue_id, user_id) VALUES (1, 1);
INSERT INTO assignee (issue_id, user_id) VALUES (1, 2);
INSERT INTO assignee (issue_id, user_id) VALUES (2, 1);

INSERT INTO comment (issue_id, author_id, contents) VALUES (1, 3, '선택 미션의 경우, 원래 프로젝트 기간 동안 못하는 것이 (삐빅-)정상입니다.');
INSERT INTO comment (issue_id, author_id, contents) VALUES (1, 4, '???????????????');

INSERT INTO issue_label (issue_id, label_id) VALUES (1, 1);
INSERT INTO issue_label (issue_id, label_id) VALUES (1, 3);
INSERT INTO issue_label (issue_id, label_id) VALUES (2, 4);
