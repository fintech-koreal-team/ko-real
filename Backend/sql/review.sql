-- review.sql
-- Review 테이블에 개별적으로 더미 데이터 삽입

-- 리뷰 1
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Alex'),
    (SELECT id FROM Product WHERE product_code = 'P001'),
    5,
    'I\'ve been using this lotion for a month now, and my skin has never felt better!',
    '2023-04-01'
);

-- 리뷰 2
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Riley'),
    (SELECT id FROM Product WHERE product_code = 'P001'),
    5,
    'The best lotion I\'ve ever used!',
    '2024-02-25'
);

-- 리뷰 3
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Jordan'),
    (SELECT id FROM Product WHERE product_code = 'P001'),
    4.9,
    'It\'s okay, not great.',
    '2024-05-15'
);

-- 리뷰 4
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Chris'),
    (SELECT id FROM Product WHERE product_code = 'P001'),
    1.1,
    'Did not notice much improvement.',
    '2023-09-26'
);

-- 리뷰 5
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Riley'),
    (SELECT id FROM Product WHERE product_code = 'P001'),
    3,
    'Great for sensitive skin.',
    '2024-06-28'
);

-- 리뷰 6
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Jordan'),
    (SELECT id FROM Product WHERE product_code = 'P002'),
    4,
    'Great product, but the scent is not for me.',
    '2023-05-16'
);

-- 리뷰 7
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Morgan'),
    (SELECT id FROM Product WHERE product_code = 'P002'),
    4.5,
    'Great hydration, but the bottle could be bigger.',
    '2024-01-15'
);

-- 리뷰 8
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Jordan'),
    (SELECT id FROM Product WHERE product_code = 'P002'),
    2.2,
    'Not bad, but I\'ve used better.',
    '2024-06-25'
);

-- 리뷰 9
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jessie'),
   (SELECT id FROM Product WHERE product_code = 'P002'),
   1.2,
   'Would definitely recommend.',
   '2023-08-09'
);

-- 리뷰 10
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Casey'),
#    (SELECT id FROM Product WHERE product_code = 'P002'),
#    2.3,
#    'Love the texture, very smooth.',
#    '2023-08-09'
# );

-- 리뷰 11
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Sam'),
   (SELECT id FROM Product WHERE product_code = 'P003'),
   5,
   'Absolutely love it! Hydrates my skin without feeling greasy.',
   '2024-02-13'
);

-- 리뷰 12
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Taylor'),
   (SELECT id FROM Product WHERE product_code = 'P003'),
   4,
   'Good product, but a bit pricey for the amount.',
   '2023-11-20'
);

-- 리뷰 13
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jordan'),
   (SELECT id FROM Product WHERE product_code = 'P003'),
   3.9,
   'Did not notice much improvement.',
   '2024-01-06'
);

-- 리뷰 14
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Lee'),
    (SELECT id FROM Product WHERE product_code = 'P003'),
    2.1,
    'Not bad, but I\'ve used better.',
    '2023-09-06'
);

-- 리뷰 15
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Riley'),
    (SELECT id FROM Product WHERE product_code = 'P003'),
    3.6,
    'Not worth the hype.',
    '2023-03-04'
);

-- 리뷰 16
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Taylor'),
    (SELECT id FROM Product WHERE product_code = 'P004'),
    3,
    'It\'s okay, didn\'t see much difference in my skin.',
    '2024-07-12'
);

-- 리뷰 17
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Chris'),
    (SELECT id FROM Product WHERE product_code = 'P004'),
    5,
    'Absolutely love this lotion! Keeps my skin soft all day.',
    '2024-03-01'
);

-- 리뷰 18
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Pat'),
    (SELECT id FROM Product WHERE product_code = 'P004'),
    3.8,
    'Not bad, but I\'ve used better.',
           '2023-02-17'
       );

-- 리뷰 19
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Riley'),
   (SELECT id FROM Product WHERE product_code = 'P004'),
   2.2,
   'Did not notice much improvement.',
   '2024-05-30'
);

-- 리뷰 20
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Avery'),
   (SELECT id FROM Product WHERE product_code = 'P004'),
   3.9,
   'Works well, but the scent is too strong.',
   '2024-04-27'
);

-- 리뷰 21
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Casey'),
   (SELECT id FROM Product WHERE product_code = 'P005'),
   4,
   'Good lotion, but I wish the bottle was bigger.',
   '2023-10-07'
);

-- 리뷰 22
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Pat'),
   (SELECT id FROM Product WHERE product_code = 'P005'),
   4.5,
   'Very effective, but wish it absorbed a bit faster.',
   '2024-02-10'
);

-- 리뷰 23
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Casey'),
   (SELECT id FROM Product WHERE product_code = 'P005'),
   1.3,
   'Fantastic product, very hydrating.',
   '2023-03-23'
);

-- 리뷰 24
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Drew'),
#    (SELECT id FROM Product WHERE product_code = 'P005'),
#    1.2,
#    'Excellent moisturizer!',
#    '2024-01-17'
# );

-- 리뷰 25
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Jessie'),
    (SELECT id FROM Product WHERE product_code = 'P005'),
    4.5,
    'Did not notice much improvement.',
    '2023-01-01'
);

-- 리뷰 26
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#     (SELECT id FROM Member WHERE username = 'Nancy'),
#     (SELECT id FROM Product WHERE product_code = 'P006'),
#     4.5,
#     'I love it.',
#     '2023-08-12'
# );

-- 리뷰 27
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#     (SELECT id FROM Member WHERE username = 'Avery'),
#     (SELECT id FROM Product WHERE product_code = 'P006'),
#     4,
#     'Good lotion, but the scent is too strong for me.',
#     '2023-12-05'
# );

-- 리뷰 28
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Alex'),
    (SELECT id FROM Product WHERE product_code = 'P006'),
    4.6,
    'Great for sensitive skin.',
    '2023-11-10'
);

-- 리뷰 29
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Pat'),
#    (SELECT id FROM Product WHERE product_code = 'P006'),
#    2.3,
#    'Did not notice much improvement.',
#    '2023-09-03'
# );

-- 리뷰 30
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jessie'),
   (SELECT id FROM Product WHERE product_code = 'P006'),
   4.1,
   'Works well, but the scent is too strong.',
   '2023-02-05'
);

-- 리뷰 31
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Jamie'),
#    (SELECT id FROM Product WHERE product_code = 'P007'),
#    4.5,
#    'My skin definitely feels smoother! Wish it was a bit cheaper though.',
#    '2023-11-05'
# );

-- 리뷰 32
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jordan'),
   (SELECT id FROM Product WHERE product_code = 'P007'),
   5,
   'Perfect for my dry skin! Highly recommend it.',
   '2024-01-28'
);

-- 리뷰 33
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Riley'),
    (SELECT id FROM Product WHERE product_code = 'P007'),
    4.2,
    'Love the texture, very smooth.',
    '2024-01-06'
);

-- 리뷰 34
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#     (SELECT id FROM Member WHERE username = 'Sam'),
#     (SELECT id FROM Product WHERE product_code = 'P007'),
#     3,
#     'Too expensive for the amount.',
#     '2023-10-05'
# );

-- 리뷰 35
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#     (SELECT id FROM Member WHERE username = 'Sam'),
#     (SELECT id FROM Product WHERE product_code = 'P007'),
#     2.1,
#     'Great for sensitive skin.',
#     '2023-11-29'
# );

-- 리뷰 36
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
    (SELECT id FROM Member WHERE username = 'Morgan'),
    (SELECT id FROM Product WHERE product_code = 'P008'),
    5,
    'Perfect product! Love the scent too.',
    '2023-12-21'
);

-- 리뷰 37
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#     (SELECT id FROM Member WHERE username = 'Sam'),
#     (SELECT id FROM Product WHERE product_code = 'P008'),
#     3.5,
#     'Works okay, but didn''t notice any significant difference.',
#     '2024-02-18'
# );

-- 리뷰 38
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Chris'),
   (SELECT id FROM Product WHERE product_code = 'P008'),
   4.2,
   'Not worth the hype.',
   '2024-02-16'
);

-- 리뷰 39
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Taylor'),
#    (SELECT id FROM Product WHERE product_code = 'P008'),
#    3.1,
#    'Works well, but the scent is too strong.',
#    '2023-01-06'
# );

-- 리뷰 40
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Morgan'),
   (SELECT id FROM Product WHERE product_code = 'P008'),
   2.8,
   'Fantastic product, very hydrating.',
   '2023-06-27'
);

-- 리뷰 41
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Riley'),
   (SELECT id FROM Product WHERE product_code = 'P009'),
   3,
   'Didn''t notice much difference. Feels like a regular lotion.',
   '2024-01-02'
);

-- 리뷰 42
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Casey'),
#    (SELECT id FROM Product WHERE product_code = 'P009'),
#    4,
#    'Good lotion, but a bit greasy.',
#    '2024-01-05'
# );

-- 리뷰 43
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Jamie'),
#    (SELECT id FROM Product WHERE product_code = 'P009'),
#    1.9,
#    'My skin feels amazing!',
#    '2023-07-28'
# );

-- 리뷰 44
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Chris'),
   (SELECT id FROM Product WHERE product_code = 'P009'),
   3.9,
   'Perfect for daily use.',
   '2023-08-19'
);

-- 리뷰 45
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Riley'),
#    (SELECT id FROM Product WHERE product_code = 'P09'),
#    3.1,
#    'Too expensive for the amount.',
#    '2023-03-15'
# );

-- 리뷰 46
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Taylor'),
#    (SELECT id FROM Product WHERE product_code = 'P010'),
#    4,
#    'Moisturizes well and absorbs quickly. Just wish it came in a larger size.',
#    '2023-09-14'
# );

-- 리뷰 47
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Jamie'),
#    (SELECT id FROM Product WHERE product_code = 'P010'),
#    4.5,
#    'Very hydrating and makes my skin feel smooth, but it''s a bit expensive.',
#    '2024-03-12'
# );

-- 리뷰 48
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Casey'),
#    (SELECT id FROM Product WHERE product_code = 'P010'),
#    2.4,
#    'Best lotion I''ve ever used.',
#    '2023-02-16'
# );

-- 리뷰 49
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jessie'),
   (SELECT id FROM Product WHERE product_code = 'P010'),
   1.9,
   'A bit too oily for my liking.',
   '2023-04-20'
);

-- 리뷰 50
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Drew'),
#    (SELECT id FROM Product WHERE product_code = 'P010'),
#    4.9,
#    'It''s okay, not great.',
#    '2023-02-21'
# );

-- 리뷰 51
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Chris'),
   (SELECT id FROM Product WHERE product_code = 'P011'),
   5,
   'Completely satisfied! This is my go-to lotion.',
   '2023-10-20'
);

-- 리뷰 52
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Alex'),
   (SELECT id FROM Product WHERE product_code = 'P011'),
   5,
   'My skin feels amazing after using this lotion.',
   '2024-03-15'
);

-- 리뷰 53
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Sam'),
#    (SELECT id FROM Product WHERE product_code = 'P011'),
#    1.4,
#    'Would definitely recommend.',
#    '2023-11-03'
# );

-- 리뷰 54
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Sam'),
#    (SELECT id FROM Product WHERE product_code = 'P011'),
#    4.2,
#    'My skin feels amazing!',
#    '2023-06-20'
# );

-- 리뷰 55
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Avery'),
#    (SELECT id FROM Product WHERE product_code = 'P011'),
#    3.3,
#    'Not bad, but I''ve used better.',
#    '2023-05-25'
# );

-- 리뷰 56
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Alex'),
   (SELECT id FROM Product WHERE product_code = 'P012'),
   4,
   '2024-02-11',
   '2024-02-11'
);

-- 리뷰 57
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Taylor'),
#    (SELECT id FROM Product WHERE product_code = 'P012'),
#    4,
#    'Absorbs quickly and keeps my skin hydrated.',
#    '2023-12-10'
# );

-- 리뷰 58
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Lee'),
   (SELECT id FROM Product WHERE product_code = 'P012'),
   2.8,
   'Excellent moisturizer!',
   '2023-10-29'
);

-- 리뷰 59
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Morgan'),
   (SELECT id FROM Product WHERE product_code = 'P012'),
   2.9,
   'Fantastic product, very hydrating.',
   '2023-01-22'
);

-- 리뷰 60
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Taylor'),
#    (SELECT id FROM Product WHERE product_code = 'P012'),
#    2.4,
#    'Did not notice much improvement.',
#    '2023-02-28'
# );

-- 리뷰 61
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Jordan'),
   (SELECT id FROM Product WHERE product_code = 'P013'),
   4.5,
   'Really good! My skin feels hydrated, but the scent is a bit strong.',
   '2023-11-30'
);

-- 리뷰 62
INSERT INTO Review (member_id, product_id, rating, comment, created_at)
VALUES (
   (SELECT id FROM Member WHERE username = 'Morgan'),
   (SELECT id FROM Product WHERE product_code = 'P013'),
   5,
   'Love this lotion! It''s perfect for daily use.',
   '2024-01-05'
);

-- 리뷰 63
# INSERT INTO Review (member_id, product_id, rating, comment, created_at)
# VALUES (
#    (SELECT id FROM Member WHERE username = 'Drew'),
#    (SELECT id FROM Product WHERE product_code = 'P013'),
#    2.6,
#    'Works well, but the scent is too strong.',
#    '2023-02-18'
# );