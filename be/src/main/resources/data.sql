INSERT INTO member (email, name, password, profile)
values ('nag@codesquad.kr', 'nag', '1q2w3e4r!',
        'https://source.boringavatars.com/beam/20/nag'),
       ('joy@codesquad.kr', 'joy', '1q2w3e4r!',
        'https://source.boringavatars.com/beam/20/joy'),
       ('ati@codesquad.kr', 'ati', '1q2w3e4r!',
        'https://source.boringavatars.com/beam/20/ati');

INSERT INTO milestone (name, due_date, is_open)
values ('마일스톤 1', current_date, 1),
       ('마일스톤 2', current_date, 0),
       ('마일스톤 3', current_date, 1),
       ('마일스톤 0', current_date, 1);

INSERT INTO label (name, background_color, text_color)
values ('라벨 1', '#F08080', '#000000'),
       ('라벨 2', '#98FB98', '#000000'),
       ('라벨 3', '#6495ED', '#000000'),
       ('라벨 0', '#98FB98', '#000000');

INSERT INTO issue (author_id, milestone_id, title, is_open)
values (1, 1, '제목 1', 1),
       (2, 2, '제목 2', 0),
       (3, 3, '제목 3', 1),
       (3, 1, '제목 4', 0),
       (1, 1, '제목 5', 0);

INSERT INTO issue_label (issue_id, label_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

INSERT INTO issue_assignee (issue_id, assignee_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

INSERT INTO comment (issue_id, author_id, contents)
values (1, 1, '1번 댓글입니다.'),
       (1, 2, '2번 댓글입니다.'),
       (1, 2, '3번 댓글입니다.'),
       (3, 1, '4번 댓글입니다.'),
       (4, 1, '5번 댓글입니다.'),
       (4, 1, '6번 댓글입니다.'),
       (4, 1, '7번 댓글입니다.'),
       (5, 2, '8번 댓글입니다.'),
       (5, 3, '9번 댓글입니다.'),
       (5, 1, '10번 댓글입니다.'),
       (5, 2, '11번 댓글입니다.');
