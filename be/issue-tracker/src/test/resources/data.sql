INSERT INTO user (login_id, password, image) VALUES ('ayaan', '1234', '이안이미지');
INSERT INTO user (login_id, password, image) VALUES ('jian', '1234', '지안이미지');
INSERT INTO user (login_id, password, image) VALUES ('bono', '1234', '보노이미지');
INSERT INTO user (login_id, password, image) VALUES ('hana', '1234', '하나이미지');
INSERT INTO user (login_id, password, image) VALUES ('khundi', '1234', '쿤디이미지');
INSERT INTO user (login_id, password, image) VALUES ('puban', '1234', '푸반이미지');

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
