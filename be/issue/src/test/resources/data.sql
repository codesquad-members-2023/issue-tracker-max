INSERT INTO users(email, password, profile_img, name, login_type)
VALUES ("abcd@naver.com",
        "$2a$10$m3yYBH1ZmolUrEA0IFjgFOFaIE2g7/Br9xgQKPZGRz6D20dGC2S8.",
        "https://upload.wikimedia.org/wikipedia/commons/1/17/Enhydra_lutris_face.jpg",
        "light", "local");

INSERT INTO users(email, password, profile_img, name, login_type)
VALUES ("abce@naver.com",
        "$2a$10$SVqof3WP3MleyIdSTQdaneZEfMk8ezFgOkW2voRY.TxzNZIotChJe",
        "https://upload.wikimedia.org/wikipedia/commons/1/17/Enhydra_lutris_face.jpg",
        "gamgyul", "local");

INSERT INTO users(email, password, profile_img, name, login_type)
VALUES ("abcf@naver.com",
        "$2a$10$GSAMSZ7WdI8Dvv.A.9bf7uOnRqp9E/esLRUxuwzSnb0uSgCUx4VXS",
        "https://upload.wikimedia.org/wikipedia/commons/1/17/Enhydra_lutris_face.jpg",
        "sio", "local");

-- issues
INSERT INTO issues (milestone_id, user_id, title, content)
VALUES (1, 1, '1번이슈', '내용1');
INSERT INTO issues (milestone_id, user_id, title, content)
VALUES (1, 2, '2번이슈', '내용2');
INSERT INTO issues (milestone_id, user_id, title, content)
VALUES (2, 3, '3번이슈', '내용3');
INSERT INTO issues (milestone_id, user_id, title, content, is_closed)
VALUES (1, 1, '1번이슈', '닫힌이슈', true);

-- milestone
INSERT INTO milestones (name, description, done_date)
VALUES ('sprint1', '1번마일스톤', '2023-09-20');
INSERT INTO milestones (name, description, done_date)
VALUES ('sprint2', '2번마일스톤', '2023-09-20');
INSERT INTO milestones (name, description, done_date)
VALUES ('sprint3', '3번마일스톤', '2023-09-20');
INSERT INTO milestones (name, description, done_date, is_closed)
VALUES ('sprint1', '4번마일스톤', '2023-09-20', true);

-- label
INSERT INTO labels (name, description, background_color, text_color)
VALUES ("1번라벨", "설명1", "#111", "#222");
INSERT INTO labels (name, description, background_color, text_color)
VALUES ("2번라벨", "설명2", "#222", "#222");
INSERT INTO labels (name, description, background_color, text_color)
VALUES ("3번라벨", "설명3", "#333", "#222");

-- label_issue
INSERT INTO issues_labels (issue_id, label_id)
VALUES (1, 1);
INSERT INTO issues_labels (issue_id, label_id)
VALUES (1, 2);
INSERT INTO issues_labels (issue_id, label_id)
VALUES (2, 2);
INSERT INTO issues_labels (issue_id, label_id)
VALUES (3, 3);


