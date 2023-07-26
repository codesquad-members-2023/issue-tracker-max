INSERT INTO member (email, password)
values ('nag@codesquad.kr', '1q2w3e4r!'),
       ('joy@codesquad.kr', '1q2w3e4r!'),
       ('ati@codesquad.kr', '1q2w3e4r!');

INSERT INTO milestone (name, due_date)
values ('마일스톤 1', current_date),
       ('마일스톤 2', current_date),
       ('마일스톤 3', current_date);

INSERT INTO label (name, background_color, text_color)
values ('라벨 1', '#F08080', '#000000'),
       ('라벨 2', '#98FB98', '#000000'),
       ('라벨 3', '#6495ED', '#000000');

INSERT INTO issue (member_id, PIC_id, milestone_id, title)
values (1, 1, 1, '제목 1'),
       (2, 3, 2, '제목 2'),
       (3, 1, 3, '제목 3'),
       (3, 2, 1, '제목 4'),
       (1, 3, 1, '제목 5');

INSERT INTO issue_label (issue_id, label_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 1),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

# 이슈 아이디 5개, 멤버 아이디 3개
INSERT INTO  comment (issue_id, member_id, contents)
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