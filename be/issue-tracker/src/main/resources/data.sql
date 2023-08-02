INSERT INTO issuetracker.user(login_id, password, image) VALUES ('jianId', 'jian1234', 's3이미지Url');
INSERT INTO issuetracker.milestone(name) VALUES ('마일스톤1');
INSERT INTO issuetracker.issue(author_id, milestone_id, title) VALUES (1, 1, '제목1');
INSERT INTO issuetracker.issue(author_id, title) VALUES (1, '제목2');
INSERT INTO issuetracker.issue(author_id, milestone_id, title, status) VALUES (1, 1, '제목3', 'closed');
INSERT INTO issuetracker.issue(author_id, milestone_id, title, status) VALUES (1, 1, '제목4', 'deleted');
