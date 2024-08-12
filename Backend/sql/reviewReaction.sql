-- 좋아요(Like)와 싫어요(Dislike) 데이터를 삽입하는 SQL 문

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 5),
           (SELECT id FROM Member WHERE username = 'Chris'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 4),
           (SELECT id FROM Member WHERE username = 'Jessie'),
           'DISLIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 2),
           (SELECT id FROM Member WHERE username = 'Jessie'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 23),
           (SELECT id FROM Member WHERE username = 'Jordan'),
           'DISLIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 13),
           (SELECT id FROM Member WHERE username = 'Jordan'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 13),
           (SELECT id FROM Member WHERE username = 'Alex'),
           'DISLIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 24),
           (SELECT id FROM Member WHERE username = 'Alex'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 20),
           (SELECT id FROM Member WHERE username = 'Riley'),
           'DISLIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 7),
           (SELECT id FROM Member WHERE username = 'Morgan'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 5),
           (SELECT id FROM Member WHERE username = 'Riley'),
           'DISLIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 3),
           (SELECT id FROM Member WHERE username = 'Jessie'),
           'LIKE'
       );

INSERT INTO review_reaction (review_id, member_id, reaction_type)
VALUES (
           (SELECT id FROM Review WHERE id = 9),
           (SELECT id FROM Member WHERE username = 'Alex'),
           'DISLIKE'
       );